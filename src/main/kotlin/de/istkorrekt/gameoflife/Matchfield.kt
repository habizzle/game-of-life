package de.istkorrekt.gameoflife

interface Matchfield {

    fun getCellAt(location: Location): Cell

    fun collectAllLocations(): Collection<Location>

    fun nextGeneration(): Matchfield

    fun switchCellAt(location: Location)

    fun getSize(): Size
}