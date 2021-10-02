import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    private val numberToString:(Int)->String = { n -> n.toString()}

    @Test
    fun `memoized function returns the correct result when using the default cache`() {
        val functionWithMemory = memoize(numberToString)
        assertEquals("56", functionWithMemory(56))
        assertEquals("61", functionWithMemory(61))
    }
    @Test
    fun `memoized function returns the correct result and stores it in the cache when using a  test cache`() {
        val testCache = mutableMapOf<Int, String>()
        val functionWithMemory = memoize(numberToString, testCache )
        assertEquals("56", functionWithMemory(56))
        assertEquals("56", testCache[56])
    }

    @Test
    fun `memoized function returns the value from the cache when using a test cache that has been updated`() {
        val testCache = mutableMapOf<Int, String>()
        testCache[56] = "The wrong answer!"
        val functionWithMemory = memoize(numberToString, testCache )
        assertEquals("The wrong answer!", functionWithMemory(56))
    }

}