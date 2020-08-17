import kotlin.math.pow

val operators = mapOf<String,(Double, Double)->Double>(
    "*" to {p1,p2 -> p1.pow(p2)},
    "X" to {p1,p2 -> p1 * p2},
    "/" to {p1,p2 -> p1 / p2},
    "+" to {p1,p2 -> p1 + p2},
    "-" to {p1,p2 -> p1 - p2}
)


class CalcNode(var value:Double, var operator:String, var nextCalcNode:CalcNode? = null) {

    operator fun plus(other:CalcNode):CalcNode{ //adds a part to the end of the list, returning the root
        findLastPart().apply{ nextCalcNode = other}
        return this
    }

    fun findLastPart():CalcNode = nextCalcNode?.let{ it.findLastPart() } ?: this

}

operator fun CalcNode?.plus(other:CalcNode)= this?.let { it + other} ?: other

sealed class Result {
    class Success(val value: Double) : Result()
    class InvalidNumber(val message: String) : Result()
}

fun calculate(expression: String): Result {
    try {
        val calcNode = parse(expression) //this will throw an exception if invalid characters are found.
        return Result.Success(calculate(calcNode))
    }
    catch (exception: NumberFormatException) {
        return Result.InvalidNumber(exception.toString())
    }
}
fun calculate(rootCalcNode: CalcNode): Double {
    operators.forEach { operator ->
        var calcNode = rootCalcNode
        while (calcNode.nextCalcNode != null) {
            if (calcNode.operator == operator.key) {
                calcNode.nextCalcNode?.let { nextPart ->
                    calcNode.value = operator.value( calcNode.value , nextPart.value)
                    calcNode.operator = nextPart.operator
                    calcNode.nextCalcNode = nextPart.nextCalcNode
                }
            } else {
                calcNode = calcNode.nextCalcNode ?: calcNode
            }
        }
    }

    return rootCalcNode.value
}

fun parse(expression:String):CalcNode {
    val chars = expression.withZeroPrefix()
        .replace(" ","")
        .replace("**","*")
        .toList().map{it.toString()}
    var calcNode:CalcNode? = null
    var numberText = ""
    chars.forEach {char ->
        if (operators.keys.contains(char)) {
            calcNode =  calcNode+ CalcNode(numberText.toDouble(),char)
            numberText = ""
        } else {
            numberText += char
        }
    }
    return calcNode + CalcNode(numberText.toDouble(),"")
}

// ++++++++++++++++++++++++++++++++++ Bonus challenge ++++++++++++++++++++++//

fun calculateWithParenthesis(expression: String): Result {
    if (expression.containsParenthesis()) {
        val expressionWithinParenthesis = expression.getFirstExpressionWithinParenthesis()
        val valueWithinParenthesis = calculate(expressionWithinParenthesis)
        if (valueWithinParenthesis is Result.Success) {
            val newExpression = expression.replaceFirst("($expressionWithinParenthesis)","${valueWithinParenthesis.value}")
            return calculateWithParenthesis(newExpression)
        } else {
            return valueWithinParenthesis  //this is a failure
        }
    } else {
        return calculate(expression)
    }
}

fun String.containsParenthesis() = contains(')')

fun String.getFirstExpressionWithinParenthesis():String {
    return  subSequence(indexOfFirstOpen + 1, indexOfFirstClose ).toString()
}

fun String.indexOfFirstBefore(index:Int, f:(Char)->Boolean )=
    index - 1 - (0..(index-1)).reversed().takeWhile { !f(get(it)) }.size

val String.indexOfFirstClose get() = indexOfFirst{it == ')'}
val String.indexOfFirstOpen get() = indexOfFirstBefore(indexOfFirstClose){it == '('}

fun String.withZeroPrefix() = (if (first() == '+' || first() == '-') "0" else "") + this
