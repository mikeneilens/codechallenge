data class DiscountsForAnEAN(val product:Product, val EAN:EAN, val discountValue:Double) {
    companion object:CreatesObjectFromAListOfStrings<DiscountsForAnEAN> {
        override val numberOfProperties: Int = 3
        override fun createObjectOrNull(properties:List<String>):DiscountsForAnEAN? {
            if ((properties.size != numberOfProperties) || (!properties[2].startsWith("£"))) return null
            val discountValueOrNull = properties[2].removePrefix("£").toDoubleOrNull()
            return if (discountValueOrNull == null)
                null
            else
                DiscountsForAnEAN(Product(properties[0]), EAN(properties[1]), discountValueOrNull)
        }
    }
}