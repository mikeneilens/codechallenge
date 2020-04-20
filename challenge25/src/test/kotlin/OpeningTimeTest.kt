import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OpeningTimeTest {
    @Test
    fun `hours of a time should give 12 when time is 12 35  `() {
        val time = "12:35"
        assertEquals(12, time.hours)
    }
    @Test
    fun `minutes of a time should give 35 when time is 12 35  `() {
        val time = "12:35"
        assertEquals(35, time.minutes)
    }
    @Test
    fun `hours text for 1 00 is 1`() {
        val time = "01:00"
        assertEquals("1", time.hoursText)

    }
    @Test
    fun `hours text for 00 00 is 12`() {
        val time = "00:00"
        assertEquals("12", time.hoursText)

    }
    @Test
    fun `hours text for 12 00 is 12`() {
        val time = "12:00"
        assertEquals("12", time.hoursText)

    }

    @Test
    fun `hours text for 13 00 is 1`() {
        val time = "13:00"
        assertEquals("1", time.hoursText)

    }
    @Test
    fun `minutes text for 00 00 is empty`() {
        val time = "00:00"
        assertEquals("", time.minutesText)

    }
    @Test
    fun `minutes text for 00 05 is 05 with colon prefix`() {
        val time = "00:05"
        assertEquals(":05", time.minutesText)

    }
    @Test
    fun `minutes text for 00 10 is 10 with colon prefix`() {
        val time = "00:10"
        assertEquals(":10", time.minutesText)

    }
    @Test
    fun `short name for Wednesday is Wed`() {
        val weekday = "Wednesday"
        assertEquals("Wed", weekday.short)
    }
    @Test
    fun `tuesday is consecutive to monday`() {
        val monday = "Monday"
        val tuesday = "Tuesday"
        assertTrue(tuesday isConsecutiveTo monday)
    }
    @Test
    fun `monday is consecutive to tuesday`() {
        val monday = "Monday"
        val tuesday = "Tuesday"
        assertTrue(monday isConsecutiveTo tuesday)
    }
    @Test
    fun `mon is consecutive to tue`() {
        val monday = "Mon"
        val tuesday = "Tue"
        assertTrue(monday isConsecutiveTo tuesday)
    }
    @Test
    fun `mon is not consecutive to wed`() {
        val monday = "Mon"
        val tuesday = "Wed"
        assertFalse(monday isConsecutiveTo tuesday)
    }
    @Test
    fun `compact time for 00 00 is 12am`() {
        val time = "00:00"
        assertEquals("12am", time.compact)
    }
    @Test
    fun `compact time for 00 05 is 12 05am`() {
        val time = "00:05"
        assertEquals("12:05am", time.compact)
    }
    @Test
    fun `compact time for 01 05 is 1 05am`() {
        val time = "01:05"
        assertEquals("1:05am", time.compact)
    }
    @Test
    fun `compact time for 12 05 is 12 05pm`() {
        val time = "12:05"
        assertEquals("12:05pm", time.compact)
    }
}