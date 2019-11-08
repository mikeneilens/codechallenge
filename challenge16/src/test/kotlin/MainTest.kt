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
    @Test
    fun `string containing IV is converted to 4`() {
        assertEquals(4, "IV".fromRomanToInt())
    }
    @Test
    fun `string containing V is converted to 5`() {
        assertEquals(5, "V".fromRomanToInt())
    }
    @Test
    fun `string containing VI is converted to 6`() {
        assertEquals(6, "VI".fromRomanToInt())
    }
    @Test
    fun `string containing VII is converted to 7`() {
        assertEquals(7, "VII".fromRomanToInt())
    }
    @Test
    fun `string containing X is converted to 10`() {
        assertEquals(10, "X".fromRomanToInt())
    }
    @Test
    fun `string containing XIII is converted to 13`() {
        assertEquals(13, "XIII".fromRomanToInt())
    }
    @Test
    fun `string containing XIV is converted to 14`() {
        assertEquals(14, "XIV".fromRomanToInt())
    }
    @Test
    fun `string containing XVIII is converted to 18`() {
        assertEquals(18, "XVIII".fromRomanToInt())
    }
    @Test
    fun `string containing IX is converted to 9`() {
        assertEquals(9, "IX".fromRomanToInt())
    }
    @Test
    fun `string containing L is converted to 50`() {
        assertEquals(50, "L".fromRomanToInt())
    }
    @Test
    fun `string containing XL is converted to 40`() {
        assertEquals(40, "XL".fromRomanToInt())
    }
}
