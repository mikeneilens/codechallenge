import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyFoldTest {

    val convertIntsToSingleString = fun  (acc:String, value:Int) ="$acc$value"

    @Test
    fun `Empty list of Ints returns an empty String`() {
        assertEquals("", myFold(listOf<Int>(),"",convertIntsToSingleString))
    }

    @Test
    fun `List of (1) returns a string containing "1" `() {
        assertEquals("1", myFold(listOf(1),"",convertIntsToSingleString))
    }
    @Test
    fun `List of (1), plus an initial value of "x" returns a string containing "x1" `() {
        assertEquals("x1", myFold(listOf(1),"x",convertIntsToSingleString))
    }
    @Test
    fun `List of (1,2), plus an initial value of "x" returns a string containing "x1" `() {
        assertEquals("x12", myFold(listOf(1,2),"x",convertIntsToSingleString))
    }
}