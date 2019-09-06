fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = GameLedger.transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = GameLedger.transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    val balance = Credit(totalCredits) + Debt(totalDebts)

    return balance
}

fun GameLedger.locationsFor(player: Player): List<OwnedLocation> {
    return GameLedger.ownedLocations.filter{it.owner == player}
}

fun GameLedger.ownerOf(location: Location):Pair<Player, GBP>? {
    val foundLocation = GameLedger.ownedLocations.filter{it.location == location}.firstOrNull()

    if (foundLocation == null) {
        return null
    } else {
        return Pair(foundLocation.owner, GBP(10))
    }
}
