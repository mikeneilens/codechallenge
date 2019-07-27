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
}
