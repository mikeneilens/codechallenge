data class DiscountForAnEAN(val product:String, val EAN:String, val discountValue:Double)

fun createDiscountForEANorNull(list:List<String>):DiscountForAnEAN? {
    when (true) {
        list.size != 3 -> return null
        list[2].removePrefix("£").toDoubleOrNull() == null -> return null
        else -> return DiscountForAnEAN(list[0], list[1],list[2].removePrefix("£").toDouble())
    }
}