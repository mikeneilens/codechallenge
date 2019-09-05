fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactionsForMike = GameLedger.transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactionsForMike.fold(GBP(0)){total, transaction -> total + transaction.amount}

    return Credit(totalCredits)
}