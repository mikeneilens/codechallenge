fun parseLocation(locationString: String): List<String> {
    return locationString.split(",")
        .chunked(4)
        .map{it[0].replace(" ","")}
        .filter{it.isNotEmpty() && it.length <= 10 }
        .map{it.toUpperCase()}
}

data class Position(val x:Int, val y:Int)

enum class Direction(val step:Position) {
    HorizonalRight(Position(1,0)),
    HorizontalLeft(Position(-1,0)),
    VerticalDown(Position(0,1)),
    VerticalUp(Position(0,-1)),
    DiagonalDownRight(Position(1,1)),
    DiagonalDownLeft(Position(-1,1)),
    DiagonalUpRight(Position(1,-1)),
    DiagonalUpLeft(Position(-1,-1)),
}

data class Word(val wordPostions:List<WordPosition>)

data class WordPosition(val letter:String, val position:Position)

fun stringIntoPositions(word: String, startPosition: Position, direction: Direction): List<WordPosition> =
    word.toList()
        .mapIndexed{index, char -> WordPosition(char.toString(),Position(startPosition.x + index * direction.step.x, startPosition.y + index * direction.step.y ))}

fun isOutOfBounds(word: Word): Boolean =
    word.wordPostions.any{ it.position.x < 0 || it.position.x > 13 || it.position.y < 0 || it.position.y > 13 }

fun doesNotOverlap(word: Word, listOfWords: List<Word>): Boolean {
    return word.wordPostions.none{ wordPosition -> listOfWords.none{ wordFromList -> wordFromList.wordPostions.none{ wordFromListWordPosition -> wordPosition.position == wordFromListWordPosition.position &&  wordPosition.letter != wordFromListWordPosition.letter  }  }   }
}
