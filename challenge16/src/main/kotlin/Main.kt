
class RomanSymbol(val symbol: String, val value: Int, val parseValue: Int)
val romanOne = RomanSymbol("I", 1, 1)
val romanFour = RomanSymbol("IV", 4, -2)
val romanFive = RomanSymbol("V", 5, 5)
val romanNine = RomanSymbol("IX", 9, -2)
val romanTen = RomanSymbol("X", 10, 10)
val romanForty = RomanSymbol("XL", 40, -20)
val romanFifty = RomanSymbol("L", 50, 50)
val romanNinety = RomanSymbol("XC", 90, -20)
val romanHundred = RomanSymbol("C", 100, 100)
val romanFourHundred = RomanSymbol("CD", 400, -200)
val romanFiveHundred = RomanSymbol("D", 500, 500)
val romanNineHundred = RomanSymbol("CM", 900, -200)
val romanThousand = RomanSymbol("M", 1000, 1000)

val romanSymbols = listOf(romanThousand,romanNineHundred,romanFiveHundred,romanFourHundred,romanHundred, romanNinety, romanFifty,romanForty,romanTen,romanNine,romanFive,romanFour,romanOne)

fun addRomanNumbers(first:String, second:String):String {
    return  (first.fromRomanSymbolToInt() + second.fromRomanSymbolToInt()).fromIntToRomanSymbol()
}

fun String.fromRomanSymbolToInt(): Int =
    romanSymbols.fold(0){acc, romanSymbol ->
        acc + this.countOccurrences(romanSymbol.symbol) * romanSymbol.parseValue
    }

fun Int.fromIntToRomanSymbol(): String =
    romanSymbols.fold(Pair("",this)){ (result,remainder),romanSymbol ->
        val multiples = remainder / romanSymbol.value
        Pair(result + romanSymbol.symbol.repeat(multiples), remainder - multiples * romanSymbol.value)
    }.first

fun String.countOccurrences(s:String):Int {
    return this.map{it}.windowed(s.length,1).fold(0){ acc, chars ->  acc + if  (chars == s.toList()) 1 else 0 }
}

