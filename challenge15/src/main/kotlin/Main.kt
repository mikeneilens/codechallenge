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

data class Result(val product:String
                  , val EAN:String
                  , val item:String
                  , val depot:String
                  , val supplier:String
                  , val qtyToShop:Int
                  , val qtyToDepot:Int
                  , val totalForEAN:Int
                  , val totalForItemDepot:Int
                  , val perc:Double  )

fun calculateResult(listOfEANs:List<DiscountsForAnEAN>, listOfDeliveryToAShop: List<DeliveryToAShop>, listOfDeliveryToADepot: List<DeliveryToADepot>):List<Result> {
    val totalDeliveryForEachEAN = calculateTotalDeliveryForEachKey(listOfDeliveryToAShop)
    val totalDeliveryForEachDepotItem = calculateTotalDeliveryForEachKey(listOfDeliveryToADepot)
    val results = mutableListOf<Result>()

    for (discountForAnEAN in listOfEANs) {
        val deliveriesForEAN = listOfDeliveryToAShop.filter{it.EAN == discountForAnEAN.EAN}
        val totalForEAN = totalDeliveryForEachEAN[discountForAnEAN.EAN] ?: 0
        for (deliveryForEAN in deliveriesForEAN){
            val key = "${deliveryForEAN.depot} ${deliveryForEAN.item}"
            val deliveriesForDepotItem = listOfDeliveryToADepot.filter{it.key == key}
            val totalForDeliveryItem = totalDeliveryForEachDepotItem[key] ?: 0
            for (deliveryForDepotItem in deliveriesForDepotItem) {
                val perc = 1.0 * deliveryForEAN.unitsDelivered / totalForEAN * deliveryForDepotItem.unitsDelivered / totalForDeliveryItem
                val result = Result(discountForAnEAN.product,discountForAnEAN.EAN, deliveryForEAN.item,deliveryForEAN.depot, deliveryForDepotItem.supplier,
                    deliveryForEAN.unitsDelivered, deliveryForDepotItem.unitsDelivered, totalForEAN, totalForDeliveryItem, perc)
                results.add(result)
                println(result.toString())
            }

        }
    }
    return results
}

/*
//This extends a function which is an ObjectCreator with a function called createList.
//Decided to do this in a more conventional way by creating function String.toListOfObjects() instead.
fun <ObjectType:Any>ObjectCreator<ObjectType>.createList(list:StringContainingCSV, noOfProperties: Int):List<ObjectType>
   = list.split(",")
        .windowed(noOfProperties)
        .mapNotNull{this(it)}
*/