fun main(args: Array<String>) {

}

fun fixPriceLabel(priceLabel:String):String {
    val listOfPrices = priceLabel.split(", ")


    class priceLabel(val prefix:String, val price:String){
        constructor (original:String ):this("",original.removePrefix("Was ").removePrefix("then ").removePrefix("now "))
        var value = price.removePrefix("£").toDouble()
    }

    when {
        (listOfPrices.size == 2) -> {
            val firstPriceLabel = priceLabel(listOfPrices[0])
            val secondPriceLabel = priceLabel(listOfPrices[1])
            if (firstPriceLabel.value > secondPriceLabel.value) {
                return priceLabel
            } else {
                return "now ${secondPriceLabel.price}"
            }
        }
        (listOfPrices.size == 3) -> {
            val firstPriceLabel = priceLabel(listOfPrices[0])
            val secondPriceLabel = priceLabel(listOfPrices[1])
            val thirdPriceLabel = priceLabel(listOfPrices[2])
            if (secondPriceLabel.value > thirdPriceLabel.value) {
                if (secondPriceLabel.value < firstPriceLabel.value) {
                    return priceLabel
                } else {
                    return "Was ${secondPriceLabel.price}, now ${thirdPriceLabel.price}"
                }
            } else {
                return "Was ${firstPriceLabel.price}, now ${thirdPriceLabel.price}"
            }
        }
        else -> {
            return priceLabel
        }
    }
}