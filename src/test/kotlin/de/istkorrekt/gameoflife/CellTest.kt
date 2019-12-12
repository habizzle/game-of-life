package de.istkorrekt.gameoflife

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CellTest {
    @Test
    fun testCellIsResurrectedWithExactly3AliveNeighbors() {
        val cell = Cell.DEAD
        val nextGeneration = cell.nextGeneration(3)
        assertAlive(nextGeneration)
    }

    @Test
    fun testCellStaysDeadWithLessThan3AliveNeighbors() {
        val cell = Cell.DEAD
        val nextGeneration = cell.nextGeneration(2)
        assertDead(nextGeneration)
    }

    @Test
    fun testCellStaysDeadWithMoreThan3AliveNeighbors() {
        val cell = Cell.DEAD
        val nextGeneration = cell.nextGeneration(4)
        assertDead(nextGeneration)
    }

    @Test
    fun testCellDiesWithLessThan2Neighbors() {
        val cell = Cell.ALIVE
        val nextGeneration = cell.nextGeneration(1)
        assertDead(nextGeneration)
    }

    @Test
    fun testCellStaysAliveWith2Neighbors() {
        val cell = Cell.ALIVE
        val nextGeneration = cell.nextGeneration(2)
        assertAlive(nextGeneration)
    }

    @Test
    fun testCellStaysAliveWith3Neighbors() {
        val cell = Cell.ALIVE
        val nextGeneration = cell.nextGeneration(3)
        assertAlive(nextGeneration)
    }

    @Test
    fun testCellDiesWithMoreThan3Neighbors() {
        val cell = Cell.ALIVE
        val nextGeneration = cell.nextGeneration(4)
        assertDead(nextGeneration)
    }

    companion object {
        private fun assertDead(cell: Cell) {
            Assertions.assertFalse(cell.isAlive())
        }

        private fun assertAlive(cell: Cell) {
            Assertions.assertTrue(cell.isAlive())
        }
    }
}