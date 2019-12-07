package de.istkorrekt.gameoflife

data class Size(val width: Int, val height: Int) {

    constructor(size: Int) : this(size, size)

    init {
        require(width >= 0 && height >= 0) { "Size dimensions must not be negative!" }
    }

    override fun toString(): String {
        return "$width x $height"
    }
}