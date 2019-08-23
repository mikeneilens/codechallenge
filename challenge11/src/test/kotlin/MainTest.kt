import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


class MainTest {
    @Test
    fun `ParseJsonIntoPubs parses empty json array OK`() {
        val listOfPubs = ParseJsonIntoPubs(noPubs)

        assertTrue(listOfPubs.isEmpty())
    }

    @Test
    fun `ParseJsonIntoPubs parses a json array containing one pub with no beers`() {
        val listOfPubs = ParseJsonIntoPubs(singlePubWithNoBeer)

        assertTrue(listOfPubs.size == 1)
        assertEquals("Cask and Glass", listOfPubs[0].name)
        assertEquals("WLD", listOfPubs[0].branch)
        assertEquals("15938", listOfPubs[0].id)
        assertEquals("https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London", listOfPubs[0].pubService)
        assertEquals("2019-05-16 19:31:39", listOfPubs[0].createTS)
        assertTrue(listOfPubs[0].regularBeers.isEmpty())
        assertTrue(listOfPubs[0].guestBeers.isEmpty())
    }
}