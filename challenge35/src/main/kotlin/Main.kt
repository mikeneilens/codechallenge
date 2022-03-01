typealias LetterChecker = (letter: Char, answerLetter: Char, numberOfEachLetter: MutableMap<Char, Int>) -> Boolean

val isGreen = {letter: Char, answerLetter: Char, _: MutableMap<Char, Int> -> letter == answerLetter}

val isYellow = {letter: Char, answerLetter: Char, numberOfEachLetter: MutableMap<Char, Int> ->
    ( letter != answerLetter ) && ( letter in numberOfEachLetter ) && ( numberOfEachLetter.getValue(letter) > 0 ) }


fun wordleResult(guess: String, answer: String):List<String> {
    val result = guess.map{"grey"}.toMutableList()
    val numberOfEachLetter = answer.groupingBy { it }.eachCount().toMutableMap()
    updateResult("green", guess, answer, numberOfEachLetter, result, isGreen)
    updateResult("yellow", guess, answer, numberOfEachLetter, result, isYellow)
    return result
}

private fun updateResult(colour: String, guess: String, answer: String, numberOfEachLetter: MutableMap<Char, Int>, result: MutableList<String>, isValidLetter: LetterChecker) {
    guess.forEachIndexed { index, letter ->
        if (isValidLetter(letter, answer[index], numberOfEachLetter)) {
            numberOfEachLetter[letter] = numberOfEachLetter.getValue(letter) - 1
            result[index] = colour
        }
    }
}


