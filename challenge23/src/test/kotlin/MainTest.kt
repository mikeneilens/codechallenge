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
    fun `stringIntoPositions should create a list of positions going from left to right when starting position is 1,1 and direction is HorizontalRight`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(1,1),Direction.HorizonalRight)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(1,1)), result[0])
        assertEquals(WordPosition("S",Position(2,1)), result[1])
        assertEquals(WordPosition("H",Position(3,1)), result[2])
        assertEquals(WordPosition("O",Position(4,1)), result[3])
        assertEquals(WordPosition("P",Position(5,1)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going from right to left when starting position is 13,1 and direction is HorizontalLeft`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(13,1),Direction.HorizontalLeft)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(13,1)), result[0])
        assertEquals(WordPosition("S",Position(12,1)), result[1])
        assertEquals(WordPosition("H",Position(11,1)), result[2])
        assertEquals(WordPosition("O",Position(10,1)), result[3])
        assertEquals(WordPosition("P",Position(9,1)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going from top to botton when starting position is 1,1 and direction is VerticalDown`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(1,1),Direction.VerticalDown)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(1,1)), result[0])
        assertEquals(WordPosition("S",Position(1,2)), result[1])
        assertEquals(WordPosition("H",Position(1,3)), result[2])
        assertEquals(WordPosition("O",Position(1,4)), result[3])
        assertEquals(WordPosition("P",Position(1,5)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going from botton to top when starting position is 1,13 and direction is VerticalUp`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(1,13),Direction.VerticalUp)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(1,13)), result[0])
        assertEquals(WordPosition("S",Position(1,12)), result[1])
        assertEquals(WordPosition("H",Position(1,11)), result[2])
        assertEquals(WordPosition("O",Position(1,10)), result[3])
        assertEquals(WordPosition("P",Position(1,9)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going diagonal going right and down when starting position is 1,1 and direction is DiagonalDownRight`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(1,1),Direction.DiagonalDownRight)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(1,1)), result[0])
        assertEquals(WordPosition("S",Position(2,2)), result[1])
        assertEquals(WordPosition("H",Position(3,3)), result[2])
        assertEquals(WordPosition("O",Position(4,4)), result[3])
        assertEquals(WordPosition("P",Position(5,5)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going diagonal going left and down when starting position is 13,1 and direction is DiagonalDownLeft`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(13,1),Direction.DiagonalDownLeft)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(13,1)), result[0])
        assertEquals(WordPosition("S",Position(12,2)), result[1])
        assertEquals(WordPosition("H",Position(11,3)), result[2])
        assertEquals(WordPosition("O",Position(10,4)), result[3])
        assertEquals(WordPosition("P",Position(9,5)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going diagonal going right and up when starting position is 1,13 and direction is DiagonalUpRight`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(1,13),Direction.DiagonalUpRight)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(1,13)), result[0])
        assertEquals(WordPosition("S",Position(2,12)), result[1])
        assertEquals(WordPosition("H",Position(3,11)), result[2])
        assertEquals(WordPosition("O",Position(4,10)), result[3])
        assertEquals(WordPosition("P",Position(5,9)), result[4])
    }
    @Test
    fun `stringIntoPositions should create a list of positions going diagonal going left and up when starting position is 13,13 and direction is DiagonalUpLeft`() {
        val result:List<WordPosition> = stringIntoPositions("ASHOP",Position(13,13),Direction.DiagonalUpLeft)
        assertEquals(5, result.size)
        assertEquals(WordPosition("A",Position(13,13)), result[0])
        assertEquals(WordPosition("S",Position(12,12)), result[1])
        assertEquals(WordPosition("H",Position(11,11)), result[2])
        assertEquals(WordPosition("O",Position(10,10)), result[3])
        assertEquals(WordPosition("P",Position(9,9)), result[4])
    }

    @Test
    fun `isOutOfBounds should give false if the position of every word position for a word is between 0 and 13`() {
        val word = Word(listOf(
            WordPosition("A",Position(0,1)),
            WordPosition("S",Position(2,13)),
            WordPosition("H",Position(3,1)),
            WordPosition("O",Position(4,0)),
            WordPosition("P",Position(13,1))))
            assertFalse(isOutOfBounds(word))
    }
    @Test
    fun `isOutOfBounds should give true if the x position of any word position for a word is not between 0 and 13`() {
        val word = Word(listOf(
            WordPosition("A",Position(1,1)),
            WordPosition("S",Position(2,1)),
            WordPosition("H",Position(14,1)),
            WordPosition("O",Position(4,1)),
            WordPosition("P",Position(5,1))))
           assertTrue(isOutOfBounds(word))
    }
    @Test
    fun `isOutOfBounds should give true if the y position of any word position for a word is not between 0 and 13`() {
        val word = Word(listOf(
            WordPosition("A",Position(1,1)),
            WordPosition("S",Position(2,1)),
            WordPosition("H",Position(3,-1)),
            WordPosition("O",Position(4,1)),
            WordPosition("P",Position(5,1))))
        assertTrue(isOutOfBounds(word))
    }
    @Test
    fun `doesNotOverlap should give true if the word does not have a different letter in the same position as any letter in a list of words`() {
        val word = Word(listOf(
            WordPosition("A",Position(1,3)),
            WordPosition("S",Position(2,3)),
            WordPosition("H",Position(3,3)),
            WordPosition("O",Position(4,3)),
            WordPosition("P",Position(5,3))))

        val word1 = Word(listOf(
            WordPosition("A",Position(1,3)),
            WordPosition("S",Position(1,4)),
            WordPosition("H",Position(1,5)),
            WordPosition("O",Position(1,6)),
            WordPosition("P",Position(1,7)),
            WordPosition("1",Position(1,8))))

        val word2 = Word(listOf(
            WordPosition("A",Position(1,1)),
            WordPosition("S",Position(2,2)),
            WordPosition("H",Position(3,3)),
            WordPosition("O",Position(4,4)),
            WordPosition("P",Position(5,5)),
            WordPosition("2",Position(6,6))))
        val result:Boolean = doesNotOverlap(word, listOf(word1,word2))
        assertTrue(result)
    }
    fun `doesNotOverlap should give false if the word does have a different letter in the same position as any letter in a list of words`() {
        val word = Word(listOf(
            WordPosition("A",Position(1,3)),
            WordPosition("S",Position(2,3)),
            WordPosition("H",Position(3,3)),
            WordPosition("O",Position(4,3)),
            WordPosition("P",Position(5,3))))

        val word1 = Word(listOf(
            WordPosition("A",Position(1,3)),
            WordPosition("S",Position(1,4)),
            WordPosition("H",Position(1,5)),
            WordPosition("O",Position(1,6)),
            WordPosition("P",Position(1,7)),
            WordPosition("1",Position(1,8))))

        val word2 = Word(listOf(
            WordPosition("A",Position(1,1)),
            WordPosition("S",Position(2,2)),
            WordPosition("T",Position(3,3)), //this letter conflicts
            WordPosition("O",Position(4,4)),
            WordPosition("P",Position(5,5)),
            WordPosition("2",Position(6,6))))
        val result:Boolean = doesNotOverlap(word, listOf(word1,word2))
        assertFalse(result)
    }

}