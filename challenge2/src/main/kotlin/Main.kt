fun main(args: Array<String>) {
    println(fixPriceLabel("Was £200, then £150, now £100"))
}

fun fixPriceLabel(priceLabelString:String):String {


    class PriceLabel(original: String) {
        val price:String
        val value:Double

        init {
            price  = (original.removePrefix("Was ").removePrefix("then ").removePrefix("now "))
            value = price.removePrefix("£").toDouble()
        }
        fun addPrefix(lastPrice:PriceLabel, firstPrice:PriceLabel) = when {
            this == lastPrice -> "now $price"
            this == firstPrice -> "Was $price, "
            else -> "then $price, "
        }
    }

    val ListOfLabels = priceLabelString.split(", ").map{PriceLabel(it)}

    fun removeInvalidLabels(listOfLabels:List<PriceLabel>): List<PriceLabel> {

        data class FoldData(val resultingLabels:List<PriceLabel>, val remainingLabels:List<PriceLabel>)

        val resultOfFold:FoldData = listOfLabels.fold(FoldData(listOf(),listOfLabels)){ foldData, priceLabel ->
            val remainingLabels = foldData.remainingLabels.drop(1)
            val resultingLabels = foldData.resultingLabels
            if (remainingLabels.filter{ remainingPrice -> priceLabel.value <= remainingPrice.value }.isEmpty()) FoldData(resultingLabels + priceLabel, remainingLabels)
            else FoldData(resultingLabels, remainingLabels)
        }

        return resultOfFold.resultingLabels
    }

    val validLabels = removeInvalidLabels(ListOfLabels)
    return validLabels.map { priceLabel -> priceLabel.addPrefix(validLabels.last(), validLabels.first()) }.joinToString("")
}