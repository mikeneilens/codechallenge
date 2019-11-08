import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `string containing nothing is converted to 0`() {
        assertEquals(0, "".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing I is converted to 1`() {
        assertEquals(1, "I".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing II is converted to 2`() {
        assertEquals(2, "II".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing III is converted to 3`() {
        assertEquals(3, "III".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing IV is converted to 4`() {
        assertEquals(4, "IV".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing V is converted to 5`() {
        assertEquals(5, "V".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing VI is converted to 6`() {
        assertEquals(6, "VI".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing VII is converted to 7`() {
        assertEquals(7, "VII".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing X is converted to 10`() {
        assertEquals(10, "X".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing XIII is converted to 13`() {
        assertEquals(13, "XIII".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing XIV is converted to 14`() {
        assertEquals(14, "XIV".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing XVIII is converted to 18`() {
        assertEquals(18, "XVIII".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing IX is converted to 9`() {
        assertEquals(9, "IX".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing L is converted to 50`() {
        assertEquals(50, "L".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing XL is converted to 40`() {
        assertEquals(40, "XL".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing C is converted to 100`() {
        assertEquals(100, "C".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing XC is converted to 90`() {
        assertEquals(90, "XC".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing CCLXXVII is converted to 277`() {
        assertEquals(277, "CCLXXVII".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing D is converted to 500`() {
        assertEquals(500, "D".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing CD is converted to 400`() {
        assertEquals(400, "CD".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing M is converted to 1000`() {
        assertEquals(1000, "M".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing MM is converted to 2000`() {
        assertEquals(2000, "MM".fromRomanSymbolToInt())
    }
    @Test
    fun `string containing MCM is converted to 1900`() {
        assertEquals(1900, "MCM".fromRomanSymbolToInt())
    }

    @Test
    fun `1 is converted to I`() {
       assertEquals("I", 1.fromIntToRomanSymbol())
    }
    @Test
    fun `2 is converted to II`() {
       assertEquals("II", 2.fromIntToRomanSymbol())
    }
    @Test
    fun `3 is converted to III`() {
        assertEquals("III", 3.fromIntToRomanSymbol())
    }
    @Test
    fun `4 is converted to IV`() {
        assertEquals("IV", 4.fromIntToRomanSymbol())
    }
    @Test
    fun `5 is converted to V`() {
        assertEquals("V", 5.fromIntToRomanSymbol())
    }
    @Test
    fun `6 is converted to VI`() {
        assertEquals("VI", 6.fromIntToRomanSymbol())
    }
    @Test
    fun `8 is converted to VIII`() {
        assertEquals("VIII", 8.fromIntToRomanSymbol())
    }
    @Test
    fun `10 is converted to X`() {
        assertEquals("X", 10.fromIntToRomanSymbol())
    }
    @Test
    fun `20 is converted to XX`() {
        assertEquals("XX", 20.fromIntToRomanSymbol())
    }
    @Test
    fun `23 is converted to XXIII`() {
        assertEquals("XX", 20.fromIntToRomanSymbol())
    }
    @Test
    fun `24 is converted to XXIV`() {
        assertEquals("XXIV", 24.fromIntToRomanSymbol())
    }
    @Test
    fun `29 is converted to XXIX`() {
        assertEquals("XXIX", 29.fromIntToRomanSymbol())
    }
    @Test
    fun `50 is converted to L`() {
        assertEquals("L", 50.fromIntToRomanSymbol())
    }
    @Test
    fun `53 is converted to LIII`() {
        assertEquals("LIII", 53.fromIntToRomanSymbol())
    }
    @Test
    fun `54 is converted to LIV`() {
        assertEquals("LIV", 54.fromIntToRomanSymbol())
    }
    @Test
    fun `40 is converted to XL`() {
        assertEquals("XL", 40.fromIntToRomanSymbol())
    }
    @Test
    fun `100 is converted to C`() {
        assertEquals("C", 100.fromIntToRomanSymbol())
    }
    @Test
    fun `300 is converted to CCC`() {
        assertEquals("CCC", 300.fromIntToRomanSymbol())
    }
    @Test
    fun `290 is converted to CCXC`() {
        assertEquals("CCXC", 290.fromIntToRomanSymbol())
    }
    @Test
    fun `500 is converted to D`() {
        assertEquals("D", 500.fromIntToRomanSymbol())
    }
    @Test
    fun `400 is converted to CD`() {
        assertEquals("CD", 400.fromIntToRomanSymbol())
    }
    @Test
    fun `1000 is converted to M`() {
        assertEquals("M", 1000.fromIntToRomanSymbol())
    }
    @Test
    fun `1900 is converted to MCM`() {
        assertEquals("MCM", 1900.fromIntToRomanSymbol())
    }

}


