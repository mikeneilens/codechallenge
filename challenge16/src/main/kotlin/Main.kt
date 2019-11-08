
class Roman(val symbol:String, val value:Int, val negativeCheck:Regex = "Z".toRegex() )
val romanOne = Roman( "I",1,"IV|IX".toRegex())
val romanFour = Roman( "IV",4)
val romanFive = Roman( "V",5)
val romanNine = Roman( "IX",9)
val romanTen = Roman( "X",10,"XL|XC".toRegex())
val romanForty = Roman( "XL",40)
val romanFifty = Roman( "L",50)
val romanNinety = Roman( "XC",90)
val romanHundred = Roman( "C",100,"CD|CM".toRegex())
val romanFiveHundred = Roman( "D",500)
val romanThousand = Roman( "M",1000)

val romanValues = listOf(romanOne,romanFive,romanTen,romanFifty, romanHundred,romanFiveHundred,romanThousand)
val romanSymbols = listOf(romanThousand, romanFiveHundred,romanHundred, romanNinety, romanFifty,romanForty,romanTen,romanNine,romanFive,romanFour,romanOne)

fun String.fromRomanToInt(): Int =
     romanValues.fold(0){acc, element -> acc
        acc + this.filter { it.toString() == element.symbol }.count() * element.value  + (if (this.contains(element.negativeCheck )) -2 * element.value else 0)
     }


fun Int.fromIntToRomanSymbol(): String =
    romanSymbols.fold(Pair("",this)){ (result,remainder),roman ->
        val multiples = remainder / roman.value
        Pair(result + roman.symbol.repeat(multiples), remainder - multiples * roman.value)
    }.first


