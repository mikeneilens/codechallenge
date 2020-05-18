
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `simple request`() {
        val result = RequestObject.makeRequest()
        assertEquals(listOf(Known.Water), result)
    }

    object MockRequester:Requester {
        var results:MutableMap<String, List<Known>> = mutableMapOf()
        override fun makeRequest(param:String):List<Known> = results[param] ?: listOf()
    }
    @Test
    fun `position for integer 0 should be A0`() {
        assertEquals("A0", "${0.toPosition()}")
    }
    @Test
    fun `coordinate for integer 9 should be J0`() {
        assertEquals("J0", "${9.toPosition()}")
    }
    @Test
    fun `coordinate for integer 90 should be A9`() {
        assertEquals("A9", "${90.toPosition()}")
    }
    @Test
    fun `coordinate for integer 99 should be J9`() {
        assertEquals("J9", "${99.toPosition()}")
    }

    @Test
    fun `Positions toRight of last column is an empty list`() {
        assertEquals(listOf<Position>(), Position(9,4).toRight() )
    }
    @Test
    fun `Positions toRight of next to last column is a list containing last column`() {
        assertEquals(listOf(Position(9,4)), Position(8,4).toRight() )
    }
    @Test
    fun `Positions toRight of 7th column is a list containing 3 positions with ascending columns`() {
        assertEquals(listOf(Position(7,4),Position(8,4),Position(9,4)), Position(6,4).toRight() )
    }

    @Test
    fun `Positions toLeft of first column is an empty list`() {
        assertEquals(listOf<Position>(), Position(0,4).toLeft() )
    }
    @Test
    fun `Positions toLeft of 2nd column is a list containing first column`() {
        assertEquals(listOf(Position(0,4)), Position(1,4).toLeft() )
    }
    @Test
    fun `Positions toLeft of 4th column is a list containing 3 positions with descending columns`() {
        assertEquals(listOf(Position(2,4),Position(1,4),Position(0,4)), Position(3,4).toLeft() )
    }

    @Test
    fun `Positions Below last tow is an empty list`() {
        assertEquals(listOf<Position>(), Position(5,9).below() )
    }
    @Test
    fun `Positions below of next to last row is a list containing last row`() {
        assertEquals(listOf(Position(5,9)), Position(5,8).below() )
    }
    @Test
    fun `Positions below  7th row is a list containing 3 positions with ascending rows`() {
        assertEquals(listOf(Position(5,7),Position(5,8),Position(5,9)), Position(5,6).below() )
    }

    @Test
    fun `Positions above first row is an empty list`() {
        assertEquals(listOf<Position>(), Position(5,0).above() )
    }
    @Test
    fun `Positions above 2nd row is a list containing first row`() {
        assertEquals(listOf(Position(5,0)), Position(5,1).above() )
    }
    @Test
    fun `Positions above 4th row is a list containing 3 positions with descending rows`() {
        assertEquals(listOf(Position(5,2),Position(5,1),Position(5,0)), Position(5,3).above() )
    }
    @Test
    fun `There are 3 positions adjacent to position 0,0`() {
        val positions = Position(0,0).surroundingPositions()
        assertEquals(3, positions.size )
    }
    @Test
    fun `There are 3 positions adjacent to position 9,0`() {
        val positions = Position(9,0).surroundingPositions()
        assertEquals(3, positions.size )
    }
    @Test
    fun `There are 3 positions adjacent to position 0,9`() {
        val positions = Position(0,9).surroundingPositions()
        assertEquals(3, positions.size )
    }
    @Test
    fun `There are 3 positions adjacent to position 9,9`() {
        val positions = Position(9,9).surroundingPositions()
        assertEquals(3, positions.size )
    }
    @Test
    fun `There are 5 positions adjacent to position 5,0`() {
        val positions = Position(5,0).surroundingPositions()
        assertEquals(5, positions.size )
    }
    @Test
    fun `There are 5 positions adjacent to position 5,9`() {
        val positions = Position(5,9).surroundingPositions()
        assertEquals(5, positions.size )
    }
    @Test
    fun `There are 5 positions adjacent to position 0,5`() {
        val positions = Position(0,5).surroundingPositions()
        assertEquals(5, positions.size )
    }
    @Test
    fun `There are 5 positions adjacent to position 9,5`() {
        val positions = Position(9,5).surroundingPositions()
        assertEquals(5, positions.size )
    }
    @Test
    fun `There are 8 positions adjacent to position 1,1`() {
        val positions = Position(1,1).surroundingPositions()
        assertEquals(8, positions.size )
    }
    @Test
    fun `Removing ship of length 5 from the list results in a max ship size of 4 `() {
        val config = Config()
        config.removeShip(listOf(Position(0,0),Position(1,0),Position(2,0),Position(3,0),Position(4,0)))
        assertEquals(4, config.maxShipSize())
    }
    @Test
    fun `Removing ship of length 4 from the list results in a max ship size of 5 `() {
        val config = Config()
        config.removeShip(listOf(Position(0,0),Position(1,0),Position(2,0),Position(3,0)))
        assertEquals(5, config.maxShipSize())
    }
    @Test
    fun `Removing ship of length 4 and then removing a ship of length 5 from the list results in a max ship size of 3 `() {
        val config = Config()
        config.removeShip(listOf(Position(0,0),Position(1,0),Position(2,0),Position(3,0)))
        config.removeShip(listOf(Position(0,1),Position(1,1),Position(2,1),Position(3,1),Position(4,1)))
        assertEquals(3, config.maxShipSize())
    }
    @Test
    fun `Removing ship of length 2 and when there are 2 ships of length 2 results in a max ship length of 2 `() {
        val config = Config(ships = mutableListOf(2,2,1))
        config.removeShip(listOf(Position(0,0),Position(1,0)))
        assertEquals(2, config.maxShipSize())
    }
    @Test
    fun `Fire more shots gives the original shot if the last shot is not 'H'`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Sunk
        )
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots,resultsMap, config)
        assertEquals(listOf(Position(0,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot if there are no additional shots`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        val shots = listOf(Position(0,1))
        val additionalShots = emptyList<Shot>()
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots,resultsMap, config)
        assertEquals(listOf(Position(0,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot if the first additional shot is already on the map`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit,
            Position(1,1) to Known.Water
        )
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots,resultsMap, config)
        assertEquals(listOf(Position(0,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot if the first additional shot is a miss`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        MockRequester.results = mutableMapOf("shots=A1B1" to listOf(Known.Hit,Known.Water))
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots,resultsMap, config)
        assertEquals(listOf(Position(0,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot plus the additional shot if the first additional shot is a hit`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        MockRequester.results = mutableMapOf("shots=A1B1" to listOf(Known.Hit,Known.Hit))
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots, resultsMap, config)
        assertEquals(listOf(Position(0,1),Position(1,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot plus the additional shot if the first additional shot is a hit and second additional shot is a miss`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        MockRequester.results = mutableMapOf("shots=A1B1" to listOf(Known.Hit,Known.Hit),
                                             "shots=A1B1C1" to listOf(Known.Hit,Known.Hit,Known.Water))
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1),Position(2,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots, resultsMap, config)
        assertEquals(listOf(Position(0,1),Position(1,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot plus the additional shots if the first additional shot is a hit and second additional shot is a hit`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        MockRequester.results = mutableMapOf("shots=A1B1" to listOf(Known.Hit,Known.Hit),
                                             "shots=A1B1C1" to listOf(Known.Hit,Known.Hit,Known.Hit))
        val shots = listOf(Position(0,1))
        val additionalShots = listOf(Position(1,1),Position(2,1))
        val config = Config(MockRequester)
        val resultingShots = shots.fireMoreShots(additionalShots, resultsMap, config)
        assertEquals(listOf(Position(0,1),Position(1,1),Position(2,1)), resultingShots)
    }
    @Test
    fun `Fire more shots gives the original shot and one more shot if the largest ship left to find is a submarine`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(0,1) to Known.Hit
        )
        MockRequester.results = mutableMapOf("shots=A1B1" to listOf(Known.Hit, Known.Hit),
                                             "shots=A1B1C1" to listOf(Known.Hit, Known.Hit, Known.Hit))
        val shots = listOf(Position(0,1),Position(1,1))
        val additionShots = listOf(Position(2,1))
        val config = Config(MockRequester, ships = mutableListOf(2))
        val resultingShots = shots.fireMoreShots(additionShots, resultsMap, config)
        assertEquals(listOf(Position(0,1),Position(1,1)), resultingShots)
    }

    @Test
    fun `A destroyer is surrounded by water if there is no water around it already`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(1,1) to Known.Sunk
        )
        listOf(Position(1,1)).surroundSunkenShipsWithWater(resultsMap)
        assertEquals(Known.DMZ, resultsMap[Position(0,0)])
        assertEquals(Known.DMZ, resultsMap[Position(0,1)])
        assertEquals(Known.DMZ, resultsMap[Position(0,2)])
        assertEquals(Known.DMZ, resultsMap[Position(2,0)])
        assertEquals(Known.DMZ, resultsMap[Position(2,1)])
        assertEquals(Known.DMZ, resultsMap[Position(2,2)])
        assertEquals(Known.DMZ, resultsMap[Position(1,0)])
        assertEquals(Known.DMZ, resultsMap[Position(1,2)])
        assertEquals(Known.Sunk, resultsMap[Position(1,1)])
    }
    @Test
    fun `A destroyer is surrounded by water only in the places there is no water around it already`() {
        val resultsMap:ResultMap = mutableMapOf(
            Position(1,1) to Known.Sunk ,
            Position(0,2) to Known.Water,
            Position(1,0) to Known.Water
        )
        listOf(Position(1,1)).surroundSunkenShipsWithWater(resultsMap)
        assertEquals(Known.DMZ, resultsMap[Position(0,0)])
        assertEquals(Known.DMZ, resultsMap[Position(0,1)])
        assertEquals(Known.Water, resultsMap[Position(0,2)])
        assertEquals(Known.DMZ, resultsMap[Position(2,0)])
        assertEquals(Known.DMZ, resultsMap[Position(2,1)])
        assertEquals(Known.DMZ, resultsMap[Position(2,2)])
        assertEquals(Known.Water, resultsMap[Position(1,0)])
        assertEquals(Known.DMZ, resultsMap[Position(1,2)])
        assertEquals(Known.Sunk, resultsMap[Position(1,1)])
    }

    @Test
    fun `firing a shot that results in a miss puts a miss on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        MockRequester.results = mutableMapOf("shots=A0" to listOf(Known.Water))
        val config = Config(MockRequester)
        config.fireShot(listOf(Position(0,0)),resultsMap)
        assertEquals(Known.Water, resultsMap[Position(0,0)])
    }
    @Test
    fun `firing a shot that results in a hit puts a hit on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        MockRequester.results = mutableMapOf("shots=B4" to listOf(Known.Hit))
        val config = Config(MockRequester)
        config.fireShot(listOf(Position(1,4)),resultsMap)
        assertEquals(Known.Hit, resultsMap[Position(1,4)])
    }
    @Test
    fun `firing two shots that results in a hit and miss puts a hit and miss on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        MockRequester.results = mutableMapOf("shots=B4A0" to listOf(Known.Hit,Known.Water))
        val config = Config(MockRequester)
        config.fireShot(listOf(Position(1,4),Position(0,0) ),resultsMap)
        assertEquals(Known.Hit, resultsMap[Position(1,4)])
        assertEquals(Known.Water, resultsMap[Position(0,0)])
    }
    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk`() {
        val resultsMap:ResultMap = mutableMapOf()
        MockRequester.results =  (0..99).toList().map{"shots=" + it.toPosition().toString() to listOf(Known.Water) }.toMap().toMutableMap()
        MockRequester.results["shots=D4"] = listOf(Known.Hit)
        MockRequester.results["shots=D5"] = listOf(Known.Hit)
        MockRequester.results["shots=D6"] = listOf(Known.Hit)
        MockRequester.results["shots=D5D4"] = listOf(Known.Hit, Known.Hit)
        MockRequester.results["shots=D6D5"] = listOf(Known.Hit,Known.Hit)
        MockRequester.results["shots=D6D5D4"] = listOf(Known.Hit,Known.Hit,Known.Hit)
        MockRequester.results["shots=D4D5"] = listOf(Known.Hit,Known.Hit)
        MockRequester.results["shots=D5D6"] = listOf(Known.Hit,Known.Hit)
        MockRequester.results["shots=D4D5D6"] = listOf(Known.Hit,Known.Hit,Known.Hit)
        MockRequester.results["shots=D5D4D6"] = listOf(Known.Hit,Known.Hit,Known.Hit)
        val config = Config(MockRequester, ships = mutableListOf(3) )
        config.fireShotsUntilAllSunk(resultsMap)
        assertEquals(3, resultsMap.values.filter{it == Known.Hit || it == Known.Sunk }.size)
    }

    //===== Integration tests ==============================================================================//
    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service`() {
        val resultsMap:ResultMap = mutableMapOf()
        val config = Config()//player = "mike")
        config.fireShotsUntilAllSunk(resultsMap)
        assertEquals(18, resultsMap.values.filter{it == Known.Hit || it == Known.Sunk}.size)
    }

    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service with game1`() {
        val resultsMap:ResultMap = mutableMapOf()
        val config = Config(game = "game1") // player = "mike1")
        config.fireShotsUntilAllSunk(resultsMap)
        assertEquals(18, resultsMap.values.filter{it == Known.Hit || it == Known.Sunk}.size)
    }

    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service with game2`() {
        val resultsMap:ResultMap = mutableMapOf()
        val config = Config(game = "game2") //, player = "mike1")
        config.fireShotsUntilAllSunk(resultsMap)
        assertEquals(18, resultsMap.values.filter{it == Known.Hit || it == Known.Sunk}.size)
    }

}