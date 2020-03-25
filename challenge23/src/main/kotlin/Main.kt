
data class Position(val x:Int, val y:Int) {

    operator fun plus(other:Position) = Position(this.x + other.x, this.y + other.y)

    operator fun times(num:Int) = Position(this.x * num, this.y * num)

    companion object {
        fun inRandomOrder(randomGenerator: List<Int> = listOf()):List<Position> {
            val positions = (0..195).map{Position(it % 14, it / 14)}
            return if (randomGenerator.isNotEmpty()) randomGenerator.map{positions[it]} else (0..195).shuffled().map{positions[it]}
        }
    }
}

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
            if (randomGenerator.isNotEmpty()) randomGenerator.map { values()[it] } else (0..7).shuffled().map {values()[it] }
    }
}

data class Word(val letters:List<LetterWithPosition>) {
    val text:String  get() {
        return letters.map{it.letter}.fold(""){result,char -> result + char}
    }
    fun letterAtPosition(requiredPosition:Position) = letters.first{letter -> letter.position == requiredPosition}.letter

    fun doesNotConflictWith(listOfWords: List<Word>): Boolean {
        val existingLettersWithPosition = listOfWords.flatMap { word -> word.letters}
        return letters.none{letterWithPosition -> existingLettersWithPosition.any{it.position == letterWithPosition.position && letterWithPosition.letter != it.letter }}
    }
}

data class LetterWithPosition(val letter:String, val position:Position)

fun parseLocation(locationString: String): List<String> {
    return locationString.split(",")
        .chunked(4)
        .map{it[0].replace(" ","")}
        .filter{it.isNotEmpty() && it.length <= 10 }
        .map{it.toUpperCase()}
}

fun String.toLettersWithPosition(startPosition: Position, direction: Direction): List<LetterWithPosition> =
    this.toList()
        .mapIndexed{index, char -> LetterWithPosition(char.toString(), startPosition + (direction.step * index))}


fun plainTextInRandomOrder(locationsData: String, randomGenerator: List<Int>): List<String> {
    val listOfPlainText = parseLocation(locationsData)
    return randomGenerator.map{listOfPlainText[it]}
}

fun String.willFit(startPosition: Position, direction: Direction): Boolean {
    val (endX, endY)= startPosition + (direction.step * (length - 1))
    return (endX in 0..13 && endY in  0..13)
}

fun placeWords(locationString:String, qtyToPlace:Int = 10):List<Word> {
    val numberOfPlainText = parseLocation(locationString).size
    val listOfPlainText = plainTextInRandomOrder(locationString, (0 until numberOfPlainText).shuffled() )

    fun placeWords(listOfPlainText:List<String>, qtyToPlace:Int, listOfWords:List<Word> = mutableListOf()):List<Word> {
        if (listOfWords.size >= qtyToPlace) return listOfWords
        val plainText = listOfPlainText.first()

        val randomPositions = Position.inRandomOrder()
        val randomDirections = Direction.inRandomOrder()

        val possibleWords = (0..195).asSequence().map{ positionNdx ->
            (0..7).asSequence().mapNotNull{ directionNdx ->
                if (plainText.willFit(randomPositions[positionNdx], randomDirections[directionNdx])) {
                    val word = Word(plainText.toLettersWithPosition(randomPositions[positionNdx], randomDirections[directionNdx]))
                    if (word.doesNotConflictWith(listOfWords)) word else null
                } else null
            }
        }.take(1).first().toList()

        return if (possibleWords.isNotEmpty()) placeWords(listOfPlainText.drop(1), qtyToPlace, listOfWords + possibleWords.first())
        else placeWords(listOfPlainText.drop(1), qtyToPlace, listOfWords )
    }
    return placeWords(listOfPlainText,qtyToPlace)
}

val letters = listOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")

fun createPuzzle(listOfWords: List<Word>, randomLetter:()->String = {letters[(0..25).random()]}):Pair<List<String>,List<Word>> {
    val result = mutableListOf<String>()
    for (ndx in (0..195)) {
        val currentPosition = Position(ndx % 14, ndx / 14)
        val word = listOfWords.firstOrNull{word -> word.letters.any{letter -> letter.position == currentPosition} }
        if (word == null) result.add(randomLetter())  else result.add ( word.letterAtPosition(currentPosition))
    }
    return Pair(result, listOfWords)
}

fun createWordSearch(locationString:String,randomLetter:()->String = {letters[(0..25).random()]}):Pair<List<String>, List<String>> {
    val listOfWords = placeWords(locationString)
    val (puzzle, words ) =  createPuzzle(listOfWords,randomLetter)
    return Pair(puzzle, words.map{it.text})
}

fun printResult(listOfLetters:List<String>, clues:List<String>){
    println("Puzzle and clues")
    println("----------------")
    val rows = listOfLetters.chunked(14)
    rows.forEach { row -> println(row.fold(""){a,e -> a + e}) }

    println("\n Clues:")
    println(clues)

}

