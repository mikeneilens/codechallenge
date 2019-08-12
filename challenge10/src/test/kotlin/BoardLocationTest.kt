import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BoardLocationTest {
    @Test
    fun `Initial board location is Go` () {
        val boardLocation = BoardLocation(locations)

        assertEquals(Go, boardLocation.currentLocation())
    }
}