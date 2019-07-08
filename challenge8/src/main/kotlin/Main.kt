
sealed class Location() {
    object FreeParking
    class Go (){val fee = GBP(100)}
    class FactoryOrWarehouse (val name:String) {
        val purchasePrice = GBP(100)
        val rent = GBP(20)
    }
    class RetailSite (val group:Group, val name:String, val purchasePrice:GBP,
                      val undeveloped:DevelopmentType.RentOnly,
                      val miniStore:DevelopmentType.BuildCostAndRent,
                      val supermarket: DevelopmentType.BuildCostAndRent,
                      val megastore: DevelopmentType.BuildCostAndRent
                      )
}

sealed class DevelopmentType{
    class RentOnly(val rent:GBP)
    class BuildCostAndRent(val buildingCost:GBP, val rent:GBP)
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