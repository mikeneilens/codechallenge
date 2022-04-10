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

fun findRemainingAnswers(answers:List<String>, guesses:Set<Pair<String, List<String>>>) =
    answers.filter{answer -> guesses.all{ (guess, matches) ->  wordleResult(guess, answer) == matches }}

//part three
data class AnswersAndGuesses(val answers:List<String>, val guesses:Set<Pair<String, List<String>>>)

val answerCache = mutableMapOf<AnswersAndGuesses, List<String>>()

fun findAnswersWithCache(answers:List<String>, guesses:Set<Pair<String, List<String>>>) =
    if (AnswersAndGuesses(answers, guesses) in answerCache ) {
        answerCache.getValue(AnswersAndGuesses(answers, guesses))
    } else {
        val remainingWords = findRemainingAnswers(answers, guesses)
        answerCache[AnswersAndGuesses(answers, guesses)] = remainingWords
        remainingWords
    }

typealias AnswerGuesser = (wordList:List<String>, guess:Set<Pair<String, List<String>>>) -> String

fun partThree(wordList:List<String>, answerGuesser:AnswerGuesser ):Int =
    wordList.fold(0){total, wordToGuess ->
        total + guessAnswer(wordToGuess, wordList, setOf(), answerGuesser) }

tailrec fun guessAnswer(wordToGuess:String, wordList:List<String>, guesses:Set<Pair<String, List<String>>>, answerGuesser:AnswerGuesser ):Int {
    if (guesses.size >= 6) return -10
    val guess = answerGuesser(wordList, guesses)
    val wordleResultForNextGuess = wordleResult(guess, wordToGuess )
    if (wordleResultForNextGuess.all { it == "GREEN" }) return 6 - (guesses.size + 1)
    val remainingAnswers = findAnswersWithCache(wordList.filter{ it!= guess}, guesses + Pair(guess, wordleResultForNextGuess))
    return guessAnswer (wordToGuess, remainingAnswers, guesses + Pair(guess, wordleResultForNextGuess), answerGuesser)
}

//part four

fun answerGuesserP4(wordList: List<String>, guesses: Set<Pair<String, List<String>>>):String =
    answerGuesserP4(wordList, guesses, "soare","input")

fun answerGuesserP4(wordList: List<String>, guesses: Set<Pair<String, List<String>>>,word1:String, word2:String):String {
    if (guesses.none{it.first == word1}) return word1
    if (guesses.none{it.first == word2}) return word2
    return wordList.first()
}

fun partFour(wordList: List<String>):Int = partThree(wordList, ::answerGuesserP4)