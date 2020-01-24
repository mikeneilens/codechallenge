import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `test simple call`() {
        assertTrue(httpRequest("https://challenge20.appspot.com/").isNotEmpty())
    }
}