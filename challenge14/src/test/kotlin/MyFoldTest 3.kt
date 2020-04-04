import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyFoldTest {

    val convertIntsToSingleString = fun  (acc:String, value:Int) ="$acc$value"
    val convertIntsToSingleStringInReverse = fun  (acc:String, value:Int) ="$value$acc"
    val convertIntsToSingleStringWithSpaces = fun  (acc:String, value:Int) ="$acc $value"

    @Test
    fun `Empty list of Ints returns an empty String`() {
        assertEquals("", myFold(listOf<Int>(), "", convertIntsToSingleString))
    }

    @Test
    fun `List of (1) returns a string containing "1" `() {
        assertEquals("1", myFold(listOf(1), "", convertIntsToSingleString))
    }
    @Test
    fun `List of (1), plus an initial value of "x" returns a string containing "x1" `() {
        assertEquals("x1", myFold(listOf(1), "x", convertIntsToSingleString))
    }
    @Test
    fun `List of (1,2), plus an initial value of "x" returns a string containing "x1" `() {
        assertEquals("x12", myFold(listOf(1, 2), "x", convertIntsToSingleString))
    }
    @Test
    fun `List of (1,2,3), plus an initial value of "x" returns a string containing "x123" `() {
        assertEquals("x123", myFold(listOf(1, 2, 3), "x", convertIntsToSingleString))
    }
    @Test
    fun `List of (1,2,3), plus an initial value of "x" returns a string containing "321x" when used with reverse function `() {
        assertEquals("321x", myFold(listOf(1, 2, 3), "x", convertIntsToSingleStringInReverse))
    }
    @Test
    fun `An empty list, plus an initial value of "x" returns a string containing "x" when used with empty spaces function `() {
        assertEquals("x", myFold(listOf(), "x", convertIntsToSingleStringWithSpaces))
    }
    @Test
    fun `List of (1,2,3), plus an initial value of "x" returns a string containing "x 1 2 3" when used with empty spaces function `() {
        assertEquals("x 1 2 3", myFold(listOf(1, 2, 3), "x", convertIntsToSingleStringWithSpaces))
        listOf(1,2,3).fold("",convertIntsToSingleStringWithSpaces)
    }
    @Test
    fun `The test case from the challenge`() {
        val result = myFold(listOf(1, 2, 3, 4), "The result is ", { acc, element -> acc + element.toString() })
        assertEquals("The result is 1234", result)
    }

    @Test
    fun `The test case from the challenge using FoldFunction`() {
        val foldFunction:FoldFunction<Int, String> = { acc, element -> acc + element.toString() }
        val result = foldFunction.myFold(listOf(1, 2, 3, 4), "The result is ")
        assertEquals("The result is 1234", result)
    }
}