data class DiscountsForAnEAN(val product:String, val EAN:String, val discountValue:Double)

val createDiscountForEANorNull =  fun (list:List<String>):DiscountsForAnEAN? {
    if ((list.size != 3) || (!list[2].startsWith("£"))) return null

    val discountValueOrNull = list[2].removePrefix("£").toDoubleOrNull()
    return if (discountValueOrNull == null)
        null
    else
        DiscountsForAnEAN(list[0], list[1], discountValueOrNull)

}
