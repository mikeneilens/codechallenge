data class DeliveryToADepot(val product:String,val item:String, private val caseSize:Int, val depot:String, val supplier:String, private val qtyDelivered:Int):Delivery {
    override val unitsDelivered = qtyDelivered * caseSize
    override val key:String = "$depot $item"
}

val createDeliveryToADepotOrNull = fun (list: List<String>): DeliveryToADepot? {
    if (list.size != 6)  return null

    val caseSize = list[2].toIntOrNull()
    val qtyDelivered = list[5].toIntOrNull()
    return if ((caseSize == null) || (qtyDelivered == null))
        null
    else
        DeliveryToADepot(list[0],list[1],caseSize,list[3],list[4],qtyDelivered)
}