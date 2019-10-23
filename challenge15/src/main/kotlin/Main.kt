typealias StringContainingCSV = String
typealias ObjectCreator<Object> = (List<String>)->Object?

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:ObjectCreator<ObjectType>): List<ObjectType> =
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

fun calculateTotalDeliveryQtyToEachDepot(deliveriesToEachDepot: List<DeliveryToADepot>): Map<String, Int> {
    return mapOf()
}

/*
//This extends a function which is an ObjectCreator with a function called createList.
//Decided to do this in a more conventional way by creating function String.toListOfObjects() instead.
fun <ObjectType:Any>ObjectCreator<ObjectType>.createList(list:StringContainingCSV, noOfProperties: Int):List<ObjectType>
   = list.split(",")
        .windowed(noOfProperties)
        .mapNotNull{this(it)}
*/