
data class DeliveryToAShop(val product: Product, val EAN: EAN, val item: Item, private val caseSize: Int, val depot: Depot, private val qtyDelivered:Int) {
    val unitsDelivered = qtyDelivered * caseSize
}

val createDeliveryToAShopOrNull = fun (list: List<String>): DeliveryToAShop? {
    if (list.size != 6)  return null

    val caseSize = list[3].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToAShop(Product(list[0]),EAN(list[1]),Item(list[2]), caseSize, Depot(list[4]), qtyDelivered)
}