interface Location

interface Purchaseable:Location {
    val purchasePrice:GBP
}
interface Buildable:Purchaseable {
    val miniStore:DevelopmentType.BuildCostAndRent
    val supermarket:DevelopmentType.BuildCostAndRent
    val megastore:DevelopmentType.BuildCostAndRent
}

object FreeParking:Location

object Go:Location {val fee = GBP(100)}

class FactoryOrWarehouse (val name:String):Purchaseable {
    override val purchasePrice = GBP(100)
    val rent = GBP(20)
}

class RetailSite (val group:Group, val name:String, override val purchasePrice:GBP,
                  val undeveloped:DevelopmentType.RentOnly,
                  override val miniStore:DevelopmentType.BuildCostAndRent,
                  override val supermarket: DevelopmentType.BuildCostAndRent,
                  override val megastore: DevelopmentType.BuildCostAndRent
):Buildable

sealed class DevelopmentType{
    class RentOnly(val rent:GBP)
    class BuildCostAndRent(val buildingCost:GBP, val rent:GBP)
}

enum class Group {
    Red, Green, Blue, Brown, Orange, Purple
}
