interface Location

interface Purchaseable:Location {
    val purchasePrice:GBP
    val rent:GBP
}
interface Buildable:Purchaseable {
    val miniStore:BuildCostAndRent
    val supermarket:BuildCostAndRent
    val megastore:BuildCostAndRent
}

object FreeParking:Location

object Go:Location {val fee = GBP(100)}

class FactoryOrWarehouse (val name:String):Purchaseable {
    override val purchasePrice = GBP(100)
    override val rent = GBP(20)
}

class RetailSite (val group:Group, val name:String, override val purchasePrice:GBP,
                  override val rent:GBP,
                  override val miniStore:BuildCostAndRent,
                  override val supermarket: BuildCostAndRent,
                  override val megastore: BuildCostAndRent
):Buildable

class BuildCostAndRent(val buildingCost:GBP, val rent:GBP)

enum class Group {
    Red, Green, Blue, Brown, Orange, Purple
}
