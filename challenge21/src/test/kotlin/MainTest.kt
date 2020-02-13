import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val data = listOf(
        7,0,9,0,0,2,6,8,0,
        0,0,2,0,5,0,7,0,4,
        0,0,0,0,0,0,2,0,0,
        1,9,0,0,0,7,0,6,0,
        8,6,7,1,9,5,0,4,0,
        5,0,4,0,0,0,0,9,9,
        4,3,5,7,8,0,0,2,0,
        0,0,6,4,0,0,0,0,1,
        9,8,0,5,0,6,0,0,3)

    @Test
    fun `numberInRow should return the numbers in a row`() {
        val result = data.numbersInRow(1)
        val expectedResult = setOf(0,0,2,0,5,0,7,0,4)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `numbersInCol should return the numbers in a column`() {
        val result = data.numbersInCol(1)
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
        val result0 = data.numbersInRegion(0)
        val expectedResult0 = setOf(7,0,9,0,0,2,0,0,0)
        assertEquals(expectedResult0,result0)

        val result4 = data.numbersInRegion(4)
        val expectedResult4 = setOf(0,0,7,1,9,5,0,0,0)
        assertEquals(expectedResult4,result4)

        val result8 = data.numbersInRegion(8)
        val expectedResult8 = setOf(0,2,0,0,0,1,0,0,3)
        assertEquals(expectedResult8,result8)
    }
    @Test
    fun `nmubersAlreadyUsed should be all of the numbers already in a column, row and region of an index `() {
        val result = data.numbersAlreadyUsed(1)
        val expectedResult = setOf(0, 9, 6, 3, 8, 7, 2)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `potentialNumbers should be any numbers between 1 and 9 not already used`() {
        val result = data.potentialNumbers(1)
        val expectedResult = setOf(1,4,5)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `number to replace zero is the only potential number or else its zero`() {
        val zeroResult = data.numberToReplaceZero(1)
        val nonZeroResult = data.numberToReplaceZero(42)

        assertEquals(0, zeroResult)
        assertEquals(3, nonZeroResult)
    }
    @Test
    fun `replacing zeros with numbers after one pass gives a correct sudoku`() {
        val result = data.replaceZeros()
        val expectedResult = listOf(
            7,0,9,3,0,2,6,8,5,
            0,1,2,0,5,0,7,0,4,
            0,0,0,0,0,0,2,0,5,
            1,9,3,0,0,7,0,6,0,
            8,6,7,1,9,5,3,4,2,
            5,2,4,0,0,0,0,9,9,
            4,3,5,7,8,0,9,2,6,
            2,0,6,4,0,0,0,0,1,
            9,8,1,5,0,6,4,7,3)

        assertEquals(expectedResult,result)
    }
    @Test
    fun `completeSudoku should complete it or return one with zeros for places where it is not possible to complete without exploring permutations`() {
        val result = data.completeSoduku()
        val expectedResult = listOf(
            7,4,9,3,1,2,6,8,5,
            6,1,2,8,5,8,7,3,4,
            3,4,8,6,7,9,2,1,5,
            1,9,3,2,4,7,5,6,8,
            8,6,7,1,9,5,3,4,2,
            5,2,4,8,6,3,1,9,9,
            4,3,5,7,8,1,9,2,6,
            2,7,6,4,3,9,8,5,1,
            9,8,1,5,2,6,4,7,3
        )
        assertEquals(expectedResult,result)
    }
}



