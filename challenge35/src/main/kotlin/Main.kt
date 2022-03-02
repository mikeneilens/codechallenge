typealias LetterChecker = (guessLetter: Char, answerLetter: Char, qtyOfEachLetter: MutableMap<Char, Int>) -> Boolean

val isGreen = {guessLetter: Char, answerLetter: Char, _: MutableMap<Char, Int> -> guessLetter == answerLetter}

val isYellow = {guessLetter: Char, answerLetter: Char, qtyOfEachLetter: MutableMap<Char, Int> ->
    ( guessLetter != answerLetter ) && ( guessLetter in qtyOfEachLetter ) && ( qtyOfEachLetter.getValue(guessLetter) > 0 ) }

fun wordleResult(guess: String, answer: String):List<String> {
    val result = guess.map{"grey"}.toMutableList()
    val qtyOfEachLetter = answer.groupingBy { it }.eachCount().toMutableMap()
    updateResult("green", guess, answer, qtyOfEachLetter, result, isGreen)
    updateResult("yellow", guess, answer, qtyOfEachLetter, result, isYellow)
    return result
}

private fun updateResult(colour: String, guess: String, answer: String, qtyOfEachLetter: MutableMap<Char, Int>, result: MutableList<String>, isValidLetter: LetterChecker) {
    guess.forEachIndexed { index, guessLetter ->
        if (isValidLetter(guessLetter, answer[index], qtyOfEachLetter)) {
            result[index] = colour
            qtyOfEachLetter[guessLetter] = qtyOfEachLetter.getValue(guessLetter) - 1
        }
    }
}

