object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()
    class Transaction(val player:Player)

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(Transaction(player))
    }

}