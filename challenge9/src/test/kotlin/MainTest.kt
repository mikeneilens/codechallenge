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
        GameLedger.reset()

        val newPlayer = object:Player{override val name = "A playerCredited"}
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerCredited
        assertEquals(firstTransaction.playerCredited, newPlayer)
        assertEquals(firstTransaction.amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions.size, 1)

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerCredited
        assertEquals(firstTransaction.playerCredited, player)
        assertEquals(firstTransaction.amount, GBP(100))

        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))
        assertEquals(GameLedger.transactions.size, 2)

        val secondTransaction = GameLedger.transactions[1] as GameLedger.PlayerCredited
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
        assertEquals(firstTransaction.playerDebted, player2)
        assertEquals(firstTransaction.amount, GBP(20))

    }

    @Test
    fun `Transaction is added when a player purchases a property from the bank`() {

        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        val location = object:Purchaseable{override val purchasePrice = GBP(200)}
        val purchasePrice = GBP(200)

        GameLedger.purchaseLocation(player, location, purchasePrice)

        val firstTransaction = GameLedger.transactions[0] as GameLedger.PlayerPurchasesProerty
        assertEquals(firstTransaction.playerDebted, player)
        assertEquals(firstTransaction.location, location)
        assertEquals(firstTransaction.amount, purchasePrice)
    }
}