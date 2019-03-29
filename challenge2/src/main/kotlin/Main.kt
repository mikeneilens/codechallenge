fun main(args: Array<String>) {
    fixPriceLabel("Was £200, then £150, now £100")
}

fun fixPriceLabel(priceLabelString:String):String {


    class PriceLabel(original: String) {
        val price:String
        val value:Double

        init {
            price  = (original.removePrefix("Was ").removePrefix("then ").removePrefix("now "))
            value = price.removePrefix("£").toDouble()
        }
        fun toString(index:Int, lastIndex:Int) = when(index) {
            lastIndex -> "now $price"
            0 -> "Was $price, "
            else -> "then $price, "
        }
    }

    val ListOfLabels = priceLabelString.split(", ").map{PriceLabel(it)}

    fun removeInvalidLabels(listOfLabels:List<PriceLabel>, output:List<PriceLabel> = listOf()):List<PriceLabel> {
        if (listOfLabels.isEmpty()) return output

        val nextPriceLabel = listOfLabels.first()
        val remainingPriceLabels = listOfLabels.drop(1)

        return if (remainingPriceLabels.filter{ nextPriceLabel.value < it.value }.isEmpty()) {
            removeInvalidLabels(listOfLabels.drop(1),output + nextPriceLabel)
        } else {
            removeInvalidLabels(remainingPriceLabels,output )
        }
    }
    val validLabels = removeInvalidLabels(ListOfLabels)
    return validLabels.mapIndexed { index, priceLabel -> priceLabel.toString(index, validLabels.size-1) }.joinToString("")
}