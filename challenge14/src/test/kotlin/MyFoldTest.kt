import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MyFoldTest {

    val convertIntsToSingleString = fun  (acc:String, value:Int) ="$acc$value"

    @Test
    fun `Empty list of Ints returns an empty list of String`() {
        assertEquals(listOf<String>(), myFold(listOf<Int>(),0,convertIntsToSingleString))
    }

}