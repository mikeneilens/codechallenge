import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MainTest {
    @Test
    fun `Create an Empty GameLedger`() {
        val gameLedger = GameLedger
        assertTrue(gameLedger is GameLedger)
    }

    @Test
    fun `Transaction is added to GameLedger for new player`() {

        val newPlayer = object:Player{override val name = "A player"}
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        assertEquals(GameLedger.transactions[0].player, newPlayer)
        assertEquals(GameLedger.transactions[0].amount, GBP(500))
    }
}