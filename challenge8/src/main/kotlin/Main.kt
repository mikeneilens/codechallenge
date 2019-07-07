
sealed class Location() {
    object FreeParking
    class Go (){val fee = GBP(100)}
    class FactoryOrWarehouse (val name:String) {
        val purchasePrice = GBP(100)
        val rent = GBP(20)
    }
    class RetailSite (val name:String, val purchasePrice:GBP,
                      val undeveloped:Development.Undeveloped,
                      val miniStore:Development.MiniStore,
                      val supermarket: Development.Supermarket,
                      val megastore: Development.Megastore,
                      val group:Group)
}

sealed class Development{
    class Undeveloped(val rent:GBP)
    class MiniStore(val buildingCost:GBP, val rent:GBP)
    class Supermarket(val buildingCost:GBP, val rent:GBP)
    class Megastore(val buildingCost:GBP, val rent:GBP)
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