import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestGameLedgerQueryFunctions {
    val playerMike = Player("Mike")
    val playerJake = Player("Jake")
    val warehouse = FactoryOrWarehouse("Magna Park")
    val shop = RetailSite(Group.Red,"Victoria", GBP(100),
        rent = GBP(10),
        miniStore = BuildCostAndRent(GBP(100),GBP(20)),
        supermarket =  BuildCostAndRent(GBP(200),GBP(30)),
        megastore = BuildCostAndRent(GBP(300),GBP(40))
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
    fun `getting balance for a new player added with 500 to the GameLedger gives 500` () {
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
    fun `getting balance for a player with 200 credit and 150 debt has 50 credit in total` () {
        GameLedger.addNewPlayer(playerMike, GBP(100))
        GameLedger.payRent(playerJake,playerMike,GBP(150))
        GameLedger.addFeeForPlayerPassingGo(playerMike,GBP(100))
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Credit(GBP(50)),balanceForPlayer)
    }
    @Test
    fun `getting balance when every type of credit transaction has been created`() {
        GameLedger.addNewPlayer(playerMike, GBP(10))
        GameLedger.addFeeForPlayerPassingGo(playerMike, GBP(20))
        GameLedger.payRent(playerMike, playerJake, GBP(30))
        GameLedger.sellBuilding(playerMike, shop, Building.Supermarket, GBP(40))
        GameLedger.mortgageLocation(playerMike, shop, GBP(50))

        assertEquals(Credit(GBP(10 + 20 + 30 + 40 + 50)), GameLedger.balanceFor(playerMike))
        assertEquals(Debt(GBP(30)), GameLedger.balanceFor(playerJake))
    }

    @Test
    fun `getting balance for a player with 150 credit and 200 debt has 50 debt in total` () {
        GameLedger.addNewPlayer(playerMike, GBP(100))
        GameLedger.payRent(playerJake,playerMike,GBP(200))
        GameLedger.addFeeForPlayerPassingGo(playerMike,GBP(50))
        val balanceForPlayer = GameLedger.balanceFor(playerMike)

        assertEquals(Debt(GBP(50)),balanceForPlayer)
    }
    @Test
    fun `getting locations for a player returns an empty list if a player hasnt bought any`() {
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf<LocationStatus>(), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns a single undeveloped location if a player has only one undeveloeprd location`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf(LocationStatus(playerMike, shop, Building.Undeveloped)), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns a single location with supermarket if a player has bout a location and developed it`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, GBP(200))
        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(listOf(LocationStatus(playerMike, shop, Building.Supermarket)), locationsForPlayer)
    }

    @Test
    fun `getting locations for a player returns two locations if a player has bought two locations`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, GBP(200))
        GameLedger.purchaseLocation(playerMike, warehouse, GBP(100))

        val locationsForPlayer = GameLedger.locationsFor(playerMike)

        assertEquals(2, locationsForPlayer.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Supermarket), locationsForPlayer[0])
        assertEquals(LocationStatus(playerMike, warehouse, Building.Undeveloped), locationsForPlayer[1])
    }
    @Test
    fun `getting locations for a player returns no locations if the same property has been subsequently bought by another player`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.purchaseLocation(playerJake, shop, GBP(200))

        val locationsForMike = GameLedger.locationsFor(playerMike)
        assertEquals(0, locationsForMike.size)

        val locationsForJake = GameLedger.locationsFor(playerJake)
        assertEquals(listOf(LocationStatus(playerJake, shop, Building.Undeveloped)), locationsForJake)

    }
    @Test
    fun `getting locations for a player returns a mortgaged location in undeveloped state if it has been mortgaged`() {
        GameLedger.purchaseLocation(playerMike, shop, GBP(200))
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, GBP(100))
        GameLedger.mortgageLocation(playerMike, shop, GBP(50))

        val locationsForMike = GameLedger.locationsFor(playerMike)

        assertEquals(1, locationsForMike.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Undeveloped, true), locationsForMike[0])

    }
    @Test
    fun `locationStatus returns an empty list when nothing has been added to the GameLedger`() {
        assertEquals(0, GameLedger.locationStatuses.size)
    }
    @Test
    fun `locationStatuss returns an empty list when transactions containing no location information have been added to the GameLedger()`(){
        GameLedger.addFeeForPlayerPassingGo(playerMike,Go.fee)
        GameLedger.payRent(playerJake, playerMike, GBP(20))
        GameLedger.addNewPlayer(playerMike, GBP(200))
        assertEquals(0, GameLedger.locationStatuses.size)
    }
    @Test
    fun `locationStatuss returns a single locationStatus when one has been added to GameLEdger`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)

        assertEquals(1, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Undeveloped), GameLedger.locationStatuses[0])
    }
    @Test
    fun `locationStatuss returns a single locationStatus with correct building when one location has been added to GameLEdger and then built on`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, shop.supermarket.buildingCost)

        assertEquals(1, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Supermarket), GameLedger.locationStatuses[0])
    }
    @Test
    fun `locationStatuss returns two locationStatuss when two locations have been added to GameLedger` () {
        GameLedger.purchaseLocation(playerJake, warehouse, warehouse.purchasePrice)
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Supermarket, shop.supermarket.buildingCost)

        assertEquals(2, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerJake, warehouse, Building.Undeveloped), GameLedger.locationStatuses[0])
        assertEquals(LocationStatus(playerMike, shop, Building.Supermarket), GameLedger.locationStatuses[1])
    }
    @Test
    fun `locationStatus returns an locationStatus with a supermarket if aplayer has sold a megastore`() {
        GameLedger.sellBuilding(playerMike, shop, Building.Megastore, shop.megastore.buildingCost)

        assertEquals(1, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Supermarket), GameLedger.locationStatuses[0])
    }
    @Test
    fun `locationStatus returns an locationStatus with a minimarket if aplayer has sold a supermarket`() {
        GameLedger.sellBuilding(playerMike, shop, Building.Supermarket, shop.supermarket.buildingCost)

        assertEquals(1, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Minimarket), GameLedger.locationStatuses[0])
    }
    @Test
    fun `locationStatus returns an locationStatus with undeveloepd if aplayer has sold a minimarket`() {
        GameLedger.sellBuilding(playerMike, shop, Building.Minimarket, shop.supermarket.buildingCost)

        assertEquals(1, GameLedger.locationStatuses.size)
        assertEquals(LocationStatus(playerMike, shop, Building.Undeveloped), GameLedger.locationStatuses[0])
    }

    @Test
    fun `when no transactions have been added to GameLedger, there is no owner of a location`() {
        assertNull(GameLedger.ownerOf(shop))
    }
    @Test
    fun `when no transactions involving locations have been added to GameLedger, there is no owner of a location`() {
        GameLedger.addFeeForPlayerPassingGo(playerMike,Go.fee)
        GameLedger.payRent(playerJake, playerMike, GBP(20))
        GameLedger.addNewPlayer(playerMike, GBP(200))
        assertNull(GameLedger.ownerOf(shop))
    }
    @Test
    fun `when a transaction involving a different location has been added to GameLedger, there is no owner of a location`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        assertNull(GameLedger.ownerOf(warehouse))
    }
    @Test
    fun `when a single transaction involving a Retail location has been added to GameLedger, the correct owner and basic rent is returned `() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        assertEquals(Pair(playerMike, shop.rent), GameLedger.ownerOf(shop)  )
    }
    @Test
    fun `when a single transaction involving a warehouse has been added to GameLedger, the correct owner and basic rent is returned `() {
        GameLedger.purchaseLocation(playerMike, warehouse, warehouse.purchasePrice)
        assertEquals(Pair(playerMike, warehouse.rent), GameLedger.ownerOf(warehouse)  )
    }
    @Test
    fun `when a retail location is purchased and then has a ministore built on it , the rent for the ministore is returned `() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, shop.miniStore.buildingCost)

        assertEquals(Pair(playerMike,shop.miniStore.rent), GameLedger.ownerOf(shop) )
    }
    @Test
    fun `when a retail location is purchased and then mortgaged , the rent is zero`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, shop.miniStore.buildingCost)
        GameLedger.mortgageLocation(playerMike, shop, GBP(50))

        assertEquals(Pair( playerMike, GBP(0)), GameLedger.ownerOf(shop) )
    }
    @Test
    fun `when a retail location is purchased and then mortgaged and then unmortgaged , the rent is the standard ret`() {
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.buildOnLocation(playerMike, shop, Building.Minimarket, shop.miniStore.buildingCost)
        GameLedger.mortgageLocation(playerMike, shop, GBP(50))
        GameLedger.unMortgageLocation(playerMike, shop, GBP(60))

        assertEquals(Pair( playerMike, shop.rent), GameLedger.ownerOf(shop) )
    }

    @Test
    fun `when a mortgage free location is sold to another player, ownership moves to the other player and rent is standard rent`(){
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.sellLocation(playerMike, playerJake, shop, GBP(500))

        assertEquals(Pair(playerJake, shop.rent), GameLedger.ownerOf(shop))
        assertEquals(0, GameLedger.locationsFor(playerMike).size)
    }
    @Test
    fun `when a mortgaged location is sold to another player, ownership moves to the other player and rent is zero`(){
        GameLedger.purchaseLocation(playerMike, shop, shop.purchasePrice)
        GameLedger.mortgageLocation(playerMike, shop,GBP(100))
        GameLedger.sellMortgagedLocation(playerMike, playerJake, shop, GBP(500))

        assertEquals(Pair(playerJake, GBP(0)), GameLedger.ownerOf(shop))
        assertEquals(0, GameLedger.locationsFor(playerMike).size)
    }

    @Test
    fun `when an locationStatus is purchasable location but not buildable it should return standard rent for the location`() {
        val locationStatus = LocationStatus(playerMike, warehouse, Building.Undeveloped)
        assertEquals(warehouse.rent, locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is buildable location but not developede it should return standard rent for the location`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Undeveloped)
        assertEquals(shop.rent, locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is buildable location and has a minimarket it should return minimarket rent for the location`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Minimarket)
        assertEquals(shop.miniStore.rent, locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is buildable location and has a supermarket it should return supermarket rent for the location`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Supermarket)
        assertEquals(shop.supermarket.rent, locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is buildable location and has a megastore it should return megastore rent for the location`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Megastore)
        assertEquals(shop.megastore.rent, locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is mortgaged should return zero rent`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Megastore, true)
        assertEquals(GBP(0), locationStatus.rentPayable)
    }
    @Test
    fun `when an locationStatus is unmortgaged should return standard rent`() {
        val locationStatus = LocationStatus(playerMike, shop, Building.Undeveloped, false)
        assertEquals(shop.rent, locationStatus.rentPayable)
    }
}