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

data class DeliveryToAShop(val product: String, val EAN: String, val item: String, val caseSize: Int, val depot: String, val qtyDelivered:Int)

fun createDeliveryToAShopOrNull(list: List<String>): DeliveryToAShop? {
    if (list.size != 6)  return null

    val caseSize = list[3].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToAShop(list[0],list[1],list[2], caseSize, list[4], qtyDelivered)
}

fun createListOfDeliveryToShop(csvOfDeliveryToShop: String): List<DeliveryToAShop> =
    csvOfDeliveryToShop.split(",")
        .windowed(6,6)
        .mapNotNull{createDeliveryToAShopOrNull(it)}

fun <ObjectType:Any>createListOfObject(csvData: String, parameters:Int, objectCreator:(List<String>)->ObjectType?): List<ObjectType> =
    csvData.split(",")
        .windowed(parameters,parameters)
        .mapNotNull{objectCreator(it)}
