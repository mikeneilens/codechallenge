//part one

typealias LetterChecker = (guessLetter: Char, answerLetter: Char, qtyOfEachLetter: Map<Char, Int>) -> Boolean

val isGreen = {guessLetter: Char, answerLetter: Char, _: Map<Char, Int> -> guessLetter == answerLetter}

val isYellow = {guessLetter: Char, answerLetter: Char, qtyOfEachLetter: Map<Char, Int> ->
    ( guessLetter != answerLetter ) && ( guessLetter in qtyOfEachLetter ) && ( qtyOfEachLetter.getValue(guessLetter) > 0 ) }

fun wordleResult(guess: String, answer: String):List<String> {
    val result = guess.map{"GREY"}.toMutableList()
    val qtyOfEachLetter = answer.groupingBy { it }.eachCount().toMutableMap()
    updateResult("GREEN", guess, answer, qtyOfEachLetter, result, isGreen)
    updateResult("YELLOW", guess, answer, qtyOfEachLetter, result, isYellow)
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

//part two

fun findAnswers(answers:List<String>, guesses:Set<Pair<String, List<String>>>) =
     answers.filter{answer -> guesses.all{ (guess, matches) ->  wordleResult(guess, answer) == matches }}


