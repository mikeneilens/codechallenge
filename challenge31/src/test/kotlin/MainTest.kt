import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.NumberFormatException

class MainTest {
    @Test
    fun `when string contains 1 + 2 the result is three`(){
        val expression = "1 + 2"
        val result = calculate(expression)

        assertTrue(result.isSuccess)
        assertEquals(3.0, result.getOrNull() )
    }
    @Test
    fun `when string contains "1 plus 2" the result is an exception`(){
        val expression = "1 plus 2"
        val result = calculate(expression)

        assertTrue(result.isFailure)
    }
    @Test
    fun `when string contains 2 - 1 the result is one`(){
        val expression = "2 - 1"
        val result = calculate(expression)

        assertTrue(result. isSuccess)
        assertEquals(1.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 3 + 2 + 1 the result is six`(){
        val expression = "3 + 2 + 1"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(6.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 3 + 2 - 1 the result is four`(){
        val expression = "3 + 2 - 1"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(4.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 3 X 2 - 1 the result is five`(){
        val expression = "3 X 2 - 1"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(5.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 3 X 4 divide 2 + 1 the result is seven`(){
        val expression = "3 X 4 / 2 + 1"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(7.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 3 + 2 X 4 the result is eleven`(){
        val expression = "3 + 2 X 4"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(11.0, result.getOrNull() )
    }

    @Test
    fun `parsing a string containing one plus two gives an array containing MiniExpression(n0,"+"),MiniExpression(n1,"")`() {
        val expression = "1 + 2"
        val result = parse(expression)
        assertEquals(2, result.size)
        assertEquals(1.0, result[0].value)
        assertEquals("+", result[0].operator)
        assertEquals(2.0, result[1].value)
        assertEquals("", result[1].operator)
    }
    @Test
    fun `parsing a string containing one plus two throws a number format exception`() {
        val expression = "1 plus 2"
        assertThrows<NumberFormatException> {parse(expression)}
    }
}