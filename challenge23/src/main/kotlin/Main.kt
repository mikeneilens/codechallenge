
data class Position(val x:Int, val y:Int)

enum class Direction(val step:Position) {
    HorizontalRight(Position(1,0)),
    HorizontalLeft(Position(-1,0)),
    VerticalDown(Position(0,1)),
    VerticalUp(Position(0,-1)),
    DiagonalDownRight(Position(1,1)),
    DiagonalDownLeft(Position(-1,1)),
    DiagonalUpRight(Position(1,-1)),
    DiagonalUpLeft(Position(-1,-1)),
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
        .mapIndexed{index, char -> LetterWithPosition(char.toString(),Position(startPosition.x + index * direction.step.x, startPosition.y + index * direction.step.y ))}


fun plainTextInRandomOrder(locationsData: String, randomGenerator: List<Int>): List<String> {
    val listOfPlainText = parseLocation(locationsData)
    return randomGenerator.map{listOfPlainText[it]}
}

fun positionsInRandomOrder(randomGenerator: List<Int>):List<Position> {
    val positions = (0..13).flatMap{y -> (0..13).map{x -> Position(x,y)}}
    return randomGenerator.map{positions[it]}
}

fun directionsInRandomOrder(randomGenerator: List<Int>)= randomGenerator.map{Direction.values()[it]}

fun String.willFit(startPosition: Position, direction: Direction): Boolean {
    val endX = startPosition.x + direction.step.x * (this.length - 1)
    val endY = startPosition.y + direction.step.y * (this.length - 1)
    return (endX in 0..13 && endY in  0..13)
}

fun placeWords(locationString:String, qtyToPlace:Int = 10):List<Word> {
    val numberOfPlainText = parseLocation(locationString).size
    val listOfPlainText = plainTextInRandomOrder(locationString, (0 until numberOfPlainText).shuffled() )

    fun placeWords(listOfPlainText:List<String>, qtyToPlace:Int, listOfWords:List<Word> = mutableListOf()):List<Word> {
        if (listOfWords.size >= qtyToPlace) return listOfWords
        val plainText = listOfPlainText.first()

        val randomPositions = positionsInRandomOrder((0..195).shuffled())
        val randomDirections = directionsInRandomOrder((0..7).shuffled())

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
    val rows = listOfLetters.chunked(14)
    rows.forEach { row -> println(row.fold(""){a,e -> a + e}) }

    println("\n Clues:")
    println(clues)

}

