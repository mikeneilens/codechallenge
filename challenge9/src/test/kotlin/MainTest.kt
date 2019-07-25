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

        val newPlayer = object:Player{override val name = "A player"}
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        assertEquals(GameLedger.transactions[0].player, newPlayer)
        assertEquals(GameLedger.transactions[0].amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        GameLedger.reset()

        val player = object:Player{override val name = "A player"}
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions[0].player, player)
        assertEquals(GameLedger.transactions[0].amount, GBP(100))

    }
}