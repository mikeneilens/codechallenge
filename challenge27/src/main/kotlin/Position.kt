data class Position(val col:Int, val row:Int) {
    fun toLeft(positions:List<Position> = emptyList()):List<Position> =
        if (col == 0) positions
        else {
            val next = Position(col - 1, row)
            next.toLeft(positions + next )
        }
    fun toRight(positions:List<Position> = emptyList()):List<Position> =
        if (col == 9) positions
        else {
            val next = Position(col + 1, row)
            next.toRight(positions + next )
        }
    fun above(positions:List<Position> = emptyList()):List<Position> =
        if (row == 0) positions
        else {
            val next = Position(col , row - 1)
            next.above(positions + next )
        }
    fun below(positions:List<Position> = emptyList()):List<Position> =
        if (row == 9) positions
        else {
            val next = Position(col, row + 1)
            next.below(positions + next )
        }
    fun surroundingPositions():List<Position> {
        val result = mutableListOf<Position>()
        for (colOffset in (-1..1)) {
            for (rowOffset in (-1..1)) {
                val newCol = col + colOffset
                val newRow = row + rowOffset
                if(newCol in 0..9 && newRow in 0..9 && Position(newCol, newRow) != this  ) {
                    result.add(Position(newCol, newRow))
                }
            }
        }
        return result
    }

    override fun toString(): String = "${cols[col]}${rows[row]}"

    companion object {
        val cols = listOf("A","B","C","D","E","F","G","H","I","J")
        val rows = listOf("0","1","2","3","4","5","6","7","8","9")
    }
}

fun Int.toPosition() = Position(this % 10, this / 10)