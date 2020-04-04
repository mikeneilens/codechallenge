
fun validCodesInList(listOfCodes:List<Int>) =
    listOfCodes.fold(listOf<Int>()){ acc, code ->
        if (isAValidCode(code)) acc + code
        else acc
    }

fun isAValidCode(code: Int): Boolean {
    val codeAsList = code.toString().toList().map{it.toInt()}
    if (codeAsList.size != 6) return false
    if (!codeAsList.containsPair()) return false
    if (!codeAsList.isAscending()) return false
    return true
}

fun validCodesInListPartTwo(listOfCodes:List<Int>) =
    listOfCodes.fold(listOf<Int>()){ acc, code ->
        if (isAValidCodePartTwo(code)) acc + code
        else acc
    }

fun isAValidCodePartTwo(code: Int): Boolean {
    val codeAsList = code.toString().toList().map{it.toString().toInt()}
    if (codeAsList.size != 6) return false
    if (!codeAsList.containsPairNotInGroup()) return false
    if (!codeAsList.isAscending()) return false
    return true
}

fun List<Int>.containsPair() = this.fold(Pair(-1,false)){(previousDigit, result), digit ->
    if (digit == previousDigit) Pair(digit,true)
    else Pair(digit, result)
}.second
fun List<Int>.isAscending() = this.fold(Pair(-1,true)){(previousDigit, result), digit ->
    if (digit < previousDigit) Pair(digit,false)
    else Pair(digit, result)
}.second

fun List<Int>.containsPairNotInGroup():Boolean {
    val firstListOfDigits:MutableList<Int> = mutableListOf(this.first())
    return this.drop(1).fold(listOf(firstListOfDigits)) { acc, digit ->
        if (digit == acc.last().last()) {
            val lastListInAcc = acc.last()
            lastListInAcc.add(digit)
            acc
        } else acc + listOf(mutableListOf(digit))
    }.map { it.size }.any { it == 2 }
}