package de.istkorrekt.gameoflife

data class Location(val x: Int, val y: Int) : Comparable<Location> {

    companion object {
        private val COMPARATOR = Comparator.comparingInt<Location> { it.x }
                .thenComparingInt { it.y }
    }

    override fun compareTo(other: Location): Int {
        return COMPARATOR.compare(this, other)
    }

    override fun toString(): String {
        return "($x|$y)"
    }

    fun upper(): Location? {
        val upperY = (y - 1).coerceAtLeast(0)
        return Location(x, upperY)
    }

    fun right(bounds: Size): Location {
        // x and y are zero-based
        val rightX = (x + 1).coerceAtMost(bounds.width - 1)
        return Location(rightX, y)
    }

    fun lower(bounds: Size): Location {
        // x and y are zero-based
        val lowerY = (y + 1).coerceAtMost(bounds.height - 1)
        return Location(x, lowerY)
    }

    fun left(): Location {
        val update = (x - 1).coerceAtLeast(0)
        return Location(update, y)
    }
}