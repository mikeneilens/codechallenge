import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    val peterJones = Shop("Peter Jones", "SW3", GeoLocation(51.492246,-0.159 ),0.0)
    val oxfordStreet = Shop("Oxford Street", "W1", GeoLocation(51.524565, -0.112042),0.0)
    val liverpool = Shop("Liverpool", "L1", GeoLocation(53.403799, -2.987648),0.0)
    val headOffice = Shop("Victoria 171", "SW1E 5NN",GeoLocation(51.496466,-0.141499),0.0)

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
        val listOfOneShop = listOf(headOffice, oxfordStreet, peterJones)
        val orderedShops = orderShops(listOfOneShop)

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(oxfordStreet, orderedShops[1])
        assertEquals(peterJones, orderedShops[2])
        assertEquals(2, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }
}