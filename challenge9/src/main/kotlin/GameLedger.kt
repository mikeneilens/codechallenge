object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()


    interface Transaction {
        val playerCredited:Player
        val amount:GBP
    }
 //   open class Transaction( val playerCredited: Player, val amount:GBP)

    interface PlayerCreditedByBank:Transaction {
        val playerDebted:Player
    }

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(object:Transaction{override val playerCredited = player; override val amount = startingBalance})
    }

    fun addFeeForPlayerPassingGo(player: Player, fee:GBP) {
        transactions.add(object:Transaction{override val playerCredited = player; override val amount = fee})
    }

    fun reset() {
        transactions.clear()
    }

    fun payRent(playerCredited: Player, playerDebted: Player, rent: GBP) {
        transactions.add(object:PlayerCreditedByBank{
            override val playerCredited = playerCredited
            override val playerDebted = playerDebted
            override val amount = rent
        })
    }
}