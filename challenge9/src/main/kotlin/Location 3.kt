interface Location

interface Purchaseable:Location {
    val purchasePrice:GBP
}
interface Buildable:Purchaseable {
    val miniStore:DevelopmentType.BuildCostAndRent
    val supermarket:DevelopmentType.BuildCostAndRent
    val megastore:DevelopmentType.BuildCostAndRent
}

sealed class DevelopmentType{
    class RentOnly(val rent:GBP)
    class BuildCostAndRent(val buildingCost:GBP, val rent:GBP)
}
