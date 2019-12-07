package de.istkorrekt.gameoflife

interface Matchfield {

    fun getCellAt(location: Location): Cell

    fun streamAllLocations(): Collection<Location>

    fun nextGeneration(): Matchfield

    fun switchCellAt(location: Location)

    fun getSize(): Size
}