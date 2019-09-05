fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = GameLedger.transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = GameLedger.transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    val balance = Credit(totalCredits) + Debt(totalDebts)

    return balance
}

data class OwnedLocation(val location: Location, val building: Building)

fun GameLedger.locationsFor(plaher: Player): List<OwnedLocation> {
    return listOf()
}