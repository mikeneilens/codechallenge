typealias StringContainingCSV = String
typealias ObjectCreator<Object> = (List<String>)->Object?

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:ObjectCreator<ObjectType>): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}

fun calculateTotalDeliveryForEachKey(deliveries: List<Delivery>): Map<String, Int> {
    val totalForEachKey = emptyMap<String, Int>().toMutableMap()
    for (delivery in deliveries) {
        val currentTotalForKey = totalForEachKey[delivery.key] ?: 0
        totalForEachKey[delivery.key] = currentTotalForKey + delivery.unitsDelivered
    }
    return totalForEachKey
}

fun calculateResult(listOfEANs:List<DiscountsForAnEAN>, listOfDeliveryToAShop: List<DeliveryToAShop>, listOfDeliveryToADepot: List<DeliveryToADepot>) {
    for (discountForAnEAN in listOfEANs) {
        val deliveriesForEAN = listOfDeliveryToAShop.filter{it.EAN == discountForAnEAN.EAN}
        for (deliveryForEAN in deliveriesForEAN){
            val key = "${deliveryForEAN.depot} ${deliveryForEAN.item}"
            val deliveriesForDepotItem = listOfDeliveryToADepot.filter{it.key == key}
            println("${discountForAnEAN.product} ${discountForAnEAN.EAN} ${deliveryForEAN.depot} ${deliveryForEAN.item}")
        }
    }
}

/*
//This extends a function which is an ObjectCreator with a function called createList.
//Decided to do this in a more conventional way by creating function String.toListOfObjects() instead.
fun <ObjectType:Any>ObjectCreator<ObjectType>.createList(list:StringContainingCSV, noOfProperties: Int):List<ObjectType>
   = list.split(",")
        .windowed(noOfProperties)
        .mapNotNull{this(it)}
*/