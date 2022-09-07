import kotlin.math.pow

typealias Lunches = Set<Fruit>

fun possibleLunch(fruits:List<Fruit>) = fruits.fold(emptySet(), ::addFruit)

fun addFruit(allLunches:Set<Lunches>, fruit: Fruit) =
    allLunches + allLunches.map{ lunch -> lunch + fruit } + setOf(setOf(fruit))

//optimised version
fun possibleLunch2(fruits:List<Fruit>) =
    (1 until fruits.numberOfCombinations)
        .map{ it.toString(2)}
        .map { binary ->
            binary.mapIndexedNotNull{digit, digitValue -> fruitForDigit(digit, binary.length, digitValue, fruits)}
        }

val List<Fruit>.numberOfCombinations get() = 2.0.pow(size).toInt()

fun fruitForDigit(digit:Int, binaryLength:Int, digitValue:Char, fruits:List<Fruit>) =
    if (digitValue == '1') fruits[fruits.size - binaryLength + digit] else null
