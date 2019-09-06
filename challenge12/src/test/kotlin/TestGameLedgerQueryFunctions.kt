import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestGameLedgerQueryFunctions {
    val playerMike = Player("Mike")
    val playerJake = Player("Jake")
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
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Credit(GBP(0)),balanceForPlayer )
    }

    @Test
    fun `getting balance for a new player added with £500 to the GameLedger gives £500` () {
        GameLedger.addNewPlayer(playerMike, GBP(500))
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Credit(GBP(500)),balanceForPlayer)
    }

    @Test
    fun `getting balance for a player with no credit pays rent of 200` () {
        GameLedger.payRent(playerJake,playerMike,GBP(200))
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Debt(GBP(200)),balanceForPlayer)
    }

    @Test
    fun `getting balance for a player with £200 credit and £150 debt has £50 credit in total` () {
        GameLedger.addNewPlayer(playerMike, GBP(100))
        GameLedger.payRent(playerJake,playerMike,GBP(150))
        GameLedger.addFeeForPlayerPassingGo(playerMike,GBP(100))
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Credit(GBP(50)),balanceForPlayer)
    }

    @Test
    fun `getting locations for a player returns an empty list if a player hasnt bought any`() {
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf<OwnedLocation>(), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns a single undeveloped location if a player has only one undeveloeprd location`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf(OwnedLocation(playerMike, shop, Building.Undeveloped)), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns a single location with supermarket if a player has bout a location and developed it`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, GBP(200))
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf(OwnedLocation(playerMike, shop, Building.Supermarket)), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns two locations if a player has bought two locations`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, GBP(200))
        GameLedger.purchaseLocation(playerMike, warehouse, GBP(100))

        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(2, locationsForPlayer.size)
        assertEquals(OwnedLocation(playerMike, shop, Building.Supermarket), locationsForPlayer[0])
        assertEquals(OwnedLocation(playerMike, warehouse, Building.Undeveloped), locationsForPlayer[1])
    }

    @Test
    fun `getting locations for a player returns no locations if the same property has been subsequently bought by another player`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.purchaseLocation(playerJake, shop, GBP(200))

        val locationsForMike = GameLedger.locationsFor(playerMike)
        assertEquals(0, locationsForMike.size)

        val locationsForJake = GameLedger.locationsFor(playerJake)
        assertEquals(listOf(OwnedLocation(playerJake, shop, Building.Undeveloped)), locationsForJake)

    }

    @Test
    fun `ownedLocation returns an empty list when nothing has been added to the GameLedger`() {
        assertEquals(0, GameLedger.ownedLocations.size)
    }
    @Test
    fun `ownedLocations returns an empty list when transactions containing no location information have been added to the GameLedger()`(){
        GameLedger.addFeeForPlayerPassingGo(playerMike,Go.fee)
        GameLedger.payRent(playerJake, playerMike, GBP(20))
        GameLedger.addNewPlayer(playerMike, GBP(200))
        assertEquals(0, GameLedger.ownedLocations.size)
    }
    @Test
    fun `ownedLocations returns a single owned location when one has been added to GameLEdger`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)

        assertEquals(1, GameLedger.ownedLocations.size)
        assertEquals(OwnedLocation(playerMike, shop, Building.Undeveloped), GameLedger.ownedLocations[0])
    }
    @Test
    fun `ownedLocations returns a single owned location with correct building when one location has been added to GameLEdger and then built on`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, shop.supermarket.buildingCost)

        assertEquals(1, GameLedger.ownedLocations.size)
        assertEquals(OwnedLocation(playerMike, shop, Building.Supermarket), GameLedger.ownedLocations[0])
    }
    @Test
    fun `ownedLocations returns two owned locations when two locations have been added to GameLedger` () {
        GameLedger.purchaseLocation(playerJake, warehouse, warehouse.purchasePrice)
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, shop.supermarket.buildingCost)

        assertEquals(2, GameLedger.ownedLocations.size)
        assertEquals(OwnedLocation(playerJake, warehouse, Building.Undeveloped), GameLedger.ownedLocations[0])
        assertEquals(OwnedLocation(playerMike, shop, Building.Supermarket), GameLedger.ownedLocations[1])

    }
}