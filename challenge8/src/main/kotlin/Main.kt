fun main(args: Array<String>) {
    print("hello world")
}

sealed class Location() {
    object FreeParking
    class Go (){val fee = GBP(100)}
    class FactoryOrWarehouse (val name:String) {
        val purchasePrice = GBP(100)
        val rent = GBP(20)}
    class RetailSite (val name:String, val purchasePrice:Int, val undeveloped:Development.Undeveloped,
                      val miniStore:Development.MiniStore, val supermarket: Development.Supermarket,
                      val megastore: Development.Megastore) {
    }
}
sealed class Development{
    class Undeveloped(val rent:GBP)
    class MiniStore(val buildingCost:GBP, val rent:GBP)
    class Supermarket(val buildingCost:GBP, val rent:GBP)
    class Megastore(val buildingCost:GBP, val rent:GBP)
}

class GBP(_value:Int) {
    val value:Int
    init {
        if (_value > 0 ) value = _value else value = - _value
    }
    
    override fun toString(): String {
        return "£$value"
    }
}