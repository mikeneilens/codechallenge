data class DiscountForAnEAN(val product:String, val EAN:String, val discountValue:Double)

fun createDiscountForEANorNull(list:List<String>):DiscountForAnEAN? {
    if ((list.size != 3) || (!list[2].startsWith("£"))) return null

    val discountValueOrNull = list[2].removePrefix("£").toDoubleOrNull()
    return if (discountValueOrNull == null)
                null
            else
                DiscountForAnEAN(list[0], list[1], discountValueOrNull)

}

fun createListOfDiscountsForAnEAN(csvOfDiscountsForEachEAN: String): List<DiscountForAnEAN> =
     csvOfDiscountsForEachEAN.split(",")
        .windowed(3,3)
        .mapNotNull{createDiscountForEANorNull(it)}
