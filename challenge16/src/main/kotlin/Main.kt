data class RomanSymbol(val symbol: String, val value: Int, val conversionValue: Int)

//there's a repeating pattern here but automating the creation of roman Symbols took lots of complex code when I tried
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

fun addRomanNumbers(first:String, second:String):String = (first.fromRomanSymbolToInt + second.fromRomanSymbolToInt).fromIntToRomanSymbol

val String.fromRomanSymbolToInt: Int get() =
    romanSymbols.fold(0){result, romanSymbol ->
        result + this.countOccurrences(romanSymbol.symbol) * romanSymbol.conversionValue
    }

val Int.fromIntToRomanSymbol: String get() =
    romanSymbols.fold(Pair("",this)){ (result,remainder),romanSymbol ->
        val multiplesOfTheValue = remainder / romanSymbol.value
        Pair(result + romanSymbol.symbol.repeat(multiplesOfTheValue), remainder - multiplesOfTheValue * romanSymbol.value)
    }.first

fun String.countOccurrences(stringToFind:String):Int = this.toList().windowed(stringToFind.length,1).map{it.joinToString ("")}
                                                           .fold(0){ result, chars ->  if  (chars == stringToFind) result + 1 else result }


