data class DeliveryToADepot(val product:Product,val item:Item, private val caseSize:Int, val depot:Depot, val supplier:Supplier, private val qtyDelivered:Int) {
    val unitsDelivered = qtyDelivered * caseSize

    companion object:CreatesObjectFromAListOfStrings<DeliveryToADepot> {
        override val numberOfProperties: Int = 6
        override fun createObjectOrNull (properties: List<String>): DeliveryToADepot? {
            if (properties.size != numberOfProperties)  return null
            val caseSize = properties[2].toIntOrNull()
            val qtyDelivered = properties[5].toIntOrNull()
            return if ((caseSize == null) || (qtyDelivered == null))
                null
            else
                DeliveryToADepot(Product(properties[0]),Item(properties[1]),caseSize,Depot(properties[3]),Supplier(properties[4]),qtyDelivered)
        }
    }
}
