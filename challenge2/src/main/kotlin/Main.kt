fun main(args: Array<String>) {
    println(fixPriceLabel("Was £200, then £150, now £100"))
}

class PriceLabel(original: String) {
    private val price:String = (original.removePrefix("Was ").removePrefix("then ").removePrefix("now "))
    val value:Double

    init {
        value = price.removePrefix("£").toDouble()
    }
    fun addPrefix(lastPrice:PriceLabel, firstPrice:PriceLabel) = when {
        this == lastPrice -> "now $price"
        this == firstPrice -> "Was $price, "
        else -> "then $price, "
    }
}

fun List<PriceLabel>.allPricesAreLessThan(priceLabel:PriceLabel):Boolean {
    return this.none { priceInList -> priceLabel.value <= priceInList.value }
}

fun List<PriceLabel>.removeInvalidLabels(): List<PriceLabel> {

    return this.filterIndexed{index, priceLabel ->
        val remainingLabels = this.drop(index + 1)
        remainingLabels.allPricesAreLessThan(priceLabel)
    }
}

fun List<PriceLabel>.toPriceLabelString():String {
    return this.joinToString(separator = "") { priceLabel -> priceLabel.addPrefix(this.last(), this.first()) }
}

fun fixPriceLabel(priceLabelString:String):String {
    val listOfLabels = priceLabelString.split(", ").map{PriceLabel(it)}
    val validLabels = listOfLabels.removeInvalidLabels()
    return validLabels.toPriceLabelString()
}