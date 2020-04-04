import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestGameLedgerCommandFunctions {

    @BeforeEach
    fun `delete all transactions`() {
        GameLedger.transactions.clear()
    }

    @Test
    fun `Transaction is added to GameLedger for new player`() {
        val newPlayer = Player( "A playerCredited")
        GameLedger.addNewPlayer(newPlayer, GBP(500))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.CreditTransaction
        assertEquals(lastTransaction.playerCredited, newPlayer)
        assertEquals(lastTransaction.amount, GBP(500))
    }

    @Test
    fun `Transaction is added to GameLedger when player passes go` () {
        val originalNoOfTransactions = GameLedger.transactions.size

        val player = Player("A playerCredited")
        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))

        assertEquals(GameLedger.transactions.size, originalNoOfTransactions + 1)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.CreditTransaction
        assertEquals(lastTransaction.playerCredited, player)
        assertEquals(lastTransaction.amount, GBP(100))

        GameLedger.addFeeForPlayerPassingGo(player, GBP(100))
        assertEquals(GameLedger.transactions.size, originalNoOfTransactions + 2)

        val secondTransaction = GameLedger.transactions.last() as GameLedger.CreditTransaction
        assertEquals(secondTransaction.playerCredited, player)
        assertEquals(secondTransaction.amount, GBP(100))
    }

    @Test
    fun `Transaction is added to GameLedger when one player pays rent to another`() {
        val player1 = Player("Player 1")
        val player2 = Player("Player 2")
        val rent = GBP(20)

        GameLedger.payRent(player1, player2, rent)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerPayingAnotherPlayer
        assertEquals(lastTransaction.playerCredited, player1)
        assertEquals(lastTransaction.playerDebited, player2)
        assertEquals(lastTransaction.amount, GBP(20))

    }

    @Test
    fun `Transaction is added when a player purchases a property from the bank`() {
        val player = Player("A playerCredited")
        val location = object:Purchaseable{
            override val purchasePrice: GBP = GBP(20); override val rent:GBP = GBP(20)
        }
        val purchasePrice = GBP(200)

        GameLedger.purchaseLocation(player, location, purchasePrice)

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerPurchasingProperty
        assertEquals(lastTransaction.playerDebited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, purchasePrice)
    }

    @Test
    fun `Transaction is added when a player builds on a location`() {
        val player = Player("A playerCredited")

        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val developmentCost = GBP(300)
        val buildingType = Building.Minimarket

        GameLedger.buildOnLocation(player, location, buildingType, developmentCost )

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerBuildingOnLocation
        assertEquals(lastTransaction.playerDebited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.building, buildingType)
        assertEquals(lastTransaction.amount, developmentCost)

    }
    @Test
    fun `Transaction is added when a player sells property to the bank`() {
        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val player = Player("The playerCredited")

        GameLedger.sellBuilding(player, location, Building.Megastore, GBP(200))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerSellingBuilding
        assertEquals(lastTransaction.playerCredited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.buildingSold, Building.Megastore)
        assertEquals(lastTransaction.amount, GBP(200))
    }

    @Test
    fun `Transaction is added when a player mortgages a property`(){
        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val player = Player("The playerMortgaging")

        GameLedger.mortgageLocation(player, location, GBP(50))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerMortgagingLocation
        assertEquals(lastTransaction.playerCredited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, GBP(50))
    }

    @Test
    fun `Transaction is added when a player unmortgages a property`(){
        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val player = Player("The playerUnMortgaging")

        GameLedger.unMortgageLocation(player, location, GBP(50))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerUnMortgagingLocation
        assertEquals(lastTransaction.playerDebited, player)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, GBP(50))
    }

    @Test
    fun `Transaction is added when a player sells a property to another player`() {
        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val playerSelling = Player("The playerSelling")
        val playerBuying = Player("The playerBuying")

        GameLedger.sellLocation(playerSelling, playerBuying, location, GBP(1000))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerSellingLocation
        assertEquals(lastTransaction.playerDebited, playerBuying)
        assertEquals(lastTransaction.playerCredited, playerSelling)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, GBP(1000))
    }
    @Test
    fun `A mortgaged sale transaction is added when a player sells a mortgaged property to another player`() {
        val location = object:Buildable {
            override val purchasePrice = GBP(200)
            override val rent = GBP(10)
            override val miniStore = BuildCostAndRent(GBP(100), GBP(10))
            override val supermarket = BuildCostAndRent(GBP(200), GBP(20))
            override val megastore = BuildCostAndRent(GBP(300), GBP(30))
        }

        val playerSelling = Player("The playerSelling")
        val playerBuying = Player("The playerBuying")

        GameLedger.purchaseLocation(playerSelling, location, location.purchasePrice)
        GameLedger.mortgageLocation(playerSelling, location, GBP(100))
        GameLedger.sellMortgagedLocation(playerSelling, playerBuying, location, GBP(1000))

        val lastTransaction = GameLedger.transactions.last() as GameLedger.PlayerSellingMortgagedLocation

        assertEquals(lastTransaction.playerDebited, playerBuying)
        assertEquals(lastTransaction.playerCredited, playerSelling)
        assertEquals(lastTransaction.location, location)
        assertEquals(lastTransaction.amount, GBP(1000))

    }
}