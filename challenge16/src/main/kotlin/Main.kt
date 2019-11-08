
class Roman(val symbol:String, val value:Int, val negativeCheck:Regex )
val romanOne = Roman( "I",1,"IV|IX".toRegex())
val romanFive = Roman( "V",5,"Z".toRegex())
val romanTen = Roman( "X",10,"XL|XC".toRegex())
val romanFifty = Roman( "L",50,"Z".toRegex())
val romanHundred = Roman( "C",100,"CD|CM".toRegex())
val romanFiveHundred = Roman( "D",500,"Z".toRegex())
val romanThousand = Roman( "M",1000,"Z".toRegex())
val romanValues = listOf(romanOne,romanFive,romanTen,romanFifty, romanHundred,romanFiveHundred,romanThousand)


fun String.fromRomanToInt(): Int {
    return romanValues.fold(0){acc, element -> acc
        acc + this.filter { it.toString() == element.symbol }.count() * element.value  + (if (this.contains(element.negativeCheck )) -2 * element.value else 0)
    }
}

fun Int.fromIntToRoman(): String {
    return "I"
}