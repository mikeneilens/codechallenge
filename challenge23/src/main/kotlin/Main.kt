//This is the code to create random puzzle.
const val WIDTH = 14
const val SIZE = WIDTH * WIDTH
val RANDOMLETTERS = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ".toList().map{it.toString()}[(0..25).random()]}

typealias WordSearchGrid = MutableMap<Position, String>

data class Position(val x:Int, val y:Int) {

    val  isValid = (x in 0 until WIDTH && y in 0 until WIDTH)

    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
    operator fun times(num:Int) = Position(x * num, y * num)

    companion object {
        fun inRandomOrder(randomGenerator: List<Int> = listOf()):List<Position> {
            val positions = (0 until SIZE).map{it.asPosition}
            return if (randomGenerator.isNotEmpty()) randomGenerator.map{positions[it]} else (0 until SIZE).shuffled().map{positions[it]}
        }
    }
}

val Int.asPosition:Position get() = Position(this % WIDTH, this / WIDTH)

enum class Direction(val step:Position) {
    HorizontalRight(Position(1,0)),
    HorizontalLeft(Position(-1,0)),
    VerticalDown(Position(0,1)),
    VerticalUp(Position(0,-1)),
    DiagonalDownRight(Position(1,1)),
    DiagonalDownLeft(Position(-1,1)),
    DiagonalUpRight(Position(1,-1)),
    DiagonalUpLeft(Position(-1,-1));

    companion object {
        fun inRandomOrder(randomGenerator: List<Int> = listOf()) =
            if (randomGenerator.isNotEmpty()) randomGenerator.map { values()[it] } else (0 until values().size).shuffled().map {values()[it] }
    }
}

fun parseIntoClues(locationString: String): List<String> {
    return locationString.split(",")
        .chunked(4)
        .map{it[0].replace(" ","")}
        .filter{it.isNotEmpty() && it.length <= 10 } //not part of the spec but only using location names with length of ten or less
        .map{it.toUpperCase()}
}

fun WordSearchGrid.clueConflictsWithGrid(clue:String, position:Position, direction:Direction) =
    clue.toList()
        .mapIndexed{ndx, letter ->
            val positionOnGrid = position + (direction.step * ndx)
            Pair(positionOnGrid,letter.toString())}
        .any{(position,letter) ->
            containsKey(position)  && this[position] != letter  }

fun WordSearchGrid.addClue(clue:String, position:Position, direction:Direction) =
    clue.toList()
        .forEachIndexed { ndx, letter ->
            val positionOnGrid = position + (direction.step * ndx)
            this[positionOnGrid] = letter.toString()}

fun String.willFit(startPosition: Position, direction: Direction): Boolean {
    val endPosition= startPosition + (direction.step * (length - 1))
    return startPosition.isValid && endPosition.isValid
}

fun WordSearchGrid.placeClueOnGrid(clue:String, positions:List<Position>? = null, directions:List<Direction>? = null) {
    val randomPositions = positions ?: Position.inRandomOrder()
    val randomDirections = directions ?: Direction.inRandomOrder()

    val validPositionsAndDirections = randomPositions.asSequence().flatMap { position ->
        randomDirections.asSequence().mapNotNull { direction ->
            if (clue.willFit(position, direction)) {
                if (clueConflictsWithGrid(clue, position, direction)) null else Pair(position, direction)
            } else null
        }
    }.take(1)
    if (validPositionsAndDirections.toList().isNotEmpty()) {
        val (validPosition, validDirection) = validPositionsAndDirections.first()
        addClue(clue, validPosition, validDirection)
    }
}

fun WordSearchGrid.placeCluesOnGrid(listOfClues:List<String>, positions:List<Position>? = null, directions:List<Direction>? = null) {
    listOfClues.forEach { clue ->
        placeClueOnGrid(clue, positions, directions)
    }
}

fun createPuzzle(locations: String, randomLetter:()->String = RANDOMLETTERS):Pair<List<String>,List<String>> {
    val wordSeachGrid:WordSearchGrid = mutableMapOf()
    val clues = parseIntoClues(locations).shuffled().take(10)
    wordSeachGrid.placeCluesOnGrid(clues)
    val puzzle = (0 until SIZE).map { wordSeachGrid[it.asPosition] ?: randomLetter()}
    return Pair(puzzle, clues)
}

fun printResult(listOfLetters:List<String>, clues:List<String>){
    println("Puzzle and clues")
    println("----------------")
    val rows = listOfLetters.chunked(WIDTH)
    rows.forEach { row -> println(row.fold(""){a,e -> a + e}) }

    println("\n Clues:")
    println(clues)

}

