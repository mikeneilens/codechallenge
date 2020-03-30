import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `clueConflictsWithGrid should be false if single letter text is anywhere on the grid`() {
        val wordSearchGrid:WordSearchGrid = mutableMapOf()
        val result1 = wordSearchGrid.clueConflictsWithGrid("A",Position(0,0),Direction.VerticalDown)
        assertFalse(result1)
        val result2 = wordSearchGrid.clueConflictsWithGrid("A",Position(5,5),Direction.HorizontalRight)
        assertFalse(result2)
    }
    @Test
    fun `clueConflictsWithGrid should be true if single letter text is in the same position as a different letter on the grid`() {
        val wordSearchGrid:WordSearchGrid = mutableMapOf(Position(5,6) to "B")
        wordSearchGrid[Position(5,6)] = "B"
        val result = wordSearchGrid.clueConflictsWithGrid("A",Position(5,6),Direction.VerticalDown)
        assertTrue(result)
    }
    @Test
    fun `clueConflictsWithGrid should be false if single letter text is in the same position as the same letter on the grid`() {
        val wordSearchGrid:WordSearchGrid = mutableMapOf(Position(5,6) to "B")
        val result = wordSearchGrid.clueConflictsWithGrid("B",Position(5,6),Direction.VerticalDown)
        assertFalse(result)
    }
    @Test
    fun `clueConflictsWithGrid should be false if two letter text is in the same position as the same two letters on the grid`() {
        val wordSearchGridVD:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(5,7) to "C")
        val resultVD = wordSearchGridVD.clueConflictsWithGrid("BC",Position(5,6),Direction.VerticalDown)
        assertFalse(resultVD)
        val wordSearchGridVU:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(5,5) to "C")
        val resultVU = wordSearchGridVU.clueConflictsWithGrid("BC",Position(5,6),Direction.VerticalUp)
        assertFalse(resultVU)
        val wordSearchGridHL:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(4,6) to "C")
        val resultHL = wordSearchGridHL.clueConflictsWithGrid("BC",Position(5,6),Direction.HorizontalLeft)
        assertFalse(resultHL)
        val wordSearchGridHR:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(6,6) to "C")
        val resultHR = wordSearchGridHR.clueConflictsWithGrid("BC",Position(5,6),Direction.HorizontalRight)
        assertFalse(resultHR)
    }
    @Test
    fun `clueConflictsWithGrid should be false if two letter text is in the same position as the different two letters on the grid`() {
        val wordSearchGridVD:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(5,7) to "D")
        val resultVD = wordSearchGridVD.clueConflictsWithGrid("BC",Position(5,6),Direction.VerticalDown)
        assertTrue(resultVD)
        val wordSearchGridVU:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(5,5) to "D")
        val resultVU = wordSearchGridVU.clueConflictsWithGrid("BC",Position(5,6),Direction.VerticalUp)
        assertTrue(resultVU)
        val wordSearchGridHL:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(4,6) to "D")
        val resultHL = wordSearchGridHL.clueConflictsWithGrid("BC",Position(5,6),Direction.HorizontalLeft)
        assertTrue(resultHL)
        val wordSearchGridHR:WordSearchGrid = mutableMapOf(Position(5,6) to "B" ,Position(6,6) to "D")
        val resultHR = wordSearchGridHR.clueConflictsWithGrid("BC",Position(5,6),Direction.HorizontalRight)
        assertTrue(resultHR)
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
    fun `Positions inRandomOrder returns a list of positions in the order set by the random number generator`() {
        val randomGenerator = listOf(1,0,2)
        val result = Position.inRandomOrder(randomGenerator)
        assertEquals(Position(1,0), result[0])
        assertEquals(Position(0,0), result[1])
        assertEquals(Position(2,0), result[2])
    }
    @Test
    fun `Directions inRandomOrder returns a list of directions in the order set by the random number generator`() {
        val randomGenerator = listOf(1,0,2)
        val result = Direction.inRandomOrder(randomGenerator)
        assertEquals(Direction.HorizontalLeft, result[0])
        assertEquals(Direction.HorizontalRight, result[1])
        assertEquals(Direction.VerticalDown, result[2])
    }
    @Test
    fun `Position plus returns correct position if two are added together`() {
        val result = Position(3,5) + Position(4,6)
        assertEquals(Position(7,11), result)
    }
    @Test
    fun `Position times returns correct position if position is multiplied`() {
        val result = Position(3,5) * 4
        assertEquals(Position(12,20), result)
    }

    @Test
    fun `WordSearchGrid addClue should put a single character on a grid if the text contains one character `() {
        val wordSearchGrid:WordSearchGrid = mutableMapOf()
        wordSearchGrid.addClue("A",Position(5,6),Direction.VerticalUp)
        assertEquals("A", wordSearchGrid[Position(5,6)])
    }

    @Test
    fun `WordSearchGrid addClue should put two characters on a grid if the text contains two characters `() {
        val wordSearchGrid:WordSearchGrid = mutableMapOf()
        wordSearchGrid.addClue("AB",Position(5,6),Direction.VerticalUp)
        assertEquals("A", wordSearchGrid[Position(5,6)])
        assertEquals("B", wordSearchGrid[Position(5,5)])
        wordSearchGrid.addClue("AC",Position(5,6),Direction.VerticalDown)
        assertEquals("A", wordSearchGrid[Position(5,6)])
        assertEquals("C", wordSearchGrid[Position(5,7)])
        wordSearchGrid.addClue("AD",Position(5,6),Direction.HorizontalLeft)
        assertEquals("A", wordSearchGrid[Position(5,6)])
        assertEquals("D", wordSearchGrid[Position(4,6)])
        wordSearchGrid.addClue("AE",Position(5,6),Direction.HorizontalRight)
        assertEquals("A", wordSearchGrid[Position(5,6)])
        assertEquals("E", wordSearchGrid[Position(6,6)])
    }

    @Test
    fun `WordSearchGrid placeClueOnGrid will put single clue onto the grid using first position and first direction if can be placed there `() {
        val clue = "AB"
        val wordSearchGrid:WordSearchGrid = mutableMapOf(Position(4,6) to "X")
        val randomPositions = listOf(Position(5,6))
        val randomDirections = listOf(Direction.HorizontalRight)

        wordSearchGrid.placeClueOnGrid(clue, positions = randomPositions, directions = randomDirections)

        assertEquals("A", wordSearchGrid[Position(5,6)])
        assertEquals("B", wordSearchGrid[Position(6,6)])
    }
    @Test
    fun `WordSearchGrid placeClueOnGrid will put single clue onto the grid using second position and first direction if can be placed on first position `() {
        val clue = "AB"
        val wordSearchGrid:WordSearchGrid = mutableMapOf(Position(5,6) to "X")
        val randomPositions = listOf(Position(5,6), Position(6,6) )
        val randomDirections = listOf(Direction.HorizontalRight)

        wordSearchGrid.placeClueOnGrid(clue, positions = randomPositions, directions = randomDirections)

        assertEquals("A", wordSearchGrid[Position(6,6)])
        assertEquals("B", wordSearchGrid[Position(7,6)])
    }
    @Test
    fun `WordSearchGrid placeClueOnGrid will put single clue onto the grid using second direction and first position if can be placed using first direction `() {
        val clue = "AB"
        val wordSearchGrid:WordSearchGrid = mutableMapOf(Position(5,6) to "X")
        val randomPositions = listOf(Position(4,6) )
        val randomDirections = listOf(Direction.HorizontalRight, Direction.HorizontalLeft)

        wordSearchGrid.placeClueOnGrid(clue, positions = randomPositions, directions = randomDirections)

        assertEquals("A", wordSearchGrid[Position(4,6)])
        assertEquals("B", wordSearchGrid[Position(3,6)])
    }

    @Test
    fun `WordSearchGrid placeCluesOnGrid will put two clues onto the grid using first position for first clue and second position for second clue `() {
        val clues = listOf("AB","XY")
        val wordSearchGrid:WordSearchGrid = mutableMapOf()
        val randomPositions = listOf(Position(4,6), Position(13,3) )
        val randomDirections = listOf(Direction.HorizontalRight, Direction.HorizontalLeft)

        wordSearchGrid.placeCluesOnGrid(clues, positions = randomPositions, directions = randomDirections)

        assertEquals("A", wordSearchGrid[Position(4,6)])
        assertEquals("B", wordSearchGrid[Position(5,6)])

        assertEquals("X", wordSearchGrid[Position(13,3)])
        assertEquals("Y", wordSearchGrid[Position(12,3)])

    }
    @Test
    fun `WordSearchGrid placeClueOnGrid will not put a clue on a grid if there is nowhere for it to go `() {
        val clue = "AB"
        val wordSearchGrid:WordSearchGrid = (0 until SIZE).map{it.asPosition to "X"}.toMap().toMutableMap()
        val randomPositions = listOf(Position(5,6))
        val randomDirections = listOf(Direction.HorizontalRight)

        wordSearchGrid.placeClueOnGrid(clue, positions = randomPositions, directions = randomDirections)
        assertFalse(wordSearchGrid.containsValue("A"))
        assertFalse(wordSearchGrid.containsValue("B"))
    }

    @Test
    fun `createWordSearch and test if all clues can be found`() {
        val (puzzle, clues) = createPuzzle(testData)
        printResult(puzzle, clues)

        val puzzleAsString = puzzle.fold(""){a, e -> a + e}
        val solution = puzzleAsString.findPositionOfEachClue(clues)
        assertEquals(10, solution.size)

        println("The solution found for each clue is")
        solution.forEach { println(it) }

    }
}