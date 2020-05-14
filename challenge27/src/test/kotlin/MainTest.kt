import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `simple request`() {
        val result = RequestObject.makeRequest()
        assertEquals(listOf("M"), result)
    }
}