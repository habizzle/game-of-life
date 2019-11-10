package de.istkorrekt.gameoflife;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ArrayMatchfield implements Matchfield {

    private final Cell[][] cells;

    ArrayMatchfield(Collection<Location> locations, Size size) {
        cells = emptyCells(size.getWidth(), size.getHeight());
        locations.forEach(location -> cells[location.getY()][location.getX()] = Cell.alive());
    }

    private ArrayMatchfield(Cell[][] cells) {
        this.cells = cells;
    }

    @Override
    public Cell getCellAt(Location location) {
        Cell cell = cells[location.getY()][location.getX()];
        if (cell == null) {
            return Cell.dead();
        }
        return cell;
    }

    @Override
    public Stream<Location> streamAllLocations() {
        return IntStream.range(0, width())
                .mapToObj(this::streamLocationsWithX)
                .flatMap(Function.identity());
    }

    private Stream<Location> streamLocationsWithX(int x) {
        return Stream.iterate(0, y -> y + 1)
                .map(y -> Location.of(x, y))
                .limit(height());
    }

    @Override
    public ArrayMatchfield nextGeneration() {
        Cell[][] nextGenerationCells = emptyCells(width(), height());
        streamAllLocations()
                .forEach(location -> {
                    Cell cell = getCellAt(location);
                    long amountOfLivingNeighbors = streamNeighborCells(location)
                            .filter(Cell::isAlive)
                            .count();
                    Cell nextGenerationCell = cell.nextGeneration(amountOfLivingNeighbors);
                    nextGenerationCells[location.getY()][location.getX()] = nextGenerationCell;
                });

        return new ArrayMatchfield(nextGenerationCells);
    }

    Stream<Cell> streamNeighborCells(Location location) {
        return streamNeighborLocations(location)
                .map(this::getCellAt);
    }

    Stream<Location> streamNeighborLocations(Location location) {
        int maxIndexX = maxIndexX();
        int maxIndexY = maxIndexY();
        int x = location.getX();
        int y = location.getY();
        int leftX = x > 0 ? x - 1 : maxIndexX;
        int rightX = x < maxIndexX ? x + 1 : 0;
        int topY = y > 0 ? y - 1 : maxIndexY;
        int bottomY = y < maxIndexY ? y + 1 : 0;
        return Stream.of(
                Location.of(leftX, topY),
                Location.of(x, topY),
                Location.of(rightX, topY),
                Location.of(leftX, y),
                Location.of(rightX, y),
                Location.of(leftX, bottomY),
                Location.of(x, bottomY),
                Location.of(rightX, bottomY)
        );
    }

    int width() {
        return cells[0].length;
    }

    private int maxIndexX() {
        return width() - 1;
    }

    int height() {
        return cells.length;
    }

    private int maxIndexY() {
        return height() - 1;
    }

    @Override
    public Size getSize() {
        return Size.of(width(), height());
    }

    @Override
    public String toString() {
        int maxIndexX = maxIndexX();
        int maxIndexY = maxIndexY();
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < maxIndexY; y++) {
            for (int x = 0; x < maxIndexX; x++) {
                sb.append(getCellAt(Location.of(x, y)).toString());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public void switchCellAt(Location location) {
        Cell existingCell = getCellAt(location);
        cells[location.getY()][location.getX()] = existingCell.isAlive()
                ? Cell.dead()
                : Cell.alive();
    }

    private static Cell[][] emptyCells(int width, int height) {
        return new Cell[height][width];
    }
}
