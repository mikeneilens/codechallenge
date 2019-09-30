import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `distance between Liverpool and London should be 173`() {
        val londonLocation = GeoLocation(51.524565, -0.112042)
        val liverpoolLocation = GeoLocation(53.403799, -2.987648)

        val distanceBetweenLiverpoolAndLondon = londonLocation.distanceTo(liverpoolLocation).toInt()

        assertEquals(177, distanceBetweenLiverpoolAndLondon)
    }

    @Test
    fun `distance between Peter Jones and Oxford Street shops should be`() {
        val peterJonesLocation = GeoLocation(51.492246,-0.159 )
        val oxfordStreetLocation = GeoLocation(51.524565, -0.112042)

        val distanceBetweenPeterJonesAndOxfordStreet = peterJonesLocation.distanceTo(oxfordStreetLocation).toInt()

        assertEquals(3, distanceBetweenPeterJonesAndOxfordStreet)
    }
}