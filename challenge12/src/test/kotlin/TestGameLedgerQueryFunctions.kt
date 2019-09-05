import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestGameLedgerQueryFunctions {
    @Test
    fun `get balance for a player not on GameLedger`(){
        val mike = object:Player { override val name = "Mike"}
        val balanceForPlayer = GameLedger.balanceFor(mike)

        assertEquals(GBP(0),balanceForPlayer )
    }

}