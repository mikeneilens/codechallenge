
data class DeliveryToAShop(val product: Product, val EAN: EAN, val item: Item, private val caseSize: Int, val depot: Depot, private val qtyDelivered:Int) {
    val unitsDelivered = qtyDelivered * caseSize

    companion object :CreatesObjectFromAListOfStrings<DeliveryToAShop> {
        override val numberOfProperties: Int = 6
        override fun createObjectOrNull(properties: List<String>): DeliveryToAShop? {
            if (properties.size != numberOfProperties)  return null
            val caseSize = properties[3].toIntOrNull()
            val qtyDelivered = properties[5].toIntOrNull()
            return if ((caseSize == null) || (qtyDelivered == null))
                null
            else
                DeliveryToAShop(Product(properties[0]),EAN(properties[1]),Item(properties[2]), caseSize, Depot(properties[4]), qtyDelivered)
        }
    }
}