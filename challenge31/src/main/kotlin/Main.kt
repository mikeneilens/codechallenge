import kotlin.math.pow

typealias Calculation = (Double, Double) -> Double

enum class Operator(val symbol:String,  val calculation: Calculation) {
    Power("**",{ p1, p2 -> p1.pow(p2)}),
    Multiply ("X", { p1, p2 -> p1 * p2}),
    Divide ("/", { p1, p2 -> p1 / p2}),
    Add ("+", { p1, p2 -> p1 + p2}),
    Minus ("-",{ p1, p2 -> p1 - p2})
}

sealed class Result
class Success(val value: Double) : Result()
class InvalidNumber(val message: String) : Result()

fun Char.isPartOfNumber() = "0123456789.".contains(this)

fun String.numberAfter(index:Int):String {
    var end = index + 1
    while (end < length && get(end).isPartOfNumber()) {
        end++
    }
    return subSequence(index + 1, end).toString()
}
fun String.numberBefore(index:Int):String {
    val reversedString = reversed()
    val reversedIndex = length - index - 1
    return reversedString.numberAfter(reversedIndex).reversed()
}

fun String.calculate():Result {
    return try {
        var result = this.replace(" ","").withZeroPrefix()
        Operator.values().forEach {
            result = result.calculate(it)
        }
        Success( result.toDouble())
    }
    catch (exception:NumberFormatException) {
        InvalidNumber(exception.toString())
    }
}
fun String.calculate(operator:Operator):String {
    var string = this
    while (string.contains(operator.symbol)) {
        val startIndexOfSymbol = string.indexOf(operator.symbol)
        if (startIndexOfSymbol == 0) return string // needed for when first number in the string is negative
        string = string.substituteCalculatedValue(startIndexOfSymbol, operator)
    }
    return string
}

fun String.substituteCalculatedValue(startIndexOfSymbol:Int, operator: Operator):String {
    val endIndexOfSymbol = startIndexOfSymbol + operator.symbol.length - 1
    val number1 = numberBefore(startIndexOfSymbol)
    val number2 = numberAfter(endIndexOfSymbol)
    val newNumber = operator.calculation(number1.toDouble(),number2.toDouble()).toString()
    return replace("$number1${operator.symbol}$number2",newNumber)
}

// ++++++++++++++++++++++++++++++++++ Bonus challenge ++++++++++++++++++++++//

fun String.calculateWithParenthesis(): Result =
    if (containsParenthesis()) {
        val expressionWithinParenthesis = getFirstExpressionWithinParenthesis()
        val valueWithinParenthesis = expressionWithinParenthesis.calculate()
        if (valueWithinParenthesis is Success) {
            val newExpression = replaceFirst("($expressionWithinParenthesis)","${valueWithinParenthesis.value}")
            newExpression.calculateWithParenthesis()
        } else {
            valueWithinParenthesis  //this is a failure
        }
    } else {
        calculate()
    }


fun String.containsParenthesis() = contains(')')

fun String.getFirstExpressionWithinParenthesis() = subSequence(indexOfFirstOpen + 1, indexOfFirstClose ).toString()

fun String.indexOfFirstBefore(index:Int, predicate:(Char)->Boolean )=
    index - 1 - (0 until index).reversed().takeWhile { !predicate(get(it)) }.size


val String.indexOfFirstClose get() = indexOfFirst{it == ')'}
val String.indexOfFirstOpen get() = indexOfFirstBefore(indexOfFirstClose){it == '('}

fun String.withZeroPrefix() = (if (first() == '+' || first() == '-') "0" else "") + this