import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `test simple call`() {
        assertTrue(httpRequest("https://challenge20.appspot.com/").isNotEmpty())
    }

    @Test
    fun `parseResponse should return a referenceid succeed when the response only contains a reference id `() {
        val expectedMapResponse = MapResponse("abcd", listOf())
        val input = "abcd"
        val result = parseResponse(input)
        assertEquals(expectedMapResponse, result)
    }
    @Test
    fun `parseResponse should return a referenceid and a point when the response contains a referenceid and a point`() {
        val expectedMapResponse = MapResponse("abcd", listOf("O"))
        val input = "abcd,O"
        val result = parseResponse(input)
        assertEquals(expectedMapResponse, result)

    }
    @Test
    fun `parseResponse should return a referenceid and a point when the response contains a referenceid and many points`() {
        val expectedMapResponse = MapResponse("abcd", listOf("O","O","O"))
        val input = "abcd,O,O,O"
        val result = parseResponse(input)
        assertEquals(expectedMapResponse, result)
    }

    @Test
    fun `decide action should return M if there is a space ahead`() {
        val mapResponse = MapResponse("abcd",listOf("O"))
        val expectedCommand = Command.M
        assertEquals(expectedCommand, decideAction(mapResponse) )
    }
}