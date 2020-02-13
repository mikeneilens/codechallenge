import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    val easyProblem = listOf(
        7,0,9,0,0,2,6,8,0,
        0,0,2,0,5,0,7,0,4,
        0,0,0,0,0,0,2,0,0,
        1,9,0,0,0,7,0,6,0,
        8,6,7,1,9,5,0,4,0,
        5,0,4,0,0,0,0,9,0,
        4,3,5,7,8,0,0,2,0,
        0,0,6,4,0,0,0,0,1,
        9,8,0,5,0,6,0,0,3)

    @Test
    fun `numberInRow should return the numbers in a row`() {
        val result = easyProblem.numbersInRow(1)
        val expectedResult = setOf(0,0,2,0,5,0,7,0,4)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `numbersInCol should return the numbers in a column`() {
        val result = easyProblem.numbersInCol(1)
        val expectedResult = setOf(0,0,0,9,6,0,3,0,8)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `region for indexes are calculated correctly`() {
        assertEquals(0,20.region())
        assertEquals(1,21.region())
        assertEquals(4,30.region())
        assertEquals(8,80.region())
    }
    @Test
    fun `numbersInRefion should return the numbers in the region`() {
        val result0 = easyProblem.numbersInRegion(0)
        val expectedResult0 = setOf(7,0,9,0,0,2,0,0,0)
        assertEquals(expectedResult0,result0)

        val result4 = easyProblem.numbersInRegion(4)
        val expectedResult4 = setOf(0,0,7,1,9,5,0,0,0)
        assertEquals(expectedResult4,result4)

        val result8 = easyProblem.numbersInRegion(8)
        val expectedResult8 = setOf(0,2,0,0,0,1,0,0,3)
        assertEquals(expectedResult8,result8)
    }
    @Test
    fun `nmubersAlreadyUsed should be all of the numbers already in a column, row and region of an index `() {
        val result = easyProblem.numbersAlreadyUsed(1)
        val expectedResult = setOf(0, 9, 6, 3, 8, 7, 2)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `potentialNumbers should be any numbers between 1 and 9 not already used`() {
        val result = easyProblem.potentialNumbers(1)
        val expectedResult = setOf(1,4,5)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `number to replace zero is the only potential number or else its zero`() {
        val zeroResult = easyProblem.numberToReplaceZero(1)
        val nonZeroResult = easyProblem.numberToReplaceZero(42)

        assertEquals(0, zeroResult)
        assertEquals(3, nonZeroResult)
    }
    @Test
    fun `replacing zeros with numbers after one pass gives a correct sudoku`() {
        val result = easyProblem.replaceZeros()
        val expectedResult = listOf(
            7,0,9,3,0,2,6,8,5,
            0,1,2,0,5,0,7,0,4,
            0,0,0,0,0,0,2,0,0,
            1,9,3,0,0,7,0,6,0,
            8,6,7,1,9,5,3,4,2,
            5,2,4,0,0,0,0,9,0,
            4,3,5,7,8,0,9,2,0,
            2,0,6,4,0,0,0,0,1,
            9,8,1,5,0,6,4,7,3)

        assertEquals(expectedResult,result)
    }

    @Test
    fun `completeSudoku should complete it or return one with zeros for places where it is not possible to complete without exploring permutations`() {
        val result = easyProblem.completeSoduku()
        val expectedResult = listOf(
            7,4,9,3,1,2,6,8,5,
            6,1,2,9,5,8,7,3,4,
            3,5,8,6,7,4,2,1,9,
            1,9,3,2,4,7,5,6,8,
            8,6,7,1,9,5,3,4,2,
            5,2,4,8,6,3,1,9,7,
            4,3,5,7,8,1,9,2,6,
            2,7,6,4,3,9,8,5,1,
            9,8,1,5,2,6,4,7,3
        )
        assertEquals(expectedResult,result)
    }
    @Test
    fun `completeAllSudoku should give an empty list if there are no solutions`() {
        val unsolvable = listOf(
            7,8,1,5,4,3,9,2,6,
            0,0,6,1,7,9,5,0,0,
            9,5,4,6,2,8,7,3,1,
            6,9,5,8,3,7,2,1,4,
            1,4,8,2,6,5,3,7,9,
            3,2,7,9,1,4,8,0,0,
            4,1,3,7,5,2,6,9,8,
            0,0,2,0,0,0,4,0,0,
            5,7,9,4,8,6,1,0,3
        )
        val result = unsolvable.completeAllSodoku()
        val expectedResult = listOf<SudokuGrid>()
        assertEquals(expectedResult,result)
    }

    @Test
    fun `completeAllSudoku should solve a hard problem with one answer`() {
        val hardProblem = listOf(
            0,0,0,0,7,4,3,1,6,
            0,0,0,6,0,3,8,4,0,
            0,0,0,0,0,8,5,0,0,
            7,2,5,8,0,0,0,3,4,
            0,0,0,0,3,0,0,5,0,
            0,0,0,0,0,2,7,9,8,
            0,0,8,9,4,0,0,0,0,
            0,4,0,0,8,5,9,0,0,
            9,7,1,3,2,6,4,8,5
        )
        val result = hardProblem.completeAllSodoku()
        val expectedResult = listOf(listOf(
            5,8,9,2,7,4,3,1,6,
            2,1,7,6,5,3,8,4,9,
            4,6,3,1,9,8,5,2,7,
            7,2,5,8,1,9,6,3,4,
            8,9,6,4,3,7,1,5,2,
            1,3,4,5,6,2,7,9,8,
            6,5,8,9,4,1,2,7,3,
            3,4,2,7,8,5,9,6,1,
            9,7,1,3,2,6,4,8,5
        ))
        assertEquals(expectedResult,result)
    }
    @Test
    fun `completeAllSudoku should solve a hard problem with more than one answer`() {
        val multipleAnswerProblem = listOf(
            0,8,0,0,0,9,7,4,3,
            0,5,0,0,0,8,0,1,0,
            0,1,0,0,0,0,0,0,0,
            8,0,0,0,0,5,0,0,0,
            0,0,0,8,0,4,0,0,0,
            0,0,0,3,0,0,0,0,6,
            0,0,0,0,0,0,0,7,0,
            0,3,0,5,0,0,0,8,0,
            9,7,2,4,0,0,0,5,0
        )

        val results = multipleAnswerProblem.completeAllSodoku()

        results.forEachIndexed { index, result ->
            assertTrue(result.isLegitimate())
        }

    }
}



