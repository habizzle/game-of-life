package de.istkorrekt.gameoflife.presentation.terminal;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import de.istkorrekt.gameoflife.*;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TerminalApplication {

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 32;

    private static final int CANVAS_TOP_ROWS = 1;
    private static final int CANVAS_BOTTOM_ROWS = 1;
    private static final int CELL_COLS = 3;

    public static void main(String[] args) {
        TerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(DEFAULT_WIDTH, DEFAULT_HEIGHT))
                .setForceTextTerminal(false);
        try (Terminal terminal = terminalFactory.createTerminal()) {
            Screen screen = new TerminalScreen(terminal);
            new TerminalApplication(screen).start();
        } catch (IOException e) {
            Logger.getLogger("game").throwing(TerminalApplication.class.toString(), "main", e);
        }
    }

    private final Screen screen;

    private int roundCounter = 0;
    private Matchfield currentMatchField;
    private Location currentSelectorLocation;

    public TerminalApplication(Screen screen) {
        this.screen = screen;
    }

    private void start() {
        drawInitialScreen();

        Stream.generate(this::nextAction)
                .takeWhile(this::waitForNextAction)
                .forEach(this::handleAction);

        stop();
    }

    private void drawInitialScreen() {
        screen.setCursorPosition(null);
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        drawCanvas();
        currentMatchField = initMatchField();
        drawMatchField();
        refresh();
    }

    private Action nextAction() {
        if (screen.doResizeIfNecessary() != null) {
            refresh();
        }
        try {
            KeyStroke keyStroke = screen.pollInput();
            return Action.valueOfKeyStroke(keyStroke);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean waitForNextAction(Action action) {
        return action != Action.EXIT;
    }

    private void handleAction(Action action) {
        if (action != null) {
            switch (action) {
                case CURSOR_UP:
                    handleSelectorLocationChange(Location::upper);
                    break;
                case CURSOR_RIGHT:
                    handleSelectorLocationChange(location -> location.right(currentMatchField.getSize()));
                    break;
                case CURSOR_DOWN:
                    handleSelectorLocationChange(location -> location.lower(currentMatchField.getSize()));
                    break;
                case CURSOR_LEFT:
                    handleSelectorLocationChange(Location::left);
                    break;
                case SELECT:
                    handleSelect();
                    break;
                case NEXT_ROUND:
                    handleNextRound();
                    break;
                case RESET_CURSOR:
                    handleResetSelector();
                    break;
            }
            refresh();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Matchfield initMatchField() {
        TerminalSize size = screen.getTerminalSize();
        int width = size.getColumns();
        int height = size.getRows();

        int matchFieldWidth = width / CELL_COLS;
        int matchFieldHeight = height - CANVAS_TOP_ROWS - CANVAS_BOTTOM_ROWS;
        return MatchfieldFactory.INSTANCE.createAllDead(new Size(matchFieldWidth, matchFieldHeight));
    }

    private void handleSelectorLocationChange(Function<Location, Location> locationTransformer) {
        Location oldSelectorLocation = currentSelectorLocation;

        if (currentSelectorLocation == null) {
            currentSelectorLocation = new Location(0, 0);
        } else {
            currentSelectorLocation = locationTransformer.apply(currentSelectorLocation);
            drawCellAt(oldSelectorLocation);
        }

        if (!Objects.equals(currentSelectorLocation, oldSelectorLocation)) {
            drawSelectorLocation();
        }
    }

    private void handleResetSelector() {
        if (currentSelectorLocation != null) {
            Location oldSelectorLocation = currentSelectorLocation;
            currentSelectorLocation = null;
            drawCellAt(oldSelectorLocation);
        }
    }

    private void drawSelectorLocation() {
        if (currentSelectorLocation != null) {
            drawCellAt(currentSelectorLocation);
        }
    }

    private void handleNextRound() {
        roundCounter++;
        drawCanvas();
        currentMatchField = currentMatchField.nextGeneration();
        drawMatchField();
    }

    private void handleSelect() {
        if (currentSelectorLocation != null) {
            currentMatchField.switchCellAt(currentSelectorLocation);
            drawCellAt(currentSelectorLocation);
        }
    }

    private void refresh() {
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawCanvas() {
        TerminalSize terminalSize = screen.getTerminalSize();
        screen.newTextGraphics()
                .setBackgroundColor(TextColor.ANSI.GREEN)
                .setForegroundColor(TextColor.ANSI.BLACK)
                .drawLine(0, 0, terminalSize.getColumns(), 0, ' ')
                .putString(0, 0, "Conway's Game of Life - Round " + roundCounter)
                .setBackgroundColor(TextColor.ANSI.GREEN)
                .setForegroundColor(TextColor.ANSI.BLACK)
                .drawLine(0, terminalSize.getRows() - 1, terminalSize.getColumns(), terminalSize.getRows() - 1, ' ')
                .putString(0, terminalSize.getRows() - 1, "CURSOR KEYS: Move cell selector   SPACE: Switch cell state (kill / resurrect)   ENTER: Breed next generation   STRG+C: Quit");
    }

    private void drawMatchField() {
        currentMatchField.streamAllLocations()
                .forEach(this::drawCellAt);
    }

    private void drawCellAt(Location location) {
        drawCell(location, currentMatchField.getCellAt(location));
    }

    private void drawCell(Location location, Cell cell) {
        String formattedCell = cell.toString();
        screen.newTextGraphics()
                .setForegroundColor(colorForCell(location, cell))
                .setModifiers(collectStyleModifiersForCell(cell))
                .putString(positionAt(location), formattedCell);
    }

    private TerminalPosition positionAt(Location location) {
        return new TerminalPosition(location.getX() * CELL_COLS, location.getY() + CANVAS_TOP_ROWS);
    }

    private TextColor colorForCell(Location location, Cell cell) {
        if (location.equals(currentSelectorLocation)) {
            return TextColor.ANSI.WHITE;
        }
        byte scale = (byte) (0.2 * 255);
        return cell.isAlive() ? TextColor.ANSI.RED : TextColor.Indexed.fromRGB(scale, scale, scale);
    }

    private EnumSet<SGR> collectStyleModifiersForCell(Cell cell) {
        return cell.isAlive()
                ? EnumSet.of(SGR.BOLD)
                : EnumSet.noneOf(SGR.class);
    }

    private void stop() {
        try {
            screen.stopScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
