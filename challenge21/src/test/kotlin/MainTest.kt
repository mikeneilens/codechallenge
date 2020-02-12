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
        val expectedResult = listOf(0,0,2,0,5,0,7,0,4)
        assertEquals(expectedResult,result)
    }
    @Test
    fun `numbersInCol should return the numbers in a column`() {
        val result = data.numbersInCol(1)
        val expectedResult = listOf(0,0,0,9,6,0,3,0,8)
    }

}

fun List<Int>.numbersInRow(row: Int)= this.chunked(9)[row]
fun List<Int>.numbersInCol(col: Int)= this.chunked(9).map{it[col]}
