enum class GuessResult{Green, Yellow, Grey}

data class Guess(val word:String, val result:List<GuessResult>)

fun findAnswers(answers:List<String>, guesses:List<Guess>  ) =
    answers.map{answer -> answer.filterAllGuesses(guesses) }.filter{it != ""}

fun String.filterAllGuesses(guesses:List<Guess>):String {
    if (guesses.isEmpty()) return this
    val filteredAnswer = filterGreen(guesses.first())
        .filterYellow(guesses.first())
        .filterYellow2(guesses.first())
        .filterGrey(guesses.first())
    if (filteredAnswer.contains('!')) return ""
    else return filterAllGuesses(guesses.drop(1))
}

//Any letters in the guess that are green need to match the answer. Replace matches with _. Replace errors with !
fun String.filterGreen(guess: Guess) =
    guess.result.mapIndexed { index, result ->
        if (result != GuessResult.Green) this[index]
        else if (guess.word[index] == this[index]) '_' else '!'
    }.joinToString("")

//Any letters in the answer that are yellow must not match the answer. Replace errors with !.
fun String.filterYellow(guess: Guess) =
    guess.result.mapIndexed { index, result ->
        if (result != GuessResult.Yellow) this[index]
        else if (guess.word[index] == this[index]) '!' else this[index]
    }.joinToString("")

//Any letters in the answer that are yellow must be in the answer. Replace matcheswith _. If no matches write out !!!!.
fun String.filterYellow2(guess: Guess):String {
    var output = this
    guess.result.forEachIndexed {index, result ->
        if (result == GuessResult.Yellow)
            if (guess.word[index] in output ) output = output.replaceFirst(guess.word[index],'_')
            else output = output.map{'!'}.joinToString("")
    }
    return output
}

//Any letters in the answer are grey must not be in the answer. Replace any grey letters in the answer with !.
fun String.filterGrey(guess: Guess):String {
    var output = this
    guess.result.forEachIndexed {index, result ->
        if (result == GuessResult.Grey)
            if (guess.word[index] in output ) output = output.replaceFirst(guess.word[index],'!')
    }
    return output
}


