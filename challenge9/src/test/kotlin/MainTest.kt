import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MainTest {
    @Test
    fun `Create an Empty GameLedger`() {
        val gameLedger = GameLedger
        assertTrue(gameLedger is GameLedger)

        GameLedger.reset()
        assertTrue(GameLedger.transactions.isEmpty())
    }

    @Test
    fun `Transaction is added to GameLedger for new player`() {
        GameLedger.reset() // need to reset as is a singleton so gets updated by other tests.

        val newPlayer = object:Player{override val name = "A playerCredited"}
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        val firstTransaction = GameLedger.transactions[0] as GameLedger.Crediting
        assertEquals(firstTransaction.playerCredited, newPlayer)
        assertEquals(firstTransaction.amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions.size, 1)

        val firstTransaction = GameLedger.transactions[0] as GameLedger.Crediting
        assertEquals(firstTransaction.playerCredited, player)
        assertEquals(firstTransaction.amount, GBP(100))

        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))
        assertEquals(GameLedger.transactions.size, 2)

        val secondTransaction = GameLedger.transactions[1] as GameLedger.Crediting
        assertEquals(secondTransaction.playerCredited, player)
        assertEquals(secondTransaction.amount, GBP(100))
    }

    @Test
    fun `Transaction is added to GameLedger when one player pays rent to another`() {

        GameLedger.reset()

        val player1 = object:Player{override val name = "Player 1"}
        val player2 = object:Player{override val name = "Player 2"}
        val rent = GBP(20)

        GameLedger.payRent(player1, player2, rent)

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerPayingAnotherPlayer
        assertEquals(firstTransaction.playerCredited, player1)
        assertEquals(firstTransaction.playerDebited, player2)
        assertEquals(firstTransaction.amount, GBP(20))

    }

    @Test
    fun `Transaction is added when a player purchases a property from the bank`() {

        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        val location = object:Purchaseable{override val purchasePrice = GBP(150)}
        val purchasePrice = GBP(200)

        GameLedger.purchaseLocation(player, location, purchasePrice)

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerPurchasingProperty
        assertEquals(firstTransaction.playerDebited, player)
        assertEquals(firstTransaction.location, location)
        assertEquals(firstTransaction.amount, purchasePrice)
    }

    @Test
    fun `Transaction is added when a player builds on a location`() {

        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        val location = object:Buildable{
            override val purchasePrice = GBP(200)
            override val miniStore = DevelopmentType.BuildCostAndRent(GBP(100), GBP(10) )
            override val supermarket = DevelopmentType.BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = DevelopmentType.BuildCostAndRent(GBP(300), GBP(30))
        }
        val developmentCost = GBP(300)
        val buildingType = Building.minimarket

        GameLedger.buildOnLocation(player, location, buildingType, developmentCost )

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerBuildingOnLocation
        assertEquals(firstTransaction.playerDebited, player)
        assertEquals(firstTransaction.location, location)
        assertEquals(firstTransaction.building, buildingType)
        assertEquals(firstTransaction.amount, developmentCost)

    }
}