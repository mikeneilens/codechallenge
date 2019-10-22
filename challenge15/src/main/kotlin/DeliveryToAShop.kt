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