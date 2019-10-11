import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MyFoldGenericTest {
    val convertListOfStringToInt = fun(acc:Int, value:String):Int {
        val intValue = value.toIntOrNull()
        if ((intValue != null) && (intValue < 10) && (intValue >= 0)) {
            return acc * 10 + intValue
        } else return acc
    }

    @Test
    fun `Empty list of String returns 0`() {
        Assertions.assertEquals(0, myFold(listOf<String>(), 0, convertListOfStringToInt))
    }
    @Test
    fun `List of one String containing "1" returns 1`() {
        Assertions.assertEquals(1, myFold(listOf<String>("1"), 0, convertListOfStringToInt))
    }
    @Test
    fun `List of one String containing ("1","2") returns 12`() {
        Assertions.assertEquals(12, myFold(listOf<String>("1","2"), 0, convertListOfStringToInt))
    }
    @Test
    fun `List of one String containing ("1","2") and an initial value of 3  returns 312`() {
        Assertions.assertEquals(312, myFold(listOf<String>("1","2"), 3, convertListOfStringToInt))
    }
}