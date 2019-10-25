typealias StringContainingCSV = String
typealias ObjectCreator<Object> = (List<String>)->Object?

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:ObjectCreator<ObjectType>): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}

data class RebateForAnEAN(val product:Product, val EAN:EAN, val supplier:Supplier, val percentRebate:Double, val rebateAmount:Double)

data class RebateForAProduct(val product:Product, val supplier:Supplier, val rebateAmount:Double)

fun calculateRebatesForEANs(discountsForEANs:List<DiscountsForAnEAN>, deliveriesToAShop:List<DeliveryToAShop>, deliveriesToDepots:List<DeliveryToADepot>):List<RebateForAnEAN>
    =  discountsForEANs
        .flatMap{ discountForAnEAN -> EANRebateCalculator(discountForAnEAN.EAN, deliveriesToAShop, deliveriesToDepots).suppliersGrouped
            .map{ suppliersGrouped -> RebateForAnEAN(discountForAnEAN.product, discountForAnEAN.EAN, suppliersGrouped.supplier, suppliersGrouped.percentRebate,0.5 * suppliersGrouped.percentRebate * discountForAnEAN.discountValue)}}

fun List<RebateForAnEAN>.sumByProduct() = this.groupingBy { Pair(it.product, it.supplier) }
    .aggregate { _, accumulator: Double?, element, _ -> (accumulator ?: 0.0) + element.rebateAmount }
    .map{ RebateForAProduct(it.key.first, it.key.second, it.value)}

fun calculateRebate(discountsForEachEANCSV:StringContainingCSV, deliveriesToAShopCSV:StringContainingCSV, deliveriesToADepotCSV:StringContainingCSV):List<RebateForAProduct> {
    val discountsForEANs = discountsForEachEANCSV.toListOfObjects(3,createDiscountForEANorNull)
    val deliveriesToAShop = deliveriesToAShopCSV.toListOfObjects(6,createDeliveryToAShopOrNull)
    val deliveriesToDepots = deliveriesToADepotCSV.toListOfObjects(6,createDeliveryToADepotOrNull)

    val rebatesForEANs = calculateRebatesForEANs(discountsForEANs, deliveriesToAShop, deliveriesToDepots)

    return  rebatesForEANs.sumByProduct()
}