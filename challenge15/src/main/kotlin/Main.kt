typealias StringContainingCSV = String
typealias ObjectCreator<Object> = (List<String>)->Object?

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:ObjectCreator<ObjectType>): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}

data class ResultForAnEAN(val product:String, val EAN:String, val supplier:String, val percentRebate:Double, val rebateAmount:Double)

data class ResultForAProduct(val product:String, val supplier:String, val rebateAmount:Double)

fun calculateResultForEANs(discountsForEANs:List<DiscountsForAnEAN>,deliveriesToAShop:List<DeliveryToAShop>, deliveriesToDepots:List<DeliveryToADepot>):List<ResultForAnEAN>
    =  discountsForEANs
        .flatMap{ discountForAnEAN -> EANRebateCalculator(discountForAnEAN.EAN, deliveriesToAShop, deliveriesToDepots).suppliersGrouped
            .map{ suppliersGrouped -> ResultForAnEAN(discountForAnEAN.product, discountForAnEAN.EAN,suppliersGrouped.supplier, suppliersGrouped.percentRebate,0.5 * suppliersGrouped.percentRebate * discountForAnEAN.discountValue)}}

fun calculateResultForProducts(discountsForEANs:List<DiscountsForAnEAN>,deliveriesToAShop:List<DeliveryToAShop>, deliveriesToDepots:List<DeliveryToADepot>):List<ResultForAProduct> {
    val resultsForEANs = calculateResultForEANs(discountsForEANs, deliveriesToAShop, deliveriesToDepots)

    return resultsForEANs.groupingBy { Pair(it.product, it.supplier) }
        .aggregate { _, accumulator: Double?, element, _ -> (accumulator ?: 0.0) + element.rebateAmount }
        .map{ ResultForAProduct(it.key.first, it.key.second, it.value)}
}

fun calculateRebate(discountsForEachEANCSV:StringContainingCSV, deliveriesToAShopCSV:StringContainingCSV, deliveriesToADepotCSV:StringContainingCSV):List<ResultForAProduct> {
    val discountsForEANs = discountsForEachEANCSV.toListOfObjects(3,createDiscountForEANorNull)
    val deliveriesToAShop = deliveriesToAShopCSV.toListOfObjects(6,createDeliveryToAShopOrNull)
    val deliveriesToDepots = deliveriesToADepotCSV.toListOfObjects(6,createDeliveryToADepotOrNull)

    return  calculateResultForProducts(discountsForEANs, deliveriesToAShop, deliveriesToDepots)
}