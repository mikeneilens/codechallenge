object GameLedger {

    val transactions:MutableList<Transaction> = mutableListOf()

    class Transaction(val player:Player, val amount:GBP)

    fun addNewPlayer(player:Player, startingBalance:GBP ) {
        transactions.add(Transaction(player, startingBalance))
    }

    fun addFeeForPlayerPassingGo(player: Player, fee:GBP) {
        transactions.add(Transaction(player, fee))
    }

    fun reset() {
        transactions.clear()
    }
}