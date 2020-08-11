import java.lang.NumberFormatException

sealed class Result {
    class OK (val value:Double):Result()
    class Failed(val exception:Exception):Result()
}

fun calculate(expression: String): Result {
    try {
        val parsedExpression = parse(expression)
        val result = parsedExpression[0].first + parsedExpression[1].first
        return Result.OK(result)
    }
    catch (exception: Exception) {
        return Result.Failed(exception)
    }
}

fun parse(expression:String) :List<Pair<Double, String>> {
    return expression.replace(" ","")
        .replace("+", " + ")
        .replace("-", " - ")
        .replace("X", " X ")
        .replace("/", " / ")
        .replace("**", " ** ")
        .split(" ")
        .chunked(2)
        .map{pair -> if (pair.size ==2) Pair(pair[0].toDouble(), pair[1]) else Pair(pair[0].toDouble(), "")  }
}