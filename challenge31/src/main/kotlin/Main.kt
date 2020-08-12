import kotlin.math.pow

val operators = mapOf<String,(Double, Double)->Double>(
    "**" to {p1,p2 -> p1.pow(p2)},
    "X" to {p1,p2 -> p1 * p2},
    "/" to {p1,p2 -> p1 / p2},
    "+" to {p1,p2 -> p1 + p2},
    "-" to {p1,p2 -> p1 - p2}
)

data class MiniExpression(val value:Double, val operator:String)

fun calculate(expression: String): Result<Double> {
    try {
        var miniExpressions = parse(expression).toMutableList()

        operators.forEach{operator ->
            var nextMiniExpressions = mutableListOf<MiniExpression>()
            miniExpressions.forEachIndexed { index, miniExpression ->

                if (miniExpression.operator == operator.key) {
                    val p1 = miniExpressions[index]
                    val p2 = miniExpressions[index + 1]
                    operator.value?.let{calculation ->
                        val calculatedValue = calculation(p1.value, p2.value)
                        miniExpressions[index + 1] = MiniExpression(calculatedValue, p2.operator)
                    }

                } else {
                    nextMiniExpressions.add(miniExpression)
                }
            }
            miniExpressions = nextMiniExpressions
        }
        return Result.success(miniExpressions.last().value)
    }
    catch (exception: Exception) {
        return Result.failure(exception)
    }
}

fun calculateWithParenthesis(expression: String): Result<Double> {
    if (expression.containsParenthesis()) {
        val expressionWithinParenthesis = expression.getFirstExpressionWithinParenthesis()
        val valueWithinParenthesis = calculate(expressionWithinParenthesis)
        if (valueWithinParenthesis.isSuccess) {
            val newExpression = expression.replaceFirst("($expressionWithinParenthesis)","${valueWithinParenthesis.getOrNull()}")
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

fun parse(expression:String) :List<MiniExpression> =
    expression
        .withZeroPrefix()
        .replace(" ","")
        .replace("+", " + ")
        .replace("-", " - ")
        .replace("X", " X ")
        .replace("/", " / ")
        .replace("**", " ** ")
        .split(" ")
        .chunked(2)
        .map{pair -> if (pair.size ==2) MiniExpression(pair[0].toDouble(), pair[1])  else MiniExpression(pair[0].toDouble(),"")  }


fun String.withZeroPrefix() = (if (first() == '+' || first() == '-') "0" else "") + this