import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyFoldTest {

    val convertIntsToSingleString = fun  (acc:String, value:Int) ="$acc$value"

    @Test
    fun `Empty list of Ints returns an empty String`() {
        assertEquals("", myFold(listOf<Int>(),0,convertIntsToSingleString))
    }

    @Test
    fun `List of (1) returns a string containing "1" `() {
        assertEquals("1", myFold(listOf(1),0,convertIntsToSingleString))
    }
}