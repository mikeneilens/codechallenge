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
    fun `ParseJsonIntoPubs returns an empty list if the json is invalid`() {
        val rubbishJson = "This is rubbish json!"
        val listOfPubs = parseJsonIntoPubs(rubbishJson)

        assertTrue(listOfPubs.isEmpty())
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
    fun `RemoveDuplicatePubs returns distinct values in the correct order`() {
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub2 = Pub("pub2","b1","id1","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub3 = Pub("pub3","b1","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub4 = Pub("pub4","b1","id2","2019-05-16 19:33:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub5 = Pub("pub5","b1","id2","2019-05-16 19:32:39","pub service", listOf("beer1"),listOf("beer2"))
        val pub6 = Pub("pub6","b2","id1","2019-05-16 19:31:39","pub service", listOf("beer1"),listOf("beer2"))

        val pubs = listOf(pub1, pub2, pub3, pub4, pub5, pub6)
        val distinctPubs = pubs.removeDuplicatePubs()

        assertEquals(3, distinctPubs.size)
        assertEquals(pub1, distinctPubs[0])
        assertEquals(pub4, distinctPubs[1])
        assertEquals(pub6, distinctPubs[2])
    }
    @Test
    fun `RemoveDuplicatePubs removes duplicate pub, retaining the latest pub when there is more than one with the same key`() {
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
    @Test
    fun `mapRegularBeers returns an empty array if pub has no beers`() {
        val pub = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf(),listOf("beer1","beer2"))
        val beers = pub.mapRegularBeers()
        assertTrue(beers.isEmpty())
    }
    @Test
    fun `mapRegularBeers returns an array of beers`() {
        val pub = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1","beer2"),listOf("beer3","beer4","beer5"))
        val beers = pub.mapRegularBeers()
        assertEquals(2, beers.size)
        assertEquals("beer1",beers[0].name)
        assertTrue(beers[0].isRegularBeer)
        assertEquals("beer2",beers[1].name)
        assertTrue(beers[1].isRegularBeer)
    }
    @Test
    fun `mapGuestBeers returns an empty array if pub has no beers`() {
        val pub = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1","beer2"),listOf())
        val beers = pub.mapGuestBeers()
        assertTrue(beers.isEmpty())
    }
    @Test
    fun `mapGuestBeers returns an array of beers`() {
        val pub = Pub("pub1","b1","id1","2019-05-16 19:33:39","pub service", listOf("beer1","beer2"),listOf("beer3","beer4","beer5"))
        val beers = pub.mapGuestBeers()
        assertEquals(3, beers.size)
        assertEquals("beer3",beers[0].name)
        assertFalse(beers[0].isRegularBeer)
        assertEquals("beer4",beers[1].name)
        assertFalse(beers[1].isRegularBeer)
        assertEquals("beer5",beers[2].name)
        assertFalse(beers[2].isRegularBeer)
    }
    @Test
    fun `FlattenEachPub returns an empty list if pubs don't contain any beers`(){
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:31:39","pub service", listOf(),listOf())
        val pub2 = Pub("pub4","b1","id2","2019-05-16 19:33:39","pub service", listOf(),listOf())
        val pub3 = Pub("pub5","b1","id3","2019-05-16 19:33:39","pub service", listOf(),listOf())
        val pub4 = Pub("pub6","b2","id3","2019-05-16 19:33:39","pub service", listOf(),listOf())
        val pub5 = Pub("pub7","b3","id3","2019-05-16 19:33:39","pub service", listOf(),listOf())

        val pubs = listOf(pub1, pub2, pub3, pub4, pub5)
        val beers = pubs.flattenEachPub()
        assertTrue(beers.isEmpty())
    }
    @Test
    fun `FlattenEachPub returns an array of regular beers`(){
        val pub1 = Pub("pub1","b1","id1","2019-05-16 19:31:39","pub service", listOf("b1","b2"))
        val pub2 = Pub("pub2","b1","id2","2019-05-16 19:33:39","pub service", listOf("b2","b3"),listOf())
        val pub3 = Pub("pub3","b1","id3","2019-05-16 19:33:39","pub service", listOf("b2","b4"),listOf())
        val pub4 = Pub("pub4","b2","id3","2019-05-16 19:33:39","pub service", listOf("b1","b2"),listOf())
        val pub5 = Pub("pub5","b3","id3","2019-05-16 19:33:39","pub service", listOf("b3","b4"),listOf())

        val pubs = listOf(pub1, pub2, pub3, pub4, pub5)
        val beers = pubs.flattenEachPub()
        assertEquals(10,beers.size)

        assertEquals("b1", beers[0].name)
        assertEquals("pub1", beers[0].pubName)
        assertEquals("b2", beers[1].name)
        assertEquals("pub1", beers[1].pubName)

        assertEquals("b3", beers[8].name)
        assertEquals("pub5", beers[8].pubName)
        assertEquals("b4", beers[9].name)
        assertEquals("pub5", beers[9].pubName)
    }
    @Test
    fun `ObtainListOfBeers returns an empty array if json contains no pubs`() {
        val listOfBeers = obtainListOfBeers(noPubs)
        assertTrue(listOfBeers.isEmpty())
    }
    @Test
    fun `ObtainListOfBeers returns an empty array if json contain a pub but the pub has no beer`() {
        val listOfBeers = obtainListOfBeers(singlePubWithNoBeer)
        assertTrue(listOfBeers.isEmpty())
    }
    @Test
    fun `ObtianListOfBeers returns an array containing correct number of regular and guest beers when json contains a pub with beer`() {
        val listOfBeers = obtainListOfBeers(singlePub)
        assertEquals(2, listOfBeers.filter { it.isRegularBeer }.size)
        assertEquals(3, listOfBeers.filter { !it.isRegularBeer }.size)
    }
    @Test
    fun `ObtianListOfBeers returns an array containing correct number of regular and guest beers when json contains many pub with beer`() {
        val listOfBeers = obtainListOfBeers(manyPubs)

        val uniqueBeers = listOfBeers.distinctBy { it.name }
        val regularBeers = listOfBeers.filter{it.isRegularBeer}
        val guestBeers = listOfBeers.filter{!it.isRegularBeer}

        assertEquals(16, listOfBeers.size)
        assertEquals(14, uniqueBeers.size)
        assertEquals(13, regularBeers.size)
        assertEquals(3, guestBeers.size)
    }


}