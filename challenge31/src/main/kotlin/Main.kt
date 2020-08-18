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

    fun findLastPart():CalcNode = nextCalcNode?.findLastPart() ?: this

    fun clone():CalcNode {
        val clonedNext = nextCalcNode?.clone()
        return CalcNode(this.value, this.operator, clonedNext)
    }

    fun calculate():Double {
        val root = clone()
        operators.forEach { operator ->
            var calcNode = root
            while (calcNode.nextCalcNode != null) {
                if (calcNode.operator == operator.key) {
                    calcNode.combineNodes(operator.value)
                } else {
                    calcNode = calcNode.nextCalcNode ?: calcNode
                }
            }
        }
        return root.value
    }

    private fun combineNodes(calculation: (Double, Double) -> Double) {
        nextCalcNode?.let { nextCalcNode ->
            value = calculation(value, nextCalcNode.value)
            operator = nextCalcNode.operator
            this.nextCalcNode = nextCalcNode.nextCalcNode
        }
    }

    override fun toString(): String = "$value$operator${nextCalcNode ?: ""}"
}

operator fun CalcNode?.plus(other:CalcNode)= this?.let { it + other} ?: other

sealed class Result
class Success(val value: Double) : Result()
class InvalidNumber(val message: String) : Result()

fun calculate(expression: String): Result {
    return try {
        val calcNode = parse(expression) //this will throw an exception if invalid characters are found.
        Success(calcNode.calculate())
    }
    catch (exception: NumberFormatException) {
        InvalidNumber(exception.toString())
    }
}

fun parse(expression:String):CalcNode {
    val chars = expression
        .replace(" ","")
        .replace("**","*")
        .withZeroPrefix()
        .toList().map{it.toString()}
    var calcNode:CalcNode? = null
    var numberText = ""
    chars.forEach {char ->
        if (operators[char] != null) {
            calcNode += CalcNode(numberText.toDouble(), char) //throws if expression is badly formed
            numberText = ""
        } else {
            numberText += char
        }
    }
    return calcNode + CalcNode(numberText.toDouble(),"")
}

// ++++++++++++++++++++++++++++++++++ Bonus challenge ++++++++++++++++++++++//

fun calculateWithParenthesis(expression: String): Result =
    if (expression.containsParenthesis()) {
        val expressionWithinParenthesis = expression.getFirstExpressionWithinParenthesis()
        val valueWithinParenthesis = calculate(expressionWithinParenthesis)
        if (valueWithinParenthesis is Success) {
            val newExpression = expression.replaceFirst("($expressionWithinParenthesis)","${valueWithinParenthesis.value}")
            calculateWithParenthesis(newExpression)
        } else {
            valueWithinParenthesis  //this is a failure
        }
    } else {
        calculate(expression)
    }


fun String.containsParenthesis() = contains(')')

fun String.getFirstExpressionWithinParenthesis() = subSequence(indexOfFirstOpen + 1, indexOfFirstClose ).toString()

fun String.indexOfFirstBefore(index:Int, predicate:(Char)->Boolean )=
    index - 1 - (0 until index).reversed().takeWhile { !predicate(get(it)) }.size


val String.indexOfFirstClose get() = indexOfFirst{it == ')'}
val String.indexOfFirstOpen get() = indexOfFirstBefore(indexOfFirstClose){it == '('}

fun String.withZeroPrefix() = (if (first() == '+' || first() == '-') "0" else "") + this
