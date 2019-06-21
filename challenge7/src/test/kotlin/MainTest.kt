import junit.framework.TestCase.assertEquals
import org.junit.Test

class MainTest {
    @Test
    fun `Test the function isEvenAndLessThan` () {
        assertEquals(true, numberIsEvenAndLessThan(5,2))
        assertEquals(false, numberIsEvenAndLessThan(5,3))
        assertEquals(false, numberIsEvenAndLessThan(5,6))
    }
}

