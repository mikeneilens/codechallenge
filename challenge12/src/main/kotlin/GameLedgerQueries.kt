fun GameLedger.balanceFor(player: Player): Balance {
    val creditTransactions = GameLedger.transactions.filter { it is GameLedger.CreditTransaction && it.playerCredited == player }
    val totalCredits = creditTransactions.fold(GBP(0)){total, transaction -> total + transaction.amount}

    val debtTransactions = GameLedger.transactions.filter { it is GameLedger.DebitTransaction && it.playerDebited == player }
    val totalDebts = debtTransactions.fold(GBP(0)){ total, transaction -> total + transaction.amount}

    val balance = Credit(totalCredits) + Debt(totalDebts)

    return balance
}

data class OwnedLocation(val location: Location, val building: Building)

fun GameLedger.locationsFor(player: Player): List<OwnedLocation> {
    val locationTransactions = GameLedger.transactions.filter { (it is GameLedger.PlayerPurchasingProperty || it is GameLedger.PlayerBuildingOnLocation) && it is GameLedger.DebitTransaction  && it.playerDebited == player}

    val listOfOwnedLocations = locationTransactions.mapNotNull{
        when (it) {
            is GameLedger.PlayerPurchasingProperty -> OwnedLocation(it.location, Building.undeveloped)
            is GameLedger.PlayerBuildingOnLocation -> OwnedLocation(it.location, it.building)
            else -> null
        }
    }

    return listOfOwnedLocations
}