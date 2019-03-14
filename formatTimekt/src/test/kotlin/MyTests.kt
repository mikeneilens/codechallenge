import junit.framework.TestCase.assertEquals
import mike.UnitTime
import mike.Unit
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
        assertEquals("1 second", UnitTime(Unit.second,1).toString())
        assertEquals("2 seconds", UnitTime(Unit.second,2).toString())

    }
    @Test
    fun moreThanOneUnit() {
        assertEquals("1 minute and 1 second", formatTime(61))
        assertEquals("1 hour and 2 seconds", formatTime(3602))
        assertEquals("9 days, 23 hours, 53 minutes and 22 seconds", formatTime(863602))
        assertEquals("1 year, 2 hours, 52 minutes and 10 seconds", formatTime(31546330))
    }
}


