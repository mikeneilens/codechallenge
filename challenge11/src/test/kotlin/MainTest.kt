import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


class MainTest {
    @Test
    fun `ParseJsonIntoPubs parses empty json array OK`() {
        val listOfPubs = parseJsonIntoPubs(noPubs)

        assertTrue(listOfPubs.isEmpty())
    }

    @Test
    fun `ParseJsonIntoPubs parses a json array containing one pub with no beers`() {
        val listOfPubs = parseJsonIntoPubs(singlePubWithNoBeer)

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
        val listOfPubs = parseJsonIntoPubs(singlePub)

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
        val listOfPubs = parseJsonIntoPubs(manyPubs)

        assertEquals(6, listOfPubs.size )
        assertEquals("Phoenix", listOfPubs[0].name)
        assertEquals(1, listOfPubs[0].regularBeers.size)
        assertEquals(3, listOfPubs[0].guestBeers.size)

        assertEquals("Willow Walk", listOfPubs[3].name)
        assertEquals(3, listOfPubs[3].regularBeers.size)
        assertEquals(0, listOfPubs[3].guestBeers.size)
    }

    @Test
    fun `sortIntoDescendingOrder sorts pubs into correct order`() {
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub2 = Pub("pub2","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub3 = Pub("pub3","b1","id1","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub4 = Pub("pub4","b1","id2","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub5 = Pub("pub5","b1","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub6 = Pub("pub6","b2","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub7 = Pub("pub7","b3","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))

        val pubs = listOf(pub1, pub2, pub3, pub4, pub5, pub6, pub7)
        val sortedPubs = pubs.sortIntoDescendingOrder()

        assertEquals(pub7, sortedPubs[0])
        assertEquals(pub6, sortedPubs[1])
        assertEquals(pub5, sortedPubs[2])
        assertEquals(pub4, sortedPubs[3])
        assertEquals(pub2, sortedPubs[4])
        assertEquals(pub3, sortedPubs[5])
        assertEquals(pub1, sortedPubs[6])
    }
    @Test
    fun `distinctValues returns distinct values in the correct order`() {
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub2 = Pub("pub2","b1","id1","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub3 = Pub("pub3","b1","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub4 = Pub("pub4","b1","id2","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub5 = Pub("pub5","b1","id2","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub6 = Pub("pub6","b2","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))

        val pubs = listOf(pub1, pub2, pub3, pub4, pub5, pub6)
        val distinctPubs = pubs.distinctValues()

        assertEquals(3, distinctPubs.size)
        assertEquals(pub1, distinctPubs[0])
        assertEquals(pub4, distinctPubs[1])
        assertEquals(pub6, distinctPubs[2])
    }
    @Test
    fun `removeDuplicates removes duplicate pub, retaining the latest pub when there is more than one with the same key`() {
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub2 = Pub("pub2","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub3 = Pub("pub3","b1","id1","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub4 = Pub("pub4","b1","id2","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub5 = Pub("pub5","b1","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub6 = Pub("pub6","b2","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub7 = Pub("pub7","b3","id3","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))

        val pubs = listOf(pub1,pub2,pub3,pub4,pub5,pub6,pub7)

        val pubsWithNoDuplicates = pubs.removeDuplicates()
        assertEquals(pub7, pubsWithNoDuplicates[0])
        assertEquals(pub6, pubsWithNoDuplicates[1])
        assertEquals(pub5, pubsWithNoDuplicates[2])
        assertEquals(pub4, pubsWithNoDuplicates[3])
        assertEquals(pub2, pubsWithNoDuplicates[4])

    }

}