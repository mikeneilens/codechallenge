
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `simple request`() {
        val result = RequestObject.makeRequest()
        assertEquals(listOf("M"), result)
    }

    object mockRequestor:Requester {
        var results:MutableMap<String, List<Result>> = mutableMapOf()
        override fun makeRequest(param:String):List<Result> = results[param] ?: listOf()
    }
    @Test
    fun `coordinate for integet 0 should be A0`() {
        assertEquals("A0", 0.asCoordinate())
    }
    @Test
    fun `coordinate for integet 9 should be J0`() {
        assertEquals("J0", 9.asCoordinate())
    }
    @Test
    fun `coordinate for integet 90 should be A9`() {
        assertEquals("A9", 90.asCoordinate())
    }
    @Test
    fun `coordinate for integet 99 should be J9`() {
        assertEquals("J9", 99.asCoordinate())
    }

    @Test
    fun `integer for coordinate A0 should be 0`() {
        assertEquals(0, "A0".asInt())
    }
    @Test
    fun `integer for coordinate J0 should be 9`() {
        assertEquals(9, "J0".asInt())
    }
    @Test
    fun `integer for coordinate A9 should be 90`() {
        assertEquals(90, "A9".asInt())
    }
    @Test
    fun `integer for coordinate J9 should be 99`() {
        assertEquals(99, "J9".asInt())
    }

    @Test
    fun `firing a shot that results in a miss puts a miss on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        mockRequestor.results = mutableMapOf("shots=A0" to listOf("M"))
        val result = fireShot(listOf("A0"),resultsMap, mockRequestor)
        assertEquals("M", result["A0"])
    }
    @Test
    fun `firing a shot that results in a hit puts a hit on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        mockRequestor.results = mutableMapOf("shots=B4" to listOf("H"))
        val result = fireShot(listOf("B4"),resultsMap, mockRequestor)
        assertEquals("H", result["B4"])
    }
    @Test
    fun `firing two shots that results in a hit and miss puts a hit and miss on the results map`() {
        val resultsMap:ResultMap = mutableMapOf()
        mockRequestor.results = mutableMapOf("shots=B4A0" to listOf("H","M"))
        val result = fireShot(listOf("B4","A0"),resultsMap, mockRequestor)
        assertEquals("H", result["B4"])
        assertEquals("M", result["A0"])
    }
    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk`() {
        val resultsMap:ResultMap = mutableMapOf()
        mockRequestor.results =  (0..99).toList().shuffled().map{"shots=" + it.asCoordinate() to kotlin.collections.listOf("M") }.toMap().toMutableMap()
        mockRequestor.results["shots=D4"] = listOf("H")
        mockRequestor.results["shots=D5"] = listOf("H")
        mockRequestor.results["shots=D6"] = listOf("H")
        val result = fireShotsUntilAllSunk(resultsMap, mockRequestor, 3)
        assertEquals(3, result.values.filter{it == "H"}.size)
    }

    @Test
    fun `firing more shots to the right when last shot hit and the square to the right is a ship results in square to the right being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4E4"] = listOf("H","H")
        val result = fireMoreShots(Shot::shotToRightOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["E4"])
    }

    @Test
    fun `firing more shots to the right when last shot hit and the two squares to the right are ships results in both squares to the right being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4E4"] = listOf("H","H")
        mockRequestor.results["shots=D4E4F4"] = listOf("H","H","H")
        val result = fireMoreShots(Shot::shotToRightOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["E4"])
        assertEquals("H",resultsMap["F4"])
    }
    @Test
    fun `firing more shots to the right when last shot hit and the two squares to the right are ship and sea results in one squares to the right being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4E4"] = listOf("H","H")
        mockRequestor.results["shots=D4E4F4"] = listOf("H","H","M")
        val result = fireMoreShots(Shot::shotToRightOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["E4"])
        assertEquals("M",resultsMap["F4"])
    }

    @Test
    fun `firing more shots to the left when last shot hit and the square to the left is a ship results in square to the left being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4C4"] = listOf("H","H")
        val result = fireMoreShots(Shot::shotToLeftOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["C4"])
        assertEquals(listOf("D4","C4"), result)
    }

    @Test
    fun `firing more shots to the left when last shot hit and the two squares to the left are ships results in both squares to the left being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4C4"] = listOf("H","H")
        mockRequestor.results["shots=D4C4B4"] = listOf("H","H","H")
        val result = fireMoreShots(Shot::shotToLeftOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["C4"])
        assertEquals("H",resultsMap["B4"])
        assertEquals(listOf("D4","C4","B4"), result)
    }
    @Test
    fun `firing more shots to the left when last shot hit and the two squares to the left are ship and sea results in one squares to the left being hit`() {
        val resultsMap:ResultMap = mutableMapOf("D4" to "H")
        val shots = listOf("D4")
        mockRequestor.results["shots=D4C4"] = listOf("H","H")
        mockRequestor.results["shots=D4C4B4"] = listOf("H","H","M")
        val result = fireMoreShots(Shot::shotToLeftOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["D4"])
        assertEquals("H",resultsMap["C4"])
        assertEquals("M",resultsMap["B4"])
        assertEquals(listOf("D4","C4"), result)
    }
    @Test
    fun `firing more shots up when last shot hit and the three squares above contain ships`() {
        val resultsMap:ResultMap = mutableMapOf("B5" to "H")
        val shots = listOf("B5")
        mockRequestor.results["shots=B5B4"] = listOf("H","H")
        mockRequestor.results["shots=B5B4B3"] = listOf("H","H","H")
        mockRequestor.results["shots=B5B4B3B2"] = listOf("H","H","H","H")
        val result = fireMoreShots(Shot::shotUpOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["B5"])
        assertEquals("H",resultsMap["B4"])
        assertEquals("H",resultsMap["B3"])
        assertEquals("H",resultsMap["B2"])
    }
    @Test
    fun `firing more shots down when last shot hit and the three squares below contain ships`() {
        val resultsMap:ResultMap = mutableMapOf("B5" to "H")
        val shots = listOf("B5")
        mockRequestor.results["shots=B5B6"] = listOf("H","H")
        mockRequestor.results["shots=B5B6B7"] = listOf("H","H","H")
        mockRequestor.results["shots=B5B6B7B8"] = listOf("H","H","H","H")
        val result = fireMoreShots(Shot::shotDownOrNull, shots, resultsMap, mockRequestor)
        assertEquals("H",resultsMap["B5"])
        assertEquals("H",resultsMap["B6"])
        assertEquals("H",resultsMap["B7"])
        assertEquals("H",resultsMap["B8"])
    }
    @Test
    fun `shot to right or null gives null if the square two places to the right of the current position contains a ship `() {
        val resultsMap:ResultMap = mutableMapOf("E5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToRightOrNull(resultsMap))
    }
    @Test
    fun `shot to right or null gives shot to right of current shot if the square the left of the current position contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("B5" to "H")
        val shot = "C5"
        assertEquals("D5", shot.shotToRightOrNull(resultsMap))
    }
    @Test
    fun `shot to right or null gives shot to right of current shot if the square the left of the current position is empty and positions above and below are empty `() {
        val resultsMap:ResultMap = mutableMapOf()
        val shot = "C5"
        assertEquals("D5", shot.shotToRightOrNull(resultsMap))
    }
    @Test
    fun `shot to right or null gives null if the square the left of the current position is empty and positions above contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C4" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToRightOrNull(resultsMap))
    }
    @Test
    fun `shot to right or null gives null if the square the left of the current position is empty and positions below contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C6" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToRightOrNull(resultsMap))
    }

    //
    @Test
    fun `shot to left or null gives null if the square two places to the left of the current position contains a ship `() {
        val resultsMap:ResultMap = mutableMapOf("A5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToLeftOrNull(resultsMap))
    }
    @Test
    fun `shot to left or null gives shot to left of current shot if the square the right of the current position contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("D5" to "H")
        val shot = "C5"
        assertEquals("B5", shot.shotToLeftOrNull(resultsMap))
    }
    @Test
    fun `shot to left or null gives shot to left of current shot if the square the right of the current position is empty and positions above and below are empty `() {
        val resultsMap:ResultMap = mutableMapOf()
        val shot = "C5"
        assertEquals("B5", shot.shotToLeftOrNull(resultsMap))
    }
    @Test
    fun `shot to left or null gives null if the square the right of the current position is empty and positions above contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C4" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToLeftOrNull(resultsMap))
    }
    @Test
    fun `shot to left or null gives null if the square the right of the current position is empty and positions below contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C6" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotToLeftOrNull(resultsMap))
    }

    @Test
    fun `shot Up or null gives null if the square two places above the current position contains a ship `() {
        val resultsMap:ResultMap = mutableMapOf("C3" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotUpOrNull(resultsMap))
    }
    @Test
    fun `shot Up or null gives shot above current shot if the square below the current position contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C6" to "H")
        val shot = "C5"
        assertEquals("C4", shot.shotUpOrNull(resultsMap))
    }
    @Test
    fun `shot Up or null gives shot above current shot if the square below current position is empty and positions left and right of current shot are empty `() {
        val resultsMap:ResultMap = mutableMapOf()
        val shot = "C5"
        assertEquals("C4", shot.shotUpOrNull(resultsMap))
    }
    @Test
    fun `shot Up or null gives null if the square below current position is empty and position to right contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("D5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotUpOrNull(resultsMap))
    }
    @Test
    fun `shot Up or null gives null if below the current position is empty and positions to left contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("B5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotUpOrNull(resultsMap))
    }

    @Test
    fun `shot Down or null gives null if the square two places below the current position contains a ship `() {
        val resultsMap:ResultMap = mutableMapOf("C7" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotDownOrNull(resultsMap))
    }
    @Test
    fun `shot Down or null gives shot below current shot if the square above the current position contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("C4" to "H")
        val shot = "C5"
        assertEquals("C6", shot.shotDownOrNull(resultsMap))
    }
    @Test
    fun `shot Up or null gives shot below current shot if the square above current position is empty and positions left and right of current shot are empty `() {
        val resultsMap:ResultMap = mutableMapOf()
        val shot = "C5"
        assertEquals("C6", shot.shotDownOrNull(resultsMap))
    }
    @Test
    fun `shot Down or null gives null if the square above current position is empty and position to right contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("D5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotDownOrNull(resultsMap))
    }
    @Test
    fun `shot Down or null gives null if below the current position is empty and positions to left contains a hit `() {
        val resultsMap:ResultMap = mutableMapOf("B5" to "H")
        val shot = "C5"
        assertEquals(null, shot.shotDownOrNull(resultsMap))
    }

    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service`() {
        val resultsMap:ResultMap = mutableMapOf()
        val result = fireShotsUntilAllSunk(resultsMap)
        assertEquals(18, result.values.filter{it == "H" || it == "S"}.size)
    }

    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service with game1`() {
        val resultsMap:ResultMap = mutableMapOf()
        val result = fireShotsUntilAllSunk(resultsMap, game = "game1")
        assertEquals(18, result.values.filter{it == "H" || it == "S"}.size)
    }

    @Test
    fun `firing shots until all ships are sunk returns a map with all ships sunk when using real service with game2`() {
        val resultsMap:ResultMap = mutableMapOf()
        val result = fireShotsUntilAllSunk(resultsMap, game = "game2")
        assertEquals(18, result.values.filter{it == "H" || it == "S"}.size)
    }

}