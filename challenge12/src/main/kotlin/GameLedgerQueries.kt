fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    return Credit(totalCredits) + Debt(totalDebts)
}

fun GameLedger.locationsFor(player: Player): List<OwnedLocation> {
    return ownedLocations.filter{it.owner == player}
}

fun GameLedger.ownerOf(location: Location):Pair<Player, GBP>? {
    val ownedLocation = this.ownedLocations.firstOrNull { it.location == location }

    if (ownedLocation == null) {
        return null
    } else {
        val rent = ownedLocation.calcRent()
        return Pair(ownedLocation.owner, rent)
    }
}

fun OwnedLocation.calcRent():GBP = when (this.location) {
    is Buildable -> {
        when (building) {
            Building.Undeveloped -> this.location.rent
            Building.Minimarket -> this.location.miniStore.rent
            Building.Supermarket -> this.location.supermarket.rent
            Building.Megastore -> this.location.megastore.rent
        }
    }
    is Purchaseable -> {
        this.location.rent
    }
    else -> {
        GBP(0)
    }
}

