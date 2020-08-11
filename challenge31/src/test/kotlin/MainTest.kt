import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.NumberFormatException

class MainTest {
    @Test
    fun `when string contains 1 + 2 the result is three`(){
        val expression = "1 + 2"
        val result = calculate(expression)

        assertTrue(result is Result.OK)
        assertEquals(3.0, (result as Result.OK).value )
    }
    @Test
    fun `when string contains "1 plus 2" the result is an exception`(){
        val expression = "1 plus 2"
        val result = calculate(expression)

        assertTrue(result is Result.Failed)
    }

    @Test
    fun `parsing a string containing one plus two gives an array containing Pair(n0,"+"),Pair(n1,"")`() {
        val expression = "1 + 2"
        val result = parse(expression)
        assertEquals(2, result.size)
        assertEquals(1.0, result[0].first)
        assertEquals("+", result[0].second)
        assertEquals(2.0, result[1].first)
        assertEquals("", result[1].second)
    }
    @Test
    fun `parsing a string containing one plus two throws a number format exception`() {
        val expression = "1 plus 2"
        assertThrows<NumberFormatException> {parse(expression)}
    }
}