package de.istkorrekt.gameoflife;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellTest {

    @Test
    void testCellIsResurrectedWithExactly3AliveNeighbors() {
        Cell cell = Cell.dead();
        Cell nextGeneration = cell.nextGeneration(3);
        assertAlive(nextGeneration);
    }

    @Test
    void testCellStaysDeadWithLessThan3AliveNeighbors() {
        Cell cell = Cell.dead();
        Cell nextGeneration = cell.nextGeneration(2);
        assertDead(nextGeneration);
    }

    @Test
    void testCellStaysDeadWithMoreThan3AliveNeighbors() {
        Cell cell = Cell.dead();
        Cell nextGeneration = cell.nextGeneration(4);
        assertDead(nextGeneration);
    }

    @Test
    void testCellDiesWithLessThan2Neighbors() {
        Cell cell = Cell.alive();
        Cell nextGeneration = cell.nextGeneration(1);
        assertDead(nextGeneration);
    }

    @Test
    void testCellStaysAliveWith2Neighbors() {
        Cell cell = Cell.alive();
        Cell nextGeneration = cell.nextGeneration(2);
        assertAlive(nextGeneration);
    }

    @Test
    void testCellStaysAliveWith3Neighbors() {
        Cell cell = Cell.alive();
        Cell nextGeneration = cell.nextGeneration(3);
        assertAlive(nextGeneration);
    }

    @Test
    void testCellDiesWithMoreThan3Neighbors() {
        Cell cell = Cell.alive();
        Cell nextGeneration = cell.nextGeneration(4);
        assertDead(nextGeneration);
    }

    private static void assertDead(Cell cell) {
        Assertions.assertFalse(cell.isAlive());
    }

    private static void assertAlive(Cell cell) {
        Assertions.assertTrue(cell.isAlive());
    }
}
