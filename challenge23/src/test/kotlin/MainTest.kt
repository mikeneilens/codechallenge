import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `parseLocations should return empty list is string is empty`() {
        val result = parseLocation("");
        assertEquals(0, result.size)
    }
    @Test
    fun `parseLocations should return one location if string has one location in it`() {
        val result = parseLocation("shop,AB12 1XY,0.0,51.0");
        assertEquals(1, result.size)
        assertEquals(listOf("SHOP"), result)
    }
    @Test
    fun `parseLocations should return one location with no spaces in it if string has one location with spaces in the name of the location`() {
        val result = parseLocation("a shop,AB12 1XY,0.0,51.0");
        assertEquals(1, result.size)
        assertEquals(listOf("ASHOP"), result)
    }
    @Test
    fun `parseLocations should return two locations if the string has two locations in it`() {
        val result = parseLocation("a shop,AB12 1XY,0.0,51.0,next shop,AB12 1XY,0.0,51.0");
        assertEquals(2, result.size)
        assertEquals(listOf("ASHOP","NEXTSHOP"), result)
    }
    @Test
    fun `parseLocations should return locations with size no bigger than 10 chars if the string has several locations in it with some of length bigger than 10`() {
        val result = parseLocation("a shop,AB12 1XY,0.0,51.0," +
                "a very long shop name,AB12 1XY,0.0,51.0," +
                "another shop,AB12 1XY,0.0,51.0," +
                "small  shop,AB12 1XY,0.0,51.0");
        assertEquals(2, result.size)
        assertEquals(listOf("ASHOP","SMALLSHOP"), result)
    }

    @Test
    fun `String toLettersWithPosition should create a list of positions going from left to right when starting position is 1,1 and direction is HorizontalRight`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(1,1),Direction.HorizontalRight)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(1,1)), result[0])
        assertEquals(LetterWithPosition("S",Position(2,1)), result[1])
        assertEquals(LetterWithPosition("H",Position(3,1)), result[2])
        assertEquals(LetterWithPosition("O",Position(4,1)), result[3])
        assertEquals(LetterWithPosition("P",Position(5,1)), result[4])
    }
    @Test
    fun `String toLettersWithPosition should create a list of positions going from right to left when starting position is 13,1 and direction is HorizontalLeft`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(13,1),Direction.HorizontalLeft)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(13,1)), result[0])
        assertEquals(LetterWithPosition("S",Position(12,1)), result[1])
        assertEquals(LetterWithPosition("H",Position(11,1)), result[2])
        assertEquals(LetterWithPosition("O",Position(10,1)), result[3])
        assertEquals(LetterWithPosition("P",Position(9,1)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going from top to botton when starting position is 1,1 and direction is VerticalDown`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(1,1),Direction.VerticalDown)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(1,1)), result[0])
        assertEquals(LetterWithPosition("S",Position(1,2)), result[1])
        assertEquals(LetterWithPosition("H",Position(1,3)), result[2])
        assertEquals(LetterWithPosition("O",Position(1,4)), result[3])
        assertEquals(LetterWithPosition("P",Position(1,5)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going from botton to top when starting position is 1,13 and direction is VerticalUp`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(1,13),Direction.VerticalUp)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(1,13)), result[0])
        assertEquals(LetterWithPosition("S",Position(1,12)), result[1])
        assertEquals(LetterWithPosition("H",Position(1,11)), result[2])
        assertEquals(LetterWithPosition("O",Position(1,10)), result[3])
        assertEquals(LetterWithPosition("P",Position(1,9)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going diagonal going right and down when starting position is 1,1 and direction is DiagonalDownRight`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(1,1),Direction.DiagonalDownRight)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(1,1)), result[0])
        assertEquals(LetterWithPosition("S",Position(2,2)), result[1])
        assertEquals(LetterWithPosition("H",Position(3,3)), result[2])
        assertEquals(LetterWithPosition("O",Position(4,4)), result[3])
        assertEquals(LetterWithPosition("P",Position(5,5)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going diagonal going left and down when starting position is 13,1 and direction is DiagonalDownLeft`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(13,1),Direction.DiagonalDownLeft)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(13,1)), result[0])
        assertEquals(LetterWithPosition("S",Position(12,2)), result[1])
        assertEquals(LetterWithPosition("H",Position(11,3)), result[2])
        assertEquals(LetterWithPosition("O",Position(10,4)), result[3])
        assertEquals(LetterWithPosition("P",Position(9,5)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going diagonal going right and up when starting position is 1,13 and direction is DiagonalUpRight`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(1,13),Direction.DiagonalUpRight)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(1,13)), result[0])
        assertEquals(LetterWithPosition("S",Position(2,12)), result[1])
        assertEquals(LetterWithPosition("H",Position(3,11)), result[2])
        assertEquals(LetterWithPosition("O",Position(4,10)), result[3])
        assertEquals(LetterWithPosition("P",Position(5,9)), result[4])
    }
    @Test
    fun `string toLettersWithPosition should create a list of positions going diagonal going left and up when starting position is 13,13 and direction is DiagonalUpLeft`() {
        val result:List<LetterWithPosition> = "ASHOP".toLettersWithPosition(Position(13,13),Direction.DiagonalUpLeft)
        assertEquals(5, result.size)
        assertEquals(LetterWithPosition("A",Position(13,13)), result[0])
        assertEquals(LetterWithPosition("S",Position(12,12)), result[1])
        assertEquals(LetterWithPosition("H",Position(11,11)), result[2])
        assertEquals(LetterWithPosition("O",Position(10,10)), result[3])
        assertEquals(LetterWithPosition("P",Position(9,9)), result[4])
    }

    @Test
    fun `doesNotConflict should give true if the list of words is empty`() {
        val word = Word(listOf(
            LetterWithPosition("A",Position(1,3)),
            LetterWithPosition("S",Position(2,3)),
            LetterWithPosition("H",Position(3,3)),
            LetterWithPosition("O",Position(4,3)),
            LetterWithPosition("P",Position(5,3))))
        val result = word.doesNotConflictWith(listOf())
        assertTrue(result)
    }
        @Test
    fun `doesNotConflict should give true if the word does not have a different letter in the same position as any letter in a list of words`() {
        val word = Word(listOf(
            LetterWithPosition("A",Position(1,3)),
            LetterWithPosition("S",Position(2,3)),
            LetterWithPosition("H",Position(3,3)),
            LetterWithPosition("O",Position(4,3)),
            LetterWithPosition("P",Position(5,3))))

        val word1 = Word(listOf(
            LetterWithPosition("A",Position(1,3)),
            LetterWithPosition("S",Position(1,4)),
            LetterWithPosition("H",Position(1,5)),
            LetterWithPosition("O",Position(1,6)),
            LetterWithPosition("P",Position(1,7)),
            LetterWithPosition("1",Position(1,8))))

        val word2 = Word(listOf(
            LetterWithPosition("A",Position(1,1)),
            LetterWithPosition("S",Position(2,2)),
            LetterWithPosition("H",Position(3,3)),
            LetterWithPosition("O",Position(4,4)),
            LetterWithPosition("P",Position(5,5)),
            LetterWithPosition("2",Position(6,6))))
        val result:Boolean = word.doesNotConflictWith(listOf(word1,word2))
        assertTrue(result)
    }
    fun `doesNotConflict should give false if the word does have a different letter in the same position as any letter in a list of words`() {
        val word = Word(listOf(
            LetterWithPosition("A",Position(1,3)),
            LetterWithPosition("S",Position(2,3)),
            LetterWithPosition("H",Position(3,3)),
            LetterWithPosition("O",Position(4,3)),
            LetterWithPosition("P",Position(5,3))))

        val word1 = Word(listOf(
            LetterWithPosition("A",Position(1,3)),
            LetterWithPosition("S",Position(1,4)),
            LetterWithPosition("H",Position(1,5)),
            LetterWithPosition("O",Position(3,3)), //this letter conflicts
            LetterWithPosition("P",Position(1,7)),
            LetterWithPosition("1",Position(1,8))))

        val word2 = Word(listOf(
            LetterWithPosition("A",Position(1,1)),
            LetterWithPosition("S",Position(2,2)),
            LetterWithPosition("H",Position(3,3)),
            LetterWithPosition("O",Position(4,4)),
            LetterWithPosition("P",Position(5,5)),
            LetterWithPosition("2",Position(6,6))))
        val result:Boolean = word.doesNotConflictWith(listOf(word1,word2))
        assertFalse(result)
    }

    @Test
    fun `plainTextInRandomOrder should return a list of locations in the order specified by the random generator`() {
        val locations = "a shop,AB12 1XY,0.0,51.0,shop two,AB12 1XY,0.0,51.0,shop three,AB12 1XY,0.0,51.0"
        val randomGenerator = listOf(1,0,2)
        val result = plainTextInRandomOrder(locations, randomGenerator)
        assertEquals(listOf("SHOPTWO","ASHOP","SHOPTHREE"), result )
    }

    @Test
    fun `String willFit returns true if the word can be positioned on the grid in the direction supplied()`() {
        val plainText = "SHOPTWO"
        assertTrue(plainText.willFit(Position(7,1), Direction.HorizontalRight ))
        assertTrue(plainText.willFit(Position(6,1), Direction.HorizontalLeft ))
        assertTrue(plainText.willFit(Position(1,7), Direction.VerticalDown ))
        assertTrue(plainText.willFit(Position(1,6), Direction.VerticalUp ))
    }
    @Test
    fun `String willFit returns false if the word cannot be positioned on the grid in the direction supplied()`() {
        val plainText = "SHOPTWO"
        assertFalse(plainText.willFit(Position(8,1), Direction.HorizontalRight ))
        assertFalse(plainText.willFit(Position(5,1), Direction.HorizontalLeft ))
        assertFalse(plainText.willFit(Position(1,8), Direction.VerticalDown ))
        assertFalse(plainText.willFit(Position(1,5), Direction.VerticalUp ))
    }
    @Test
    fun `positionsInRandomOrder returns a list of positions in the order set by the random number generator`() {
        val randomGenerator = listOf(1,0,2)
        val result = positionsInRandomOrder(randomGenerator)
        assertEquals(Position(1,0), result[0])
        assertEquals(Position(0,0), result[1])
        assertEquals(Position(2,0), result[2])
    }
    @Test
    fun `directionsInRandomOrder returns a list of directions in the order set by the random number generator`() {
        val randomGenerator = listOf(1,0,2)
        val result = directionsInRandomOrder(randomGenerator)
        assertEquals(Direction.HorizontalLeft, result[0])
        assertEquals(Direction.HorizontalRight, result[1])
        assertEquals(Direction.VerticalDown, result[2])
    }
    @Test
    fun `placeWords places a word in a random place when quantity to place is 1`() {
        val listOfWords = placeWords("a shop,AB12 1XY,0.0,51.0,shop two,AB12 1XY,0.0,51.0,shop three,AB12 1XY,0.0,51.0" ,1)
        assertEquals(1, listOfWords.size)
    }
    @Test
    fun `placeWords places two words in a random place when quantity to place is 2`() {
        val listOfWords = placeWords("a shop,AB12 1XY,0.0,51.0,shop two,AB12 1XY,0.0,51.0,shop three,AB12 1XY,0.0,51.0" ,2)
        assertEquals(2, listOfWords.size)
    }
    @Test
    fun `placeWords places ten words in a random place when quantity to place is 10`() {
        val listOfWords = placeWords(testData ,10)
        assertEquals(10, listOfWords.size)

        val words = placeWords(testData)

        printResult(createPuzzle(words).first, words.map{it.text})

        println("\n The answer:")
        printResult(createPuzzle(words){"."}.first,words.map{it.text})
    }

    @Test
    fun `createWordSearch creates a wordSearcg`() {
        val (puzzle, clues) = createWordSearch(testData)
        printResult(puzzle, clues)
    }
}