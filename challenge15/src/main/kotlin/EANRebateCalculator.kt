
class EANRebateCalculator(EAN:String, listOfDeliveryToAShop: List<DeliveryToAShop>, listOfDeliveryToADepot: List<DeliveryToADepot>) {
    private val deliveriesToShop:List<DeliveryToShopRebateCalculator>

    val suppliers:List<SupplierRebatePercent> get()  = deliveriesToShop.flatMap{it.deliveriesToDepot}

    val suppliersGrouped get() = suppliers.groupingBy { it.supplier }
                                .aggregate{_ , accumulator:Double?, element, _ -> (accumulator ?: 0.0) + element.percentRebate }
                                .map{SupplierRebatePercent(it.key, it.value)}

    init{
        val onlyDeliveriesForThisEAN = listOfDeliveryToAShop.filter{it.EAN == EAN}
        val totalUnitsDelivered = onlyDeliveriesForThisEAN.fold(0){acc, element -> acc + element.unitsDelivered}
        this.deliveriesToShop =  onlyDeliveriesForThisEAN.map{DeliveryToShopRebateCalculator(it.depot, it.item, 1.0 * it.unitsDelivered/totalUnitsDelivered, listOfDeliveryToADepot)}
        if (this.deliveriesToShop.isEmpty()) println("No deliveries to shop for EAN $EAN")
    }
}

class DeliveryToShopRebateCalculator (depot:String, item:String, EANpercent:Double, listOfDeliveriesToADepot:List<DeliveryToADepot> ) {
    val deliveriesToDepot:List<SupplierRebatePercent>

    init {
        val onlyDeliveriesForThisDepotItem = listOfDeliveriesToADepot.filter{it.depot == depot && it.item == item}
        val totalDeliveryToDepot = onlyDeliveriesForThisDepotItem.fold(0){ acc, element -> acc + element.unitsDelivered}
        this.deliveriesToDepot = onlyDeliveriesForThisDepotItem.map{SupplierRebatePercent(it.supplier, EANpercent * it.unitsDelivered/totalDeliveryToDepot)}
        if (this.deliveriesToDepot.isEmpty()) println("No deliveries to depot for $depot, $item")
    }
}

data class SupplierRebatePercent(val supplier:String, val percentRebate:Double)