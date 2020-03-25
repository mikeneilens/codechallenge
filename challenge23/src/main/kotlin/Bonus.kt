
fun String.letterAt(position:Position):String {
    return if (position.x in 0..13 && position.y in  0..13) {
        val index = position.x  + position.y * 14
        this[index].toString()
    } else ""
}

fun String.textAt(position:Position, direction:Direction, length:Int):String {
     val letters = (0 until length).map{ this.letterAt(position + (direction.step * it))}
    return letters.fold(""){a,e -> a + e}
}

fun String.wordFound(puzzle:String, position:Position, direction: Direction) = puzzle.textAt(position, direction, this.length) == this

fun String.positionOf(text:String):Pair<Position,Direction>? {
    return (0..195).flatMap { posNdx ->
        Direction.values().mapNotNull { direction ->
            if (text.wordFound(this, Position(posNdx % 14, posNdx / 14), direction)) {
                Pair(Position(posNdx % 14, posNdx / 14), direction)
            } else null
        }
    }.firstOrNull()
}

fun String.findPositionOfEachClue(clues:List<String>):List<Triple<String, Position,Direction>> = clues.mapNotNull{ clue ->
        val positionAndDirection = this.positionOf(clue)
        positionAndDirection?.let{Triple(clue,it.first, it.second)} ?: null }
