
data class OwnedLocation(val owner:Player, val location: Purchaseable, val building: Building, val isMortgaged:Boolean = false) {

    val rentPayable:GBP get() {
        if (isMortgaged) return GBP(0)

        return when (location) {
            is Buildable -> {
                when (building) {
                    Building.Undeveloped -> location.rent
                    Building.Minimarket -> location.miniStore.rent
                    Building.Supermarket -> location.supermarket.rent
                    Building.Megastore -> location.megastore.rent
                }
            }
            else -> {
                location.rent
            }
        }
    }
}
