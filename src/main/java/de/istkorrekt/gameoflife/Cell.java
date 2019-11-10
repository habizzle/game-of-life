package de.istkorrekt.gameoflife;

public class Cell {

    private static final Cell CANONICAL_DEAD = new Cell(State.DEAD);
    private static final Cell CANONICAL_ALIVE = new Cell(State.ALIVE);

    public enum State {
        DEAD, ALIVE
    }

    private final State state;

    private Cell(State state) {
        this.state = state;
    }

    public Cell nextGeneration(long livingNeighbors) {
        if (isAlive()) {
            if (livingNeighbors < 2) {
                return dead();
            }
            if (livingNeighbors < 4) {
                return alive();
            }
            return dead();
        }
        if (livingNeighbors == 3) {
            return alive();
        }
        return dead();
    }

    public boolean isAlive() {
        return state == State.ALIVE;
    }

    public static Cell alive() {
        return CANONICAL_ALIVE;
    }

    public static Cell dead() {
        return CANONICAL_DEAD;
    }

    @Override
    public String toString() {
        switch (state) {
            case DEAD:
                return "[ ]";
            case ALIVE:
                return "[❤]";
        }
        throw new IllegalStateException();
    }
}
