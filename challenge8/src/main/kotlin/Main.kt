
sealed class Location() {
    object FreeParking
    class Go (){val fee = GBP(100)}
    class FactoryOrWarehouse (val name:String) {
        val purchasePrice = GBP(100)
        val rent = GBP(20)
    }
    class RetailSite (val name:String, val purchasePrice:GBP,
                      val undeveloped:Development.UndevelopedRent,
                      val miniStore:Development.MiniStoreCostAndRent,
                      val supermarket: Development.SupermarketCostAndRent,
                      val megastore: Development.MegastoreCostAndRent,
                      val group:Group)
}

sealed class Development{
    class UndevelopedRent(val rent:GBP)
    class MiniStoreCostAndRent(val buildingCost:GBP, val rent:GBP)
    class SupermarketCostAndRent(val buildingCost:GBP, val rent:GBP)
    class MegastoreCostAndRent(val buildingCost:GBP, val rent:GBP)
}

enum class Group {
    Red, Green, Blue, Brown, Orange, Purple
}

class GBP(_value:Int) {
    val value:Int

    init {
        if (_value > 0 ) value = _value else value = - _value
    }

    override fun equals(other: Any?): Boolean {
        return other is GBP && other.value == value
    }

    override fun toString(): String {
        return "£$value"
    }
}