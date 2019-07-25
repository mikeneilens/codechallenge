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

        assertEquals(GameLedger.transactions[0].playerCredited, newPlayer)
        assertEquals(GameLedger.transactions[0].amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        GameLedger.reset()

        val player = object:Player{override val name = "A playerCredited"}
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions.size, 1)
        assertEquals(GameLedger.transactions[0].playerCredited, player)
        assertEquals(GameLedger.transactions[0].amount, GBP(100))

        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))
        assertEquals(GameLedger.transactions.size, 2)
        assertEquals(GameLedger.transactions[0].playerCredited, player)
        assertEquals(GameLedger.transactions[0].amount, GBP(100))
        assertEquals(GameLedger.transactions[1].playerCredited, player)
        assertEquals(GameLedger.transactions[1].amount, GBP(100))
    }

    @Test
    fun `Transaction is added to GameLedger when one player pays rent to another`() {

        GameLedger.reset()

        val player1 = object:Player{override val name = "Player 1"}
        val player2 = object:Player{override val name = "Player 2"}
        val rent = GBP(20)

        GameLedger.payRent(player1, player2, rent)
    }
}