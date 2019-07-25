object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()


    interface Transaction {
        val amount:GBP
    }

    interface PlayerCredited:Transaction{
        val playerCredited:Player
    }
    interface PlayerDebited:Transaction{
        val playerDebited:Player
    }
    interface PlayerPayingAnotherPlayer:PlayerCredited, PlayerDebited {
        override  val playerCredited:Player
        override val playerDebited:Player
    }
    interface PlayerPurchasesProerty:PlayerDebited{
        override val playerDebited:Player
        val location:Purchaseable
    }
    interface PlayerBuildsOnLocation:PlayerDebited {
        override val playerDebited:Player
        val location:Buildable
        val building:Building
    }

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(object:PlayerCredited{override val playerCredited = player; override val amount = startingBalance})
    }

    fun addFeeForPlayerPassingGo(player: Player, fee:GBP) {
        transactions.add(object:PlayerCredited{override val playerCredited = player; override val amount = fee})
    }

    fun reset() {
        transactions.clear()
    }

    fun payRent(playerCredited: Player, playerDebted: Player, rent: GBP) {
        transactions.add(object:PlayerPayingAnotherPlayer{
            override val playerCredited = playerCredited
            override val playerDebited = playerDebted
            override val amount = rent
        })
    }

    fun purchaseLocation(player: Player, location: Purchaseable, purchasePrice: GBP) {
        transactions.add(object:PlayerPurchasesProerty{
            override val playerDebited = player
            override val location = location
            override val amount = purchasePrice
        })
    }

    fun buildOnLocation(player: Player, location: Buildable, buildingType: Building, developmentCost: GBP) {
        transactions.add(object:PlayerBuildsOnLocation {
            override val playerDebited = player
            override val location = location
            override val building = buildingType
            override val amount = developmentCost
        })
    }
}

enum class Building {
    minimarket, supermarket, megastore
}