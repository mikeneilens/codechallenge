import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.NumberFormatException

class MainTest {

    @Test
    fun `cloning a list containing one item returns a list containing one item`() {
        val calcNode = CalcNode(1.0, "+", null)
        val clone = calcNode.clone()
        assertEquals(1.0, clone.value)
        assertEquals("+", clone.operator)
        assertTrue(clone != calcNode)
    }
    @Test
    fun `cloning a list containing two item returns a list containing two items`() {
        val calcNode = CalcNode(1.0, "+", CalcNode(2.0,"X"))
        val clone = calcNode.clone()
        val cloneItem2 = clone.nextCalcNode
        assertEquals(1.0, clone.value)
        assertEquals("+", clone.operator)
        assertEquals(2.0, cloneItem2?.value)
        assertEquals("X", cloneItem2?.operator)
        assertTrue(clone != calcNode)
        assertTrue(clone.nextCalcNode != calcNode.nextCalcNode)
    }

    @Test
    fun `when list contains 1 + 2 the result is 3`() {
        val calcNode = CalcNode(1.0,"+") + CalcNode(2.0,"")
        val result = calcNode.calculate()
        assertEquals(3.0, result)
        println("$calcNode=$result")
    }
    @Test
    fun `when list contains 2 - 1 the result is 1`() {
        val calcNode = CalcNode(2.0,"-")+ CalcNode(1.0,"")
        val result = calcNode.calculate()
        assertEquals(1.0, result)
    }
    @Test
    fun `find last calcNode of list containing 2 items`() {
        val calcNode = CalcNode(1.0, "+", CalcNode(2.0, "") )
        assertEquals(2.0, calcNode.findLastNode().value)
    }
    @Test
    fun `find last calcNode of list containing 3 items`() {
        val calcNode = CalcNode(1.0, "+", CalcNode(2.0, "+",CalcNode(3.0, "") ) )
        assertEquals(3.0, calcNode.findLastNode().value)
    }
    @Test
    fun `append calcNode to a list containing 3 items`() {
        val calcNode = CalcNode(1.0, "+", CalcNode(2.0, "+",CalcNode(3.0, "") ) )+ CalcNode(4.0,"")
        assertEquals(4.0, calcNode.findLastNode().value)
    }
    @Test
    fun `when list contains 3 + 2 + 1 the result is six`() {
        val calcNode= CalcNode(3.0, "+")+ CalcNode(2.0,"+")+ CalcNode(1.0,"")
        val result = calcNode.calculate()
        assertEquals(6.0, result)
    }
    @Test
    fun `when list contains 3 + 2 - 1 the result is 4`() {
        val calcNode= CalcNode(3.0, "+")+ CalcNode(2.0,"-")+ CalcNode(1.0,"")
        val result = calcNode.calculate()
        assertEquals(4.0, result)
    }
    @Test
    fun `when list contains 3 X 2 - 1 the result is 5`() {
        val calcNode= CalcNode(3.0, "X")+ CalcNode(2.0,"-")+ CalcNode(1.0,"")
        val result = calcNode.calculate()
        assertEquals(5.0, result)
    }
    @Test
    fun `when list contains 3 X 4 divide 2 + 1 the result is 7`() {
        val calcNode= CalcNode(3.0, "X")+ CalcNode(4.0,"/")+ CalcNode(2.0,"+")+ CalcNode(1.0,"")
        val result = calcNode.calculate()
        assertEquals(7.0, result)
    }
    @Test
    fun `when list contains 3 + 2 X 4 the result is 11`() {
        val calcNode= CalcNode(3.0, "+")+ CalcNode(2.0,"X")+ CalcNode(4.0,"")
        val result = calcNode.calculate()
        assertEquals(11.0, result)
    }
    @Test
    fun `when list contains 5 ** 3 the result is 125`() {
        val calcNode= CalcNode(5.0, "*")+ CalcNode(3.0,"")
        val result = calcNode.calculate()
        assertEquals(125.0, result)
    }
    @Test
    fun `when list contains 20 the result is 20`() {
        val calcNode= CalcNode(20.0, "")
        val result = calcNode.calculate()
        assertEquals(20.0, result)
    }

    @Test
    fun `parsing a string containing one plus two gives a list of Parts`() {
        val expression = "1 + 2"
        val result = parse(expression)
        assertEquals(1.0, result.value)
        assertEquals("+", result.operator)
        assertEquals(2.0, result.nextCalcNode?.value)
        assertEquals("", result.nextCalcNode?.operator)
    }
    @Test
    fun `parsing a string containing two to the power of three gives a list of Parts`() {
        val expression = "2 ** 3"
        val result = parse(expression)
        assertEquals(2.0, result.value)
        assertEquals("*", result.operator)
        assertEquals(3.0, result.nextCalcNode?.value)
        assertEquals("", result.nextCalcNode?.operator)
    }
    @Test
    fun `parsing a string containing one plus two throws a number format exception`() {
        val expression = "1 plus 2"
        assertThrows<NumberFormatException> {parse(expression)}
    }

    @Test
    fun `when string contains 3 X 4 divide 2 + 1 the result is seven`(){
        val expression = "3 X 4 / 2 + 1"
        val result = calculate(expression)

        assertTrue(result  is Success)
        assertEquals(7.0, (result as Success).value )
    }
    @Test
    fun `when string contains +20 the result is 20`(){
        val expression = "+20"
        val result = calculate(expression)

        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains -20 the result is -20`(){
        val expression = "-20"
        val result = calculate(expression)

        assertTrue(result  is Success)
        assertEquals(-20.0, (result as Success).value )
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
        val expression = "(20)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 20 between nested parenthesis the result is 20`() {
        val expression = "((20))"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis the result is 5`() {
        val expression = "(2 + 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(5.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 plus 3 between nested parenthesis the result is failure`() {
        val expression = "(2 plus 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result is InvalidNumber)
        assertEquals("java.lang.NumberFormatException: For input string: \"2plus3\"", (result as InvalidNumber).message)
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis multiplied by 3  the result is 15`() {
        val expression = "(2 + 3) X 3"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(15.0, (result as Success).value )
    }
    @Test
    fun `when string contains 2 + 3 between nested parenthesis multiplied by 1 + 3 in parenthesis  the result is 20`() {
        val expression = "(2 + 3) X (1 + 3)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(20.0, (result as Success).value )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthesis multiplied by 2 + 1 in parenthesis  the result is 125`() {
        val expression = "5 ** ((2 - 1) X (1 + 2))"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(125.0, (result as Success).value )
    }
    @Test
    fun `when string contains 5 ** and 2 - 1 between nested parenthesis and then multiplied by 2 + 1 in parenthesis  the result is 15`() {
        val expression = "5 ** (2 - 1) X (1 + 2)"
        val result = calculateWithParenthesis(expression)
        assertTrue(result  is Success)
        assertEquals(15.0, (result as Success).value )
    }
}