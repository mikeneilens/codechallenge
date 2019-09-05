import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestGameLedgerQueryFunctions {
    val mike = Player("Mike")
    val jake = Player("Jake")
    val warehouse = FactoryOrWarehouse("Magna Park")
    val shop = RetailSite(Group.Red,"Victoria", GBP(100),
        undeveloped = DevelopmentType.RentOnly(GBP(10)),
        miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(20)),
        supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(30)),
        megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(40))
    )

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

    @Test
    fun `getting locations for a player returns an empty list if a player hasnt bought any`() {
        val locationsForPlayer = GameLedger.locationsFor(mike)

        assertEquals(listOf<OwnedLocation>(), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns a single undeveloped location if a player has only one undeveloeprd location`() {
        GameLedger.purchaseLocation(mike, shop, GBP(200))
        val locationsForPlayer = GameLedger.locationsFor(mike)

        assertEquals(listOf(OwnedLocation(shop, Building.undeveloped)), locationsForPlayer)
    }
    
}