import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    val peterJones = Shop("Peter Jones", "SW3", GeoLocation(51.492246,-0.159 ))
    val oxfordStreet = Shop("Oxford Street", "W1", GeoLocation(51.524565, -0.112042))
    val liverpool = Shop("Liverpool", "L1", GeoLocation(53.403799, -2.987648))
    val headOffice = Shop("Victoria 171", "SW1E 5NN",GeoLocation(51.496466,-0.141499))

    @Test
    fun `Empty string converts to an empty list of shops`() {
        val emptyString = ""
        assertEquals(listOf<Shop>(), emptyString.toShops() )
    }

    @Test
    fun `string containing data for one shop converts to a list of one shop`() {
        val stringForOneShop = "Peter Jones,SW3,51.492246,-0.159"
        val shops =  stringForOneShop.toShops()
        assertEquals(1, shops.size )
        assertEquals(peterJones, shops[0] )
    }

    @Test
    fun `string containing an invalid number of elements returns empty list of shops`() {
        val stringForOneShop = "Peter Jones,SW3,51.492246"
        val shops =  stringForOneShop.toShops()
        assertEquals(listOf<Shop>(), shops )
    }

    @Test
    fun `string containing invalid data for lng and lat returns an empty list`() {
        val stringForOneShop = "Peter Jones,SW3,xxx,yyy"
        val shops =  stringForOneShop.toShops()
        assertEquals(listOf<Shop>(), shops )
    }

    @Test
    fun `string containing data for two shops converts to a list of two shops`() {
        val stringForOneShop = "Peter Jones,SW3,51.492246,-0.159,Liverpool,L1,53.403799,-2.987648"
        val shops =  stringForOneShop.toShops()
        assertEquals(2, shops.size )
        assertEquals(peterJones, shops[0] )
        assertEquals(liverpool, shops[1] )
    }

    @Test
    fun `distance between Liverpool and London should be 177`() {
        val londonLocation = oxfordStreet.geoLocation
        val liverpoolLocation = liverpool.geoLocation
        val distanceBetweenLiverpoolAndLondon = londonLocation.distanceTo(liverpoolLocation).toInt()

        assertEquals(177, distanceBetweenLiverpoolAndLondon)
    }

    @Test
    fun `distance between Peter Jones location and Oxford Street location should be 3 miles`() {
        val peterJonesLocation = peterJones.geoLocation
        val oxfordStreetLocation = oxfordStreet.geoLocation
        val distanceBetweenPeterJonesAndOxfordStreet = peterJonesLocation.distanceTo(oxfordStreetLocation).toInt()

        assertEquals(3, distanceBetweenPeterJonesAndOxfordStreet)
    }

    @Test
    fun `distance to same shop is zero `() {
        val distanceToSamePlace = peterJones.distanceTo(peterJones)
        assertEquals(0.0,distanceToSamePlace)
    }

    @Test
    fun `distance between Peter Jones shop and Oxford Street shop is roughly 3 miles `() {
        val distanceBeweenPeterJonesAndOxfordStree = peterJones.distanceTo(oxfordStreet).toInt()
        assertEquals(3,distanceBeweenPeterJonesAndOxfordStree)
    }

    @Test
    fun `Re-ordering an empty list of shops creates an empty list of shops`() {
        val emptyListofShops = emptyList<Shop>()

        assertEquals(emptyList<Shop>(), orderShops(emptyListofShops))
    }

    @Test
    fun `Re-ordering a list of one shop returns a list of one shop`() {
        val listOfOneShop = listOf(oxfordStreet)

        assertEquals(1, orderShops(listOfOneShop).size)
        assertEquals(oxfordStreet, orderShops(listOfOneShop).first())
    }

    @Test
    fun `Re-ordering a list of two shops returns a list of two shop`() {
        val listOfTwoShop = listOf(headOffice, oxfordStreet)
        val orderedShops = orderShops(listOfTwoShop)

        assertEquals(2, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(oxfordStreet, orderedShops[1])
        assertEquals(2, orderedShops[1].distanceFromLastShop.toInt())

    }

    @Test
    fun `Re-ordering a list of three shops in correct order returns a list of three shops`() {
        val listOfThreeShop = listOf(headOffice, peterJones, oxfordStreet)
        val orderedShops = orderShops(listOfThreeShop)

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }

    @Test
    fun `Re-ordering a list of three shops in incorrect order returns a list of three shops`() {
        val listOfThreeShop = listOf(headOffice, oxfordStreet, peterJones)
        val orderedShops = orderShops(listOfThreeShop)

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }

    @Test
    fun `Re-ordering a list of four shops in a random order returns shops in the correct order`() {
        val listOfFourShop = listOf(headOffice, liverpool, peterJones, oxfordStreet)

        val orderedShops = orderShops(listOfFourShop)

        assertEquals(4, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(liverpool, orderedShops[3])
    }

    @Test
    fun `closest shop is null if the list of new shops is empty `() {
        val listOfNewShops = emptyList<Shop>()
        val listOfAllShops = emptyList<Shop>()

        assertEquals(null, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `closest shop is null if the list of all shops is empty `() {
        val listOfNewShops = listOf<Shop>(headOffice, liverpool, peterJones)
        val listOfAllShops = emptyList<Shop>()

        assertEquals(null, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `closest shop to peter jones is head office `() {
        val listOfNewShops = listOf<Shop>(peterJones)
        val listOfAllShops = listOf<Shop>(peterJones, liverpool, headOffice, oxfordStreet)

        assertEquals(headOffice, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `liverpool is the last shop on the new list of shops then closest shop is oxford street `() {
        val listOfNewShops = listOf<Shop>(peterJones, liverpool)
        val listOfAllShops = listOf<Shop>(peterJones, liverpool, headOffice, oxfordStreet)

        assertEquals(oxfordStreet, findClosestShop(listOfAllShops, listOfNewShops))
    }
}