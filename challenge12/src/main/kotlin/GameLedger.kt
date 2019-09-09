object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()

    interface Transaction {
        val amount:GBP

        fun toLocationStatus() = when (this) {
            is PlayerMortgagingLocation -> LocationStatus(playerCredited, location, Building.Undeveloped, true)
            is PlayerUnMortgagingLocation -> LocationStatus(playerDebited, location, Building.Undeveloped, false)
            is PlayerSellingLocation -> LocationStatus(playerDebited, location, Building.Undeveloped)
            is PlayerSellingMortgagedLocation -> LocationStatus(playerDebited, location, Building.Undeveloped, true)
            is PlayerPurchasingProperty -> LocationStatus(playerDebited, location, Building.Undeveloped)
            is PlayerBuildingOnLocation -> LocationStatus(playerDebited, location, building)
            is PlayerSellingBuilding -> LocationStatus(playerCredited, location, buildingRemaining)
            else -> null
        }
    }

    interface CreditTransaction:Transaction{
        val playerCredited:Player
    }
    interface DebitTransaction:Transaction{
        val playerDebited:Player
    }
    interface PlayerPayingAnotherPlayer:CreditTransaction, DebitTransaction

    interface PlayerPurchasingProperty:DebitTransaction{
        val location:Purchaseable
    }
    interface PlayerBuildingOnLocation:DebitTransaction {
        val location:Buildable
        val building:Building
    }

    interface PlayerSellingBuilding:CreditTransaction {
        val location:Buildable
        val buildingSold:Building
        val buildingRemaining:Building
    }

    interface PlayerMortgagingLocation:CreditTransaction {
        val location:Purchaseable
    }

    interface PlayerUnMortgagingLocation:DebitTransaction {
        val location:Purchaseable
    }

    interface PlayerSellingLocation:CreditTransaction, DebitTransaction {
        val location:Purchaseable
    }

    interface PlayerSellingMortgagedLocation:CreditTransaction, DebitTransaction {
        val location:Purchaseable
    }
    val locationStatuses:List<LocationStatus> get() {
        val listOfOwnedLocations = this.transactions.mapNotNull{it.toLocationStatus()}
        return listOfOwnedLocations.reversed().distinctBy { it.location }.reversed()
    }

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(object:CreditTransaction{override val playerCredited = player; override val amount = startingBalance})
    }

    fun addFeeForPlayerPassingGo(player: Player, fee:GBP) {
        transactions.add(object:CreditTransaction{override val playerCredited = player; override val amount = fee})
    }

    fun payRent(playerCredited: Player, playerDebited: Player, rent: GBP) {
        transactions.add(object:PlayerPayingAnotherPlayer{
            override val playerCredited = playerCredited
            override val playerDebited = playerDebited
            override val amount = rent
        })
    }

    fun purchaseLocation(player: Player, location: Purchaseable, purchasePrice: GBP) {
        transactions.add(object:PlayerPurchasingProperty{
            override val playerDebited = player
            override val location = location
            override val amount = purchasePrice
        })
    }

    fun buildOnLocation(player: Player, location: Buildable, buildingType: Building, developmentCost: GBP) {
        transactions.add(object:PlayerBuildingOnLocation {
            override val playerDebited = player
            override val location = location
            override val building = buildingType
            override val amount = developmentCost
        })
    }

    fun sellBuilding(player: Player, location: Buildable, buildingType: Building, sellingPrice: GBP) {
        val buildingRemaining = when (buildingType) {
            Building.Megastore -> Building.Supermarket
            Building.Supermarket -> Building.Minimarket
            Building.Minimarket -> Building.Undeveloped
            else -> Building.Undeveloped
        }

        transactions.add(object:PlayerSellingBuilding{
            override val playerCredited = player
            override val location = location
            override val buildingSold = buildingType
            override val buildingRemaining = buildingRemaining
            override val amount = sellingPrice
        })
    }

    fun mortgageLocation(player: Player, location: Purchaseable, mortgageValue: GBP) {
        transactions.add(object:PlayerMortgagingLocation{
            override val playerCredited = player
            override val location = location
            override val amount = mortgageValue
        })
    }

    fun unMortgageLocation(player: Player, location: Purchaseable, redeemCost: GBP) {
        transactions.add(object:PlayerUnMortgagingLocation{
            override val playerDebited = player
            override val location = location
            override val amount = redeemCost
        })
    }

    fun sellLocation(playerSelling: Player, playerBuying:Player, location:Purchaseable, sellingPrice: GBP) {
        transactions.add(object:PlayerSellingLocation {
            override val playerCredited = playerSelling
            override val playerDebited = playerBuying
            override val location = location
            override val amount = sellingPrice
        })
    }

    fun sellMortgagedLocation(playerSelling: Player, playerBuying:Player, location:Purchaseable, sellingPrice: GBP) {
        transactions.add(object:PlayerSellingMortgagedLocation {
            override val playerCredited = playerSelling
            override val playerDebited = playerBuying
            override val location = location
            override val amount = sellingPrice
        })
    }
}
