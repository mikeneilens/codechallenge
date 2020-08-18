import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `textNumberAfter index position 0 is 1point234`() {
        val result = "+1.234".textNumberAfter(0)
        assertEquals("1.234", result)
    }
    @Test
    fun `textNumberAfter index position 0 is error if index at end of string`() {
        val result = "+".textNumberAfter(0)
        assertEquals("", result)
    }
    @Test
    fun `textNumberAfter index position 0 is error if no number`() {
        val result = "+ ".textNumberAfter(0)
        assertEquals("", result)
    }
    @Test
    fun `textNumberAfter index position 0 is 1point234 if next number is not the last in the string`() {
        val result = "+1.234X4.567".textNumberAfter(0)
        assertEquals("1.234", result)
    }
    @Test
    fun `textNumberBefore index position 5 is 1point234`() {
        val result = "1.234+".textNumberBefore(5)
        assertEquals("1.234", result)
    }
    @Test
    fun `textNumberBefore index position 12 is 1point234 when there is another number before it`() {
        val result = "3.4567X1.234".textNumberBefore(12)
        assertEquals("1.234", result)
    }
    @Test
    fun `numberBeFore index position 5 is error if no number`() {
        val result = "ABCDE+".textNumberAfter(5)
        assertEquals("", result)
    }
    @Test
    fun `numberBeFore index position 0 is error if no number`() {
        val result = "+".textNumberAfter(0)
        assertEquals("", result)
    }

    @Test
    fun `calculating "1+2" should give 3`() {
        val result = "1+2".calculate()
        assertTrue(result is Success)
        assertEquals(3.0, (result as Success).value)
    }
    @Test
    fun `calculating "1+2+3" should give 6`() {
        val result = "1+2+3".calculate()
        assertTrue(result is Success)
        assertEquals(6.0, (result as Success).value)
    }
    @Test
    fun `calculating "3+2-1" should give 4`() {
        val result = "3+2-1".calculate()
        assertTrue(result is Success)
        assertEquals(4.0, (result as Success).value)
    }
    @Test
    fun `calculating 3 X 2 - 1 the result is 5`() {
        val result = "3 X 2 - 1".calculate()
        assertTrue(result is Success)
        assertEquals(5.0, (result as Success).value)
    }
    @Test
    fun `calculating 3 X 4 divide 2 + 1 the result is 7`() {
        val result = "3 X 4 / 2 + 1".calculate()
        assertTrue(result is Success)
        assertEquals(7.0, (result as Success).value)
    }
    @Test
    fun `caclulating 3 + 2 X 4 the result is 11`() {
        val result = "3+2X4".calculate()
        assertTrue(result is Success)
        assertEquals(11.0, (result as Success).value)
    }
    @Test
    fun `caclulating 5 ** 3 the result is 125`() {
        val result = "5 ** 3".calculate()
        assertTrue(result is Success)
        assertEquals(125.0, (result as Success).value)
    }
    @Test
    fun `caclulating 3 + 2  plus 3 the result is error`() {
        val result = "3 + 2 plus 3".calculate()
        assertTrue(result is InvalidNumber)
        assertEquals("java.lang.NumberFormatException: For input string: \"5.0plus3\"", (result as InvalidNumber).message)
    }
    @Test
    fun `caclulating 5 plus  the result is error`() {
        val result = "5 + ".calculate()
        assertTrue(result is InvalidNumber)
        assertEquals("java.lang.NumberFormatException: empty String", (result as InvalidNumber).message)
    }
    @Test
    fun `caclulating minus 20  the result is minus 20`() {
        val result = "- 20 ".calculate()
        assertTrue(result is Success)
        assertEquals(-20.0, (result as Success).value)
    }
    // ++++++++++++++++++++++++++++++++++ Bonus challenge ++++++++++++++++++++++//
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
    fun `when string contains 20 between parenthesis the result is 20`() {
        val result = "(20)".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 20 between nested parenthesis the result is 20`() {
        val result = "((20))".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis the result is 5`() {
        val result = "(2 + 3)".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(5.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 plus 3 between nested parenthesis the result is failure`() {
        val result = "(2 plus 3)".calculateWithParenthesis()
        assertTrue(result is InvalidNumber)
        assertEquals("java.lang.NumberFormatException: For input string: \"2plus3\"", (result as InvalidNumber).message)
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis multiplied by 3  the result is 15`() {
        val result = "(2 + 3) X 3".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(15.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis multiplied by 1 + 3 in parenthesis  the result is 20`() {
        val result = "(2 + 3) X (1 + 3)".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthesis multiplied by 2 + 1 in parenthesis  the result is 125`() {
        val result = "5 ** ((2 - 1) X (1 + 2))".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(125.0, (result as Success).value )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthesis and then multiplied by 2 + 1 in parenthesis  the result is 15`() {
        val result = "5 ** (2 - 1) X (1 + 2)".calculateWithParenthesis()
        assertTrue(result  is Success)
        assertEquals(15.0, (result as Success).value )
    }
}