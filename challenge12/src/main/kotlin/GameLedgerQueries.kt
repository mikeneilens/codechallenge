fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = GameLedger.transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = GameLedger.transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    val balance = Credit(totalCredits) + Debt(totalDebts)

    return balance
}

data class OwnedLocation(val owner:Player, val location: Location, val building: Building)

fun GameLedger.ownedLocations():List<OwnedLocation> {
    val listOfOwnedLocations = this.transactions.mapNotNull{
        when (it) {
            is GameLedger.PlayerPurchasingProperty -> OwnedLocation(it.playerDebited, it.location, Building.undeveloped)
            is GameLedger.PlayerBuildingOnLocation -> OwnedLocation(it.playerDebited, it.location, it.building)
            else -> null
        }
    }
    return listOfOwnedLocations.reversed().distinctBy { it.location }
}

fun GameLedger.locationsFor(player: Player): List<OwnedLocation> {
    return GameLedger.ownedLocations().filter{it.owner == player}
}
