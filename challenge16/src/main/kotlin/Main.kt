const val romanOne = "I"
const val romanFive = "V"
const val romanTen = "X"
const val romanFifty = "L"
const val romanHundred = "C"
const val romanFiveHundred = "D"
const val romanThousand = "M"

fun String.fromRomanToInt(): Int {
    if (this.isEmpty()) return 0

    val minusOnes = if (this.contains("IV")) -1 else 0
    val ones = this.filter { it.toString() == romanOne }.count()
    val fives = this.filter { it.toString() == romanFive }.count() * 5
    return ones + minusOnes * 2 + fives

}
