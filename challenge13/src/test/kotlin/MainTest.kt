import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    val peterJones = Shop("Peter Jones", "SW3", GeoLocation(51.492246,-0.159 ))
    val oxfordStreet = Shop("Oxford Street", "W1", GeoLocation(51.524565, -0.112042))
    val liverpool = Shop("Liverpool", "L1", GeoLocation(53.403799, -2.987648))
    val headOffice = Shop("Victoria 171", "SW1E 5NN",GeoLocation(51.496466,-0.141499))

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
        val listOfOneShop = listOf(headOffice, oxfordStreet)
        val orderedShops = orderShops(listOfOneShop)

        assertEquals(2, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(oxfordStreet, orderedShops[1])
        assertEquals(2, orderedShops[1].distanceFromLastShop.toInt())

    }

    @Test
    fun `Re-ordering a list of three shops in correct order returns a list of three shops`() {
        val listOfOneShop = listOf(headOffice, peterJones, oxfordStreet)
        val orderedShops = orderShops(listOfOneShop)

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }

    @Test
    fun `Re-ordering a list of three shops in incorrect order returns a list of three shops`() {
        val listOfOneShop = listOf(headOffice, oxfordStreet, peterJones)
        val orderedShops = orderShops(listOfOneShop)

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
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