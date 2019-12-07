package de.istkorrekt.gameoflife

class ArrayMatchfield(private val size: Size) : Matchfield {

    private val cells: Array<Array<Cell>> = Array(size.height) { Array(size.width) { Cell.DEAD } }

    constructor(size: Size, aliveLocations: Collection<Location>) : this(size) {
        aliveLocations.forEach {
            cells[it.y][it.x] = Cell.ALIVE
        }
    }

    override fun getCellAt(location: Location): Cell {
        return cells[location.y][location.x]
    }

    override fun streamAllLocations(): Collection<Location> {
        return IntRange(0, size.width - 1)
                .flatMap { streamLocationsWithX(it) }
    }

    private fun streamLocationsWithX(x: Int): Collection<Location> {
        return IntRange(0, size.height - 1)
                .map { Location(x, it) }
    }

    override fun nextGeneration(): Matchfield {
        return ArrayMatchfield(size).also { next ->
            streamAllLocations().forEach { location ->
                val cell = getCellAt(location)
                val amountOfLivingNeighbors = collectNeighborLocations(location)
                        .map { getCellAt(it) }
                        .count { it == Cell.ALIVE }
                val nextGenerationCell = cell.nextGeneration(amountOfLivingNeighbors)
                next.setCellAt(location, nextGenerationCell)
            }
            return next
        }
    }

    fun collectNeighborLocations(location: Location): Collection<Location> {
        val maxIndexX = maxIndexX()
        val maxIndexY = maxIndexY()
        val x = location.x
        val y = location.y
        val leftX = if (x > 0) x - 1 else maxIndexX
        val rightX = if (x < maxIndexX) x + 1 else 0
        val topY = if (y > 0) y - 1 else maxIndexY
        val bottomY = if (y < maxIndexY) y + 1 else 0
        return listOf(
                Location(leftX, topY),
                Location(x, topY),
                Location(rightX, topY),
                Location(leftX, y),
                Location(rightX, y),
                Location(leftX, bottomY),
                Location(x, bottomY),
                Location(rightX, bottomY)
        )
    }

    private fun maxIndexX(): Int {
        return size.width - 1
    }

    private fun maxIndexY(): Int {
        return size.height - 1
    }

    override fun switchCellAt(location: Location) {
        cells[location.y][location.x] = when (getCellAt(location)) {
            Cell.ALIVE -> Cell.DEAD
            else -> Cell.ALIVE
        }
    }

    override fun getSize(): Size {
        return size
    }

    private fun setCellAt(location: Location, cell: Cell) {
        cells[location.y][location.x] = cell
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0 until size.height) {
            for (x in 0 until size.width) {
                sb.append(getCellAt(Location(x, y)).toString())
            }
            sb.append('\n')
        }
        return sb.toString()
    }
}