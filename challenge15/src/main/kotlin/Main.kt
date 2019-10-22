data class DiscountForAnEAN(val product:String, val EAN:String, val discountValue:Double)

fun createDiscountForEANorNull(list:List<String>):DiscountForAnEAN? {
    if (list.size != 3)  return null
    if (!list[2].startsWith("£")) return null

    val discountValueOrNull = list[2].removePrefix("£").toDoubleOrNull()
    if (discountValueOrNull == null) return null
    else return DiscountForAnEAN(list[0], list[1], discountValueOrNull)

}

fun createListOfDiscountsForAnEAN(csvOfDiscountsForEachEAN: String): List<DiscountForAnEAN> {
    if (csvOfDiscountsForEachEAN.isEmpty()) return emptyList()

    val discountForAnEAN = createDiscountForEANorNull(csvOfDiscountsForEachEAN.split(",") )
    if (discountForAnEAN != null) return listOf(discountForAnEAN)
    else return emptyList()
}