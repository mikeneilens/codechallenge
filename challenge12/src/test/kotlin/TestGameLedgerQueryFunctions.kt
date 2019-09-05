import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestGameLedgerQueryFunctions {
    val mike = object:Player { override val name = "Mike"}
    val jake = object:Player { override val name = "Jake"}

    @BeforeEach
    fun `delete all transactions`() {
        GameLedger.transactions.clear()
    }

    @Test
    fun `get balance for a player not on GameLedger returns zero`(){
        val balanceForPlayer = GameLedger.balanceFor(mike)

        assertEquals(Credit(GBP(0)),balanceForPlayer )
    }

    @Test
    fun `getting balance for a new player added with £500 to the GameLedger gives £500` () {
        GameLedger.addNewPlayer(mike, GBP(500))
        val balanceForPlayer = GameLedger.balanceFor(mike)

        assertEquals(Credit(GBP(500)),balanceForPlayer)
    }

    @Test
    fun `getting balance for a player with no credit pays rent of 200` () {
        GameLedger.payRent(jake,mike,GBP(200))
        val balanceForPlayer = GameLedger.balanceFor(mike)

        assertEquals(Debt(GBP(200)),balanceForPlayer)
    }

    @Test
    fun `getting balance for a player with £200 credit and £150 debt has £50 credit in total` () {
        GameLedger.addNewPlayer(mike, GBP(100))
        GameLedger.payRent(jake,mike,GBP(150))
        GameLedger.addFeeForPlayerPassingGo(mike,GBP(100))
        val balanceForPlayer = GameLedger.balanceFor(mike)

        assertEquals(Credit(GBP(50)),balanceForPlayer)
    }


}