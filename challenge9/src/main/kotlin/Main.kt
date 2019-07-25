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

class Main {
}