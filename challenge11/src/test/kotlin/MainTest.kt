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

    @Test
    fun `ParseJsonIntoPubs parses a json array containing one pub with regular beers and guest beers`() {
        val listOfPubs = ParseJsonIntoPubs(singlePub)

        assertEquals(2, listOfPubs[0].regularBeers.size)
        assertEquals("Shepherd Neame Master Brew",listOfPubs[0].regularBeers[0])
        assertEquals("Shepherd Neame Spitfire",listOfPubs[0].regularBeers[1])

        assertEquals(3, listOfPubs[0].guestBeers.size)
        assertEquals("Shepherd Neame --seasonal--", listOfPubs[0].guestBeers[0])
        assertEquals("Shepherd Neame --varies--", listOfPubs[0].guestBeers[1])
        assertEquals("Shepherd Neame Whitstable Bay Pale Ale", listOfPubs[0].guestBeers[2])
    }
    @Test
    fun `ParseJsonIntoPubs parses a json array containing more than one pub`() {
        val listOfPubs = ParseJsonIntoPubs(manyPubs)

        assertEquals(6, listOfPubs.size )
        assertEquals("Phoenix", listOfPubs[0].name)
        assertEquals(1, listOfPubs[0].regularBeers.size)
        assertEquals(3, listOfPubs[0].guestBeers.size)

        assertEquals("Willow Walk", listOfPubs[3].name)
        assertEquals(3, listOfPubs[3].regularBeers.size)
        assertEquals(0, listOfPubs[3].guestBeers.size)
    }
}