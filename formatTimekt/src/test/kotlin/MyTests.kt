import junit.framework.TestCase.assertEquals
import mike.UnitTime
import mike.formatTime
import org.junit.Test

class MyTests {
    @Test
    fun singleValues() {
        assertEquals("none",formatTime(0))
        assertEquals("1 second",formatTime(1))
        assertEquals("1 minute",formatTime(60))
        assertEquals("1 hour",formatTime(3600))
        assertEquals("1 day",formatTime(86400))
        assertEquals("1 year",formatTime(31536000))
    }
    @Test
    fun pluralValues() {
        assertEquals("2 seconds",formatTime(2))
        assertEquals("2 minutes",formatTime(120))
        assertEquals("2 hours",formatTime(7200))
        assertEquals("2 days",formatTime(172800))
        assertEquals("2 years",formatTime(63072000))
    }
    @Test
    fun unitTime() {
        assertEquals("1 second", UnitTime("second",1).toString())
        assertEquals("2 seconds", UnitTime("second",2).toString())

    }
    @Test
    fun moreThanOneUnit() {
        assertEquals("1 minute and 1 second", formatTime(61))
        assertEquals("1 hour and 2 seconds", formatTime(3602))
    }
}


