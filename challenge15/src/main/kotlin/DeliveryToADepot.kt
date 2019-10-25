data class DeliveryToADepot(val product:Product,val item:Item, private val caseSize:Int, val depot:Depot, val supplier:Supplier, private val qtyDelivered:Int) {
    val unitsDelivered = qtyDelivered * caseSize
}

val createDeliveryToADepotOrNull = fun (list: List<String>): DeliveryToADepot? {
    if (list.size != 6)  return null

    val caseSize = list[2].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToADepot(Product(list[0]),Item(list[1]),caseSize,Depot(list[3]),Supplier(list[4]),qtyDelivered)
}