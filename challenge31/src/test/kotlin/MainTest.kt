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
    fun `when string contains 20 the result is 20`() {
        val expression = "20"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(20.0, result.getOrNull() )
    }
    @Test
    fun `when expression contains a + prefix it is prefixed by 0`() {
        val expression = "+20"
        val result = expression.withZeroPrefix()
        assertEquals("0+20",result)
    }
    @Test
    fun `when string contains +20 the result is 20`() {
        val expression = "+20"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(20.0, result.getOrNull() )
    }
    @Test
    fun `when string contains -20 the result is -20`() {
        val expression = "-20"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(-20.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 5 ** 3 the result is 125`() {
        val expression = "5 ** 3"
        val result = calculate(expression)
        assertTrue(result. isSuccess)
        assertEquals(125.0, result.getOrNull() )
    }

    @Test
    fun `when string contains "(abc)" indexOfFirstBefore(4,'(') is 0`() {
        val result = "(abc)".indexOfFirstBefore(4){it=='('}
        assertEquals(0,result)
    }
    @Test
    fun `when string contains "abc)" indexOfFirstBefore(4,'(') is -1`() {
        val result = "abc)".indexOfFirstBefore(4){it=='('}
        assertEquals(-1,result)
    }
    @Test
    fun `when string contains "(abc)(abc)" indexOfFirstBefore(9,'(') is 5`() {
        val result = "(abc)(abc)".indexOfFirstBefore(9){it=='('}
        assertEquals(5,result)
    }

    @Test
    fun `when string contains 20 between parenthisis the result is 20`() {
        val expression = "(20)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(20.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 20 between nested parenthisis the result is 20`() {
        val expression = "((20))"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(20.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthisis the result is 5`() {
        val expression = "(2 + 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(5.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 2 plus 3 between nested parenthisis the result is failure`() {
        val expression = "(2 plus 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isFailure)
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthisis multiplied by 3  the result is 15`() {
        val expression = "(2 + 3) X 3"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(15.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthisis multiplied by 1 + 3 in parenthesis  the result is 20`() {
        val expression = "(2 + 3) X (1 + 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(20.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthisis multiplied by 2 + 1 in parenthesis  the result is 125`() {
        val expression = "5 ** ((2 - 1) X (1 + 2))"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(125.0, result.getOrNull() )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthisis and then multiplied by 2 + 1 in parenthesis  the result is 15`() {
        val expression = "5 ** (2 - 1) X (1 + 2)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result. isSuccess)
        assertEquals(15.0, result.getOrNull() )
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