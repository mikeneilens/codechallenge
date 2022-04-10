import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec ({
    "guessing answer for piece gives a score of 2 using the simple word guesser" {
        guessAnswer("piece", wordList, emptySet()){worldList, _ -> worldList.first()} shouldBe 2
    }
    "guessing answer for aahed gives a score of 5 using the simple word guesser" {
        guessAnswer("aahed", wordList, emptySet()){worldList, _ -> worldList.first()} shouldBe 5
    }
    "guessing answer for all words gives a score of -19325 using the simple word guesser" {
        partThree(wordList){worldList, _ -> worldList.first()} shouldBe -19325
    }

    "using p4 word guesser if the guesses don't contain soare return soare as the guess " {
        answerGuesserP4(listOf(), setOf()) shouldBe "soare"
    }
    "using p4 word guesser if the guesses contain soare but don't contain input return input as the guess" {
        answerGuesserP4(listOf(), setOf(Pair("soare",listOf<String>()))) shouldBe "input"
    }
    "using p4 word guesser if the guesses contain soare and contain input return the first word from the word list" {
        answerGuesserP4(listOf("word1","word2","word3"), setOf(Pair("soare",listOf<String>()),Pair("input",listOf<String>()))) shouldBe "word1"
    }
    "using the answer guesser for part four gives a score of 1755" {
        partFour(wordList) shouldBe 1755
    }
    "using the custom answer guesser" {
        fun answerGuesser(wordList: List<String>, guesses: Set<Pair<String, List<String>>>):String {
            if (guesses.none{it.first == "plant"}) return "plant"
            if (guesses.none{it.first == "crowd"}) return "crowd"
            return wordList[wordList.size/2]
        }
        partThree(wordList,::answerGuesser) shouldBe 9016
    }
})