data class DiscountsForAnEAN(val product:Product, val EAN:EAN, val discountValue:Double)

val createDiscountForEANorNull =  fun (list:List<String>):DiscountsForAnEAN? {
    if ((list.size != 3) || (!list[2].startsWith("£"))) return null

    val discountValueOrNull = list[2].removePrefix("£").toDoubleOrNull()
    return if (discountValueOrNull == null)
        null
    else
        DiscountsForAnEAN(Product(list[0]), EAN(list[1]), discountValueOrNull)
}
