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


data class EANResult(val product:String
                     , val EAN:String
                     , val item:String
                     , val depot:String
                     , val supplier:String
                     , val qtyToShop:Int
                     , val qtyToDepot:Int
                     , val totalForEAN:Int
                     , val totalForItemDepot:Int
                     , val perc:Double  )

data class DepotItemResult(val depot:String
                           , val item:String
                           , val supplier:String
                           , val unitsDelivered:Int
                           , val totalUnitsDelivered:Int
                           , val perc:Double )

fun calculateResult(listOfEANs:List<DiscountsForAnEAN>, listOfDeliveryToAShop: List<DeliveryToAShop>, listOfDeliveryToADepot: List<DeliveryToADepot>):List<EANResult> {
    val totalDeliveryForEachEAN = calculateTotalDeliveryForEachKey(listOfDeliveryToAShop)
    val totalDeliveryForEachDepotItem = calculateTotalDeliveryForEachKey(listOfDeliveryToADepot)

    val results = listOfEANs.flatMap{discountForAnEAN ->
        val deliveriesForEAN = listOfDeliveryToAShop.filter{it.EAN == discountForAnEAN.EAN}
        val totalDeliveriesForEAN = totalDeliveryForEachEAN[discountForAnEAN.EAN] ?: 0
        calculateResultForEAN(deliveriesForEAN,listOfDeliveryToADepot,totalDeliveryForEachDepotItem,totalDeliveriesForEAN)
    }
    return results
}

fun calculateResultForEAN(deliveriesForEAN:List<DeliveryToAShop>, listOfDeliveryToADepot: List<DeliveryToADepot>, totalDeliveryForEachDepotItem:Map<String, Int>, totalForEAN:Int):List<EANResult> =
    deliveriesForEAN.flatMap{deliveryForEAN ->
        val deliveriesForDepotItem = listOfDeliveryToADepot.filter{it.key == deliveryForEAN.depotItemKey}
        val totalDeliveriesForDepotItem = totalDeliveryForEachDepotItem[deliveryForEAN.depotItemKey] ?: 0
        val resultsForDepotItem = calculateResultForDepotItem(deliveriesForDepotItem, totalDeliveriesForDepotItem)
        resultsForDepotItem.map{ depotItem -> EANResult(deliveryForEAN.product,deliveryForEAN.EAN, deliveryForEAN.item,deliveryForEAN.depot, depotItem.supplier,
            deliveryForEAN.unitsDelivered, depotItem.unitsDelivered, totalForEAN, totalDeliveriesForDepotItem, depotItem.perc * deliveryForEAN.unitsDelivered / totalForEAN) }
    }

fun calculateResultForDepotItem(deliveriesForDepotItem:List<DeliveryToADepot>, totalForDeliveryItem:Int):List<DepotItemResult> =
    deliveriesForDepotItem.map{deliveryForDepotItem ->
        val perc = 1.0  * deliveryForDepotItem.unitsDelivered / totalForDeliveryItem
        DepotItemResult(deliveryForDepotItem.depot,deliveryForDepotItem.item, deliveryForDepotItem.supplier, deliveryForDepotItem.unitsDelivered, totalForDeliveryItem, perc)
    }