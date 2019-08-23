import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BoardLocationTest {
    @Test
    fun `Initial board location is Go` () {
        val boardLocation = BoardLocation(locations)
        assertEquals(Go, boardLocation.currentLocation)
    }

    @Test
    fun `Initial board location is location at position 4 if a locationIndex is given to the constructor`() {
        val boardLocation = BoardLocation(locations, 4)
        assertEquals(locations[4], boardLocation.currentLocation)
    }

    @Test
    fun `Initial board has hasPassedGo set correctly depending on locationIndex sent to the constructor`() {
        val boardLocation = BoardLocation(locations, locations.size  - 1)
        assertEquals(false, boardLocation.hasPassedGo)

        val boardLocationPassedGo = BoardLocation(locations, locations.size  + 1)
        assertEquals(true, boardLocationPassedGo.hasPassedGo)
    }

    @Test
    fun `Adding dice of value 4 to an inital boardLocation updates the boardLocation to show location at position 4 in the Location array`() {
        val boardLocation = BoardLocation(locations)
        val dice = Dice(PredictableDiceValue(listOf(3,1)))
        val newBoardLocation = boardLocation + dice
        assertEquals(locations[4], newBoardLocation.currentLocation)
    }

    @Test
    fun `Adding dice of value 4 to an inital boardLocation with index set to 5 updates the boardLocation to show location at position 9 in the Location array`() {
        val boardLocation = BoardLocation(locations, 5)
        val dice = Dice(PredictableDiceValue(listOf(3,1)))
        val newBoardLocation = boardLocation + dice
        assertEquals(locations[9], newBoardLocation.currentLocation)
    }

    @Test
    fun `Adding dice of value 4 to an inital boardLocation with index set to board size - 3 updates the boardLocation to show location at position 1 in the Location array`() {
        val boardLocation = BoardLocation(locations, locations.size -3 )
        val dice = Dice(PredictableDiceValue(listOf(2,2)))
        val newBoardLocation = boardLocation + dice
        assertEquals(locations[1], newBoardLocation.currentLocation)

        assertEquals(true, newBoardLocation.hasPassedGo)
    }
}
