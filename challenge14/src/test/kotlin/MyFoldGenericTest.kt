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
}