
class Roman(val symbol:String, val value:Int, val negativeCheck:Regex )
val romanOne = Roman( "I",1,"IV|IX".toRegex())
val romanFive = Roman( "V",5,"Z".toRegex())
val romanTen = Roman( "X",10,"XL|XC".toRegex())
val romanFifty = Roman( "L",50,"Z".toRegex())
val romanHundred = Roman( "C",100,"CD|CM".toRegex())
val romanFiveHundred = Roman( "D",500,"Z".toRegex())
val romanThousand = Roman( "M",1000,"Z".toRegex())

fun String.fromRomanToInt(): Int {
    if (this.isEmpty()) return 0
    val minusOnes = if (this.contains(romanOne.negativeCheck )) -2 * romanOne.value else 0
    val minusTens = if (this.contains(romanTen.negativeCheck )) -2 * romanTen.value else 0
    val minusHundreds = if (this.contains(romanHundred.negativeCheck )) -2 * romanHundred.value else 0
    val minusFives = if (this.contains(romanFive.negativeCheck )) -2 * romanFive.value else 0
    val ones = this.filter { it.toString() == romanOne.symbol }.count()
    val fives = this.filter { it.toString() == romanFive.symbol }.count() * 5
    val tens = this.filter { it.toString() == romanTen.symbol }.count() * 10
    val fifties = this.filter { it.toString() == romanFifty.symbol }.count() * 50
    val hundreds = this.filter { it.toString() == romanHundred.symbol }.count() * 100
    val fiveHundreds = this.filter { it.toString() == romanFiveHundred.symbol }.count() * 500
    val thousands = this.filter { it.toString() == romanThousand.symbol }.count() * 1000
    return ones + minusOnes + fives + tens + minusFives +  minusTens + fifties + hundreds + minusHundreds + fiveHundreds + thousands
}
