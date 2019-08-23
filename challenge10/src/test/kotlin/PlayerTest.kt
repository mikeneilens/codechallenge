import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `Location of a new player is first location on the board`() {
        val player = Player("Mike")
        assertEquals("Mike", player.name)
        assertEquals(locations.first(), player.currentLocation)
        assertEquals(false, player.hasPassedGo)
    }

    @Test
    fun `Location of a player after moving a new player using a dice value of 4 is location 4`() {
        val player = Player("Mike")

        val dice = Dice(PredictableDiceValue(listOf(3,1)))
        player.move(dice)

        assertEquals(locations[4], player.currentLocation)
        assertEquals(false, player.hasPassedGo)
    }

    @Test
    fun `Location of a player after moving a player at position 4 using a dice value of 6 is location 10`() {
        val player = Player("Mike")

        var dice = Dice(PredictableDiceValue(listOf(3,1)))
        player.move(dice)

        dice = Dice(PredictableDiceValue(listOf(4,2)))
        player.move(dice)

        assertEquals(locations[10], player.currentLocation)
        assertEquals(false, player.hasPassedGo)
    }

    @Test
    fun `Location of a player after moving a player at position 10 using a dice value of 5 is location 2`() {
        val player = Player("Mike")


        var dice = Dice(PredictableDiceValue(listOf(3,1)))
        player.move(dice)

        dice = Dice(PredictableDiceValue(listOf(4,2)))
        player.move(dice)

        dice = Dice(PredictableDiceValue(listOf(2,3)))
        player.move(dice)

        assertEquals(locations[2], player.currentLocation)
        assertEquals(true, player.hasPassedGo)

    }

    @Test
    fun `Moving a player who has passed go resets the hasPassedGo flag to false`() {
        val player = Player("Mike")

        var dice = Dice(PredictableDiceValue(listOf(3,1)))
        player.move(dice)

        dice = Dice(PredictableDiceValue(listOf(4,2)))
        player.move(dice)

        dice = Dice(PredictableDiceValue(listOf(2,3)))
        player.move(dice)

        assertEquals(true, player.hasPassedGo)

        player.move(dice)

        assertEquals(false, player.hasPassedGo)
    }
}
