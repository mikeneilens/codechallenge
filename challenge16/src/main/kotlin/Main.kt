data class RomanSymbol(val symbol: String, val value: Int, val conversionValue: Int)

fun addRomanNumbers(first:String, second:String):String = (first.fromRomanSymbolToInt + second.fromRomanSymbolToInt).fromIntToRomanSymbol

val romanKeyValues = mapOf("M" to 1000, "CM" to 900, "D" to 500,"CD" to 400, "C" to 100, "XC" to 90, "L" to 50, "XL" to 40, "X" to 10, "IX" to 9, "V" to 5, "IV" to 4, "I" to 1 )

val String.fromRomanSymbolToInt:Int get() {
    val result:Int =  this.toList().map{it.toString()}.foldRight(Pair(0,0),{ romanDigit, (total, valueOfLastRomanDigit) ->
        val valueOfRomanDigit = romanKeyValues[romanDigit] ?: 0
        if (valueOfRomanDigit >= valueOfLastRomanDigit) Pair(total + valueOfRomanDigit, valueOfRomanDigit) else Pair(total - valueOfRomanDigit, valueOfRomanDigit)
    }).first
    return result
}

val Int.fromIntToRomanSymbol: String get() =
    romanKeyValues.toList().fold(Pair("",this)){ (result,remainder),(symbol, value) ->
        val multiplesOfTheValue = remainder / value
        Pair(result + symbol.repeat(multiplesOfTheValue), remainder - multiplesOfTheValue * value)
    }.first