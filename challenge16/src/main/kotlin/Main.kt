const val romanOne = "I"
const val romanFive = "V"
const val romanTen = "X"
const val romanFifty = "L"
const val romanHundred = "C"
const val romanFiveHundred = "D"
const val romanThousand = "M"

fun String.fromRomanToInt(): Int {
    if (this.isEmpty()) return 0
    val minusOnes = if (this.contains("IV|IX".toRegex() )) -1 else 0
    val minusTens = if (this.contains("XL|XC".toRegex() )) -10 else 0
    val minusHundreds = if (this.contains("CD|CM".toRegex() )) -100 else 0
    val ones = this.filter { it.toString() == romanOne }.count()
    val fives = this.filter { it.toString() == romanFive }.count() * 5
    val tens = this.filter { it.toString() == romanTen }.count() * 10
    val fifties = this.filter { it.toString() == romanFifty }.count() * 50
    val hundreds = this.filter { it.toString() == romanHundred }.count() * 100
    val fiveHundreds = this.filter { it.toString() == romanFiveHundred }.count() * 500
    val thousands = this.filter { it.toString() == romanThousand }.count() * 1000
    return ones + minusOnes * 2 + fives + tens + minusTens * 2 + fifties + hundreds + minusHundreds * 2 + fiveHundreds + thousands
}
