typealias StringContainingCSV = String

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:(List<String>)->ObjectType?): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}

fun calcTotalDeliveryQtyToShopForEachEAN(allDeliveriesToShop:List<DeliveryToAShop>):Map<String, Int> {
    val totalForEachEAN = emptyMap<String, Int>().toMutableMap()
    for (deliveryToAShop in allDeliveriesToShop) {
        val currentTotalForEAN = totalForEachEAN[deliveryToAShop.EAN] ?: 0
        totalForEachEAN[deliveryToAShop.EAN] = currentTotalForEAN + deliveryToAShop.qtyDelivered * deliveryToAShop.caseSize
    }
    return totalForEachEAN
}