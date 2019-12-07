package de.istkorrekt.gameoflife

enum class Cell {
    DEAD,
    ALIVE;

    fun nextGeneration(livingNeighbors: Int): Cell {
        if (this == ALIVE) {
            if (livingNeighbors < 2L) {
                return DEAD
            }
            return if (livingNeighbors < 4L) {
                ALIVE
            } else DEAD
        }
        return if (livingNeighbors == 3) {
            ALIVE
        } else DEAD
    }

    fun isAlive(): Boolean = this == ALIVE

    override fun toString(): String {
        return when (this) {
            DEAD -> "[ ]"
            ALIVE -> "[❤]"
        }
    }
}