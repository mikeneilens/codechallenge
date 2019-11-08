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
    @Test
    fun `string containing C is converted to 100`() {
        assertEquals(100, "C".fromRomanToInt())
    }
    @Test
    fun `string containing XC is converted to 90`() {
        assertEquals(90, "XC".fromRomanToInt())
    }
    @Test
    fun `string containing CCLXXVII is converted to 277`() {
        assertEquals(277, "CCLXXVII".fromRomanToInt())
    }
    @Test
    fun `string containing D is converted to 500`() {
        assertEquals(500, "D".fromRomanToInt())
    }
    @Test
    fun `string containing CD is converted to 400`() {
        assertEquals(400, "CD".fromRomanToInt())
    }
    @Test
    fun `string containing M is converted to 1000`() {
        assertEquals(1000, "M".fromRomanToInt())
    }
    @Test
    fun `string containing MM is converted to 2000`() {
        assertEquals(2000, "MM".fromRomanToInt())
    }
    @Test
    fun `string containing MCM is converted to 1900`() {
        assertEquals(1900, "MCM".fromRomanToInt())
    }

    @Test
    fun `1 is converted to I`() {
       assertEquals("I", 1.fromIntToRoman())
    }
    @Test
    fun `2 is converted to II`() {
       assertEquals("II", 2.fromIntToRoman())
    }
    @Test
    fun `3 is converted to III`() {
        assertEquals("III", 3.fromIntToRoman())
    }
    @Test
    fun `4 is converted to IV`() {
        assertEquals("IV", 4.fromIntToRoman())
    }
    @Test
    fun `5 is converted to V`() {
        assertEquals("V", 5.fromIntToRoman())
    }
    @Test
    fun `6 is converted to VI`() {
        assertEquals("VI", 6.fromIntToRoman())
    }
    @Test
    fun `8 is converted to VIII`() {
        assertEquals("VIII", 8.fromIntToRoman())
    }
}


