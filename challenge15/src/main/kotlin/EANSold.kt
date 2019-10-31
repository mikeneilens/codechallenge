data class EANSold (val EAN:EAN, val prod:Product, val price:Double, val qty:Int) {
    fun toDiscount(discountRate:Double = 0.5):DiscountsForAnEAN = DiscountsForAnEAN(this.prod, this.EAN, this.price/2)
}