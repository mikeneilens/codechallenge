import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    val peterJones = Shop("Peter Jones", "SW3", GeoLocation(51.492246,-0.159 ))
    val oxfordStreet = Shop("Oxford Street", "W1", GeoLocation(51.524565, -0.112042))
    val liverpool = Shop("Liverpool", "L1", GeoLocation(53.403799, -2.987648))


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
}