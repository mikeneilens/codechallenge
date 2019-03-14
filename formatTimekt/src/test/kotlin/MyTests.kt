import junit.framework.TestCase.assertEquals
import mike.formatTime
import org.junit.Test

class MyTests {
    @Test
    fun singleValues() {
        assertEquals("none",formatTime(0))
    }
}


