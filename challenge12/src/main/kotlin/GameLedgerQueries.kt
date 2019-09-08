fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    return Credit(totalCredits) + Debt(totalDebts)
}

fun GameLedger.locationsFor(player: Player): List<LocationStatus> {
    return locationStatuses.filter{it.owner == player}
}

fun GameLedger.ownerOf(location: Location):Pair<Player, GBP>? {
    val ownedLocation = this.locationStatuses.firstOrNull { it.location == location }

    if (ownedLocation == null) {
        return null
    } else {
        val rent = ownedLocation.rentPayable
        return Pair(ownedLocation.owner, rent)
    }
}



