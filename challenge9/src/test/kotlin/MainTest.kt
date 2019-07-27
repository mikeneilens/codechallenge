import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MainTest {

    @Test
    fun `Transaction is added to GameLedger for new player`() {
        val newPlayer = object:Player{override val name = "A playerCredited"}
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.Crediting
        assertEquals(lastTransaction.playerCredited, newPlayer)
        assertEquals(lastTransaction.amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        val originalNoOfTransactions = GameLedger.transactions.size

        val player = object:Player{override val name = "A playerCredited"}
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions.size, originalNoOfTransactions + 1)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.Crediting
        assertEquals(lastTransaction.playerCredited, player)
        assertEquals(lastTransaction.amount, GBP(100))

        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))
        assertEquals(GameLedger.transactions.size, originalNoOfTransactions + 2)

        val secondTransaction = GameLedger.transactions.last() as GameLedger.Crediting
        assertEquals(secondTransaction.playerCredited, player)
        assertEquals(secondTransaction.amount, GBP(100))
    }

    @Test
    fun `Transaction is added to GameLedger when one player pays rent to another`() {
        val player1 = object:Player{override val name = "Player 1"}
        val player2 = object:Player{override val name = "Player 2"}
        val rent = GBP(20)

        GameLedger.payRent(player1, player2, rent)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerPayingAnotherPlayer
        assertEquals(lastTransaction.playerCredited, player1)
        assertEquals(lastTransaction.playerDebited, player2)
        assertEquals(lastTransaction.amount, GBP(20))

    }

    @Test
    fun `Transaction is added when a player purchases a property from the bank`() {
        val player = object:Player{override val name = "A playerCredited"}
        val location = object:Purchaseable{override val purchasePrice = GBP(150)}
        val purchasePrice = GBP(200)

        GameLedger.purchaseLocation(player, location, purchasePrice)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerPurchasingProperty
        assertEquals(lastTransaction.playerDebited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, purchasePrice)
    }

    @Test
    fun `Transaction is added when a player builds on a location`() {
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

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerBuildingOnLocation
        assertEquals(lastTransaction.playerDebited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.building, buildingType)
        assertEquals(lastTransaction.amount, developmentCost)

    }
}