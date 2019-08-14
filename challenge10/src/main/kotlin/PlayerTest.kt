import org.junit.jupiter.api.Assertions.assertEquals
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

        var dice = Dice()
        while (dice.totalValue != 4) {
            dice = Dice()
        }

        player.move(dice)

        assertEquals(locations[4], player.currentLocation)
        assertEquals(false, player.hasPassedGo)
    }

    @Test
    fun `Location of a player after moving a player at position 4 using a dice value of 6 is location 10`() {
        val player = Player("Mike")

        var dice = Dice()
        while (dice.totalValue != 4) {
            dice = Dice()
        }

        player.move(dice)

        while (dice.totalValue != 6) {
            dice = Dice()
        }

        player.move(dice)

        assertEquals(locations[10], player.currentLocation)
        assertEquals(false, player.hasPassedGo)
    }

    @Test
    fun `Location of a player after moving a player at position 10 using a dice value of 5 is location 2`() {
        val player = Player("Mike")

        var dice = Dice()
        while (dice.totalValue != 4) {
            dice = Dice()
        }
        player.move(dice)

        while (dice.totalValue != 6) {
            dice = Dice()
        }
        player.move(dice)

        while (dice.totalValue != 5) {
            dice = Dice()
        }
        player.move(dice)

        assertEquals(locations[2], player.currentLocation)
        assertEquals(true, player.hasPassedGo)

    }
}
