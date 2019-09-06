object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()

    interface Transaction {
        val amount:GBP
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
        val building:Building
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

    val ownedLocations:List<OwnedLocation> get() {
        val listOfOwnedLocations = this.transactions.mapNotNull{
            when (it) {
                is GameLedger.PlayerMortgagingLocation -> OwnedLocation(it.playerCredited, it.location, Building.Undeveloped, true)
                is GameLedger.PlayerUnMortgagingLocation -> OwnedLocation(it.playerDebited, it.location, Building.Undeveloped, false)
                is GameLedger.PlayerSellingLocation -> OwnedLocation(it.playerDebited, it.location, Building.Undeveloped)
                is GameLedger.PlayerPurchasingProperty -> OwnedLocation(it.playerDebited, it.location, Building.Undeveloped)
                is GameLedger.PlayerBuildingOnLocation -> OwnedLocation(it.playerDebited, it.location, it.building)
                is GameLedger.PlayerSellingBuilding -> {
                    when (it.building) {
                        Building.Megastore -> OwnedLocation(it.playerCredited, it.location, Building.Supermarket)
                        Building.Supermarket -> OwnedLocation(it.playerCredited, it.location, Building.Minimarket)
                        Building.Minimarket -> OwnedLocation(it.playerCredited, it.location, Building.Undeveloped)
                        else -> OwnedLocation(it.playerCredited, it.location, Building.Undeveloped)
                    }
                }
                else -> null
            }
        }
        return listOfOwnedLocations.reversed().distinctBy { it.location }.reversed()
    }

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(object:CreditTransaction{override val playerCredited = player; override val amount = startingBalance})
    }

    fun addFeeForPlayerPassingGo(player: Player, fee:GBP) {
        transactions.add(object:CreditTransaction{override val playerCredited = player; override val amount = fee})
    }

    fun payRent(playerCredited: Player, playerDebted: Player, rent: GBP) {
        transactions.add(object:PlayerPayingAnotherPlayer{
            override val playerCredited = playerCredited
            override val playerDebited = playerDebted
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
        transactions.add(object:PlayerSellingBuilding{
            override val playerCredited = player
            override val location = location
            override val building = buildingType
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

    fun unmortgageLocation(player: Player, location: Purchaseable, redeemCost: GBP) {
        transactions.add(object:PlayerUnMortgagingLocation{
            override val playerDebited = player
            override val location = location
            override val amount = redeemCost
        })
    }

    fun sellLocation(playerSelling: Player, playerBuying:Player, location:Purchaseable, sellingPrice: GBP) {
        val locationsForSeller = locationsFor(playerSelling)
        val locationBeingSoldStatus = locationsForSeller.filter{it.location == location}.lastOrNull()

        transactions.add(object:PlayerSellingLocation {
            override val playerCredited = playerSelling
            override val playerDebited = playerBuying
            override val location = location
            override val amount = sellingPrice
        })

        locationBeingSoldStatus?.let{locationBeingSold ->
            if (locationBeingSold.mortgaged) {
                mortgageLocation(playerBuying, location, GBP(0))
            }
        }

    }
}
