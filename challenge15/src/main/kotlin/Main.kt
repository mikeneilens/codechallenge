data class DiscountForAnEAN(val product:String, val EAN:String, val discountValue:Double)

val createDiscountForEANorNull =  fun (list:List<String>):DiscountForAnEAN? {
    if ((list.size != 3) || (!list[2].startsWith("£"))) return null

    val discountValueOrNull = list[2].removePrefix("£").toDoubleOrNull()
    return if (discountValueOrNull == null)
                null
            else
                DiscountForAnEAN(list[0], list[1], discountValueOrNull)

}

data class DeliveryToAShop(val product: String, val EAN: String, val item: String, val caseSize: Int, val depot: String, val qtyDelivered:Int)

val createDeliveryToAShopOrNull = fun (list: List<String>): DeliveryToAShop? {
    if (list.size != 6)  return null

    val caseSize = list[3].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToAShop(list[0],list[1],list[2], caseSize, list[4], qtyDelivered)
}

data class DeliveryToADepot(val product:String,val item:String,val caseSize:Int, val depot:String, val supplier:String, val qtyDelivered:Int)

val createDeliveryToADepotOrNull = fun (list: List<String>): DeliveryToADepot? {
    if (list.size != 6)  return null

    val caseSize = list[2].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToADepot(list[0],list[1],caseSize,list[3],list[4],qtyDelivered)
}


fun <ObjectType:Any>String.createListOfObject(noOfProperties:Int, objectCreator:(List<String>)->ObjectType?): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties,noOfProperties)
        .mapNotNull{objectCreator(it)}
