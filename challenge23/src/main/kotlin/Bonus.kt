//This is the code to solve a puzzle given a string containing the puzzle and a list of strings containing the clues.

fun String.letterAt(position:Position):String {
    return if (position.x in 0..(WIDTH - 1) && position.y in  0..(WIDTH - 1)) {
        val index = position.x  + position.y * WIDTH
        this[index].toString()
    } else ""
}

fun String.textAt(position:Position, direction:Direction, length:Int):String {
     val letters = (0 until length).map{ this.letterAt(position + (direction.step * it))}
    return letters.joinToString("")
}

fun String.wordFound(puzzle:String, position:Position, direction: Direction) = puzzle.textAt(position, direction, this.length) == this

fun String.positionOf(text:String):Pair<Position,Direction>? {
    return (0..SIZE).flatMap { posNdx ->
        Direction.values().mapNotNull { direction ->
            if (text.wordFound(this, posNdx.asPosition, direction)) {
                Pair(Position(posNdx % WIDTH, posNdx / WIDTH), direction)
            } else null
        }
    }.firstOrNull()
}

fun String.findPositionOfEachClue(clues:List<String>):List<Triple<String, Position,Direction>> = clues.mapNotNull{ clue ->
        val positionAndDirection = this.positionOf(clue)
        positionAndDirection?.let{Triple(clue,it.first, it.second)} ?: null }
