import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `111111 is true because it is six digits, adjacent digits are the same and digits never decrease` () {
        assertTrue(isAValidCode(111111))
    }
    @Test
    fun `223450 is not valid because it contains descending digits`() {
        assertFalse(isAValidCode(223450))
    }
    @Test
    fun `123789 is not valid because it does not contain a repeating digit`() {
        assertFalse(isAValidCode(123789))
    }
    @Test
    fun `List containing 111111,223450,123789 only has 111111 as a valid code `() {
        assertEquals(listOf(111111),validCodesInList(listOf(111111,223450,123789)))
    }
    @Test
    fun `Part one`() {
        val result = validCodesInList((372304..847060).toList())
        println("Part one: ${result.size} $result")
    }

    @Test
    fun `112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long`() {
        val code = 112233.toString().toList().map{it.toString().toInt()}
        assertTrue(code.containsPairNotInGroup())
    }
    @Test
    fun `123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444)`() {
        val code = 123444.toString().toList().map{it.toString().toInt()}
        assertFalse(code.containsPairNotInGroup())
    }
    @Test
    fun `111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22)`() {
        val code = 111122.toString().toList().map{it.toString().toInt()}
        assertTrue(code.containsPairNotInGroup())
    }

    @Test
    fun `112113 meets the criteria`() {
        val code = 112113.toString().toList().map{it.toString().toInt()}
        assertTrue(code.containsPairNotInGroup())
    }

    @Test
    fun `377777 doesn't meet the criteria`() {
        val code = 377777.toString().toList().map{it.toString().toInt()}
        assertFalse(code.containsPairNotInGroup())
    }

    @Test
    fun `Part two`() {
        val result = validCodesInListPartTwo((372304..847060).toList())
        println("Part two: ${result.size} $result")
    }

}