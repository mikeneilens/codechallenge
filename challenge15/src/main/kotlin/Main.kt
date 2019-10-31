typealias StringContainingCSV = String

interface CreatesObjectFromAListOfStrings<Object> {
    fun createObjectOrNull(properties:List<String>):Object?
    val numberOfProperties:Int
}
fun <ObjectType:Any>StringContainingCSV.toListOfObjects(objectCreator:CreatesObjectFromAListOfStrings<ObjectType>): List<ObjectType> =
    this.split(",")
        .windowed(objectCreator.numberOfProperties)
        .mapNotNull{objectCreator.createObjectOrNull(it)}

data class RebateForAnEAN(val product:Product, val EAN:EAN, val supplier:Supplier, val percentRebate:Double, val rebateAmount:Double)

data class RebateForAProduct(val product:Product, val supplier:Supplier, val rebateAmount:Double)

fun calculateRebatesForEANs(discountsForEANs:List<DiscountsForAnEAN>, deliveriesToAShop:List<DeliveryToAShop>, deliveriesToDepots:List<DeliveryToADepot>)
    =  discountsForEANs
        .flatMap{ discountForAnEAN -> EANRebateCalculator(discountForAnEAN.EAN, deliveriesToAShop, deliveriesToDepots).suppliersGrouped
            .map{ suppliersGrouped -> RebateForAnEAN(discountForAnEAN.product, discountForAnEAN.EAN, suppliersGrouped.supplier, suppliersGrouped.percentRebate,0.5 * suppliersGrouped.percentRebate * discountForAnEAN.discountValue)}}

fun List<RebateForAnEAN>.sumByProduct() = this.groupingBy { Pair(it.product, it.supplier) }
    .aggregate { _, accumulator: Double?, element, _ -> (accumulator ?: 0.0) + element.rebateAmount }
    .map{ RebateForAProduct(it.key.first, it.key.second, it.value)}

fun calculateRebate(discountsForEachEANCSV:StringContainingCSV, deliveriesToAShopCSV:StringContainingCSV, deliveriesToADepotCSV:StringContainingCSV):List<RebateForAProduct> {
    val discountsForEANs = discountsForEachEANCSV.toListOfObjects(DiscountsForAnEAN)
    val deliveriesToAShop = deliveriesToAShopCSV.toListOfObjects(DeliveryToAShop)
    val deliveriesToDepots = deliveriesToADepotCSV.toListOfObjects(DeliveryToADepot)

    val rebatesForEANs = calculateRebatesForEANs(discountsForEANs, deliveriesToAShop, deliveriesToDepots)

    return  rebatesForEANs.sumByProduct()
}

fun calculateDiscountForEachEAN(salesTransaction: List<EANSold>): List<DiscountsForAnEAN> {
    return emptyList()
}