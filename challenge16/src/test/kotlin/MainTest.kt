import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `string containing nothing is converted to 0`() {
        assertEquals(0, "".fromRomanToInt())
    }
    @Test
    fun `string containing I is converted to 1`() {
        assertEquals(1, "I".fromRomanToInt())
    }
    @Test
    fun `string containing II is converted to 2`() {
        assertEquals(2, "II".fromRomanToInt())
    }
    @Test
    fun `string containing III is converted to 3`() {
        assertEquals(3, "III".fromRomanToInt())
    }
}
