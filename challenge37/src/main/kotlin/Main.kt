import kotlin.math.pow

typealias Lunches = Set<Fruit>

fun possibleLunch(fruits:List<Fruit>, result:Set<Lunches> = emptySet()):Set<Lunches> =
    if (fruits.isEmpty()) result
    else {
        val lunches = result.addFruit(fruits.first())
        possibleLunch(fruits.drop(1), lunches)
    }

private fun Set<Lunches>.addFruit(fruit: Fruit) = this + map { it + fruit } + setOf(setOf(fruit))

//optimised version
fun possibleLunch2(fruits:List<Fruit>) =
    (1 until fruits.numberOfCombinations)
        .map{it.toString(2)}
        .map { binary ->
            binary.mapIndexedNotNull{digit, digitValue -> fruitForDigit(digit, binary.length, digitValue, fruits)}
        }

val List<Fruit>.numberOfCombinations get() = 2.0.pow(size).toInt()

fun fruitForDigit(digit:Int, binaryLength:Int, digitValue:Char, fruits:List<Fruit>) = if (digitValue == '1') fruits[fruits.size - binaryLength + digit] else null
