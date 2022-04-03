import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec ({
    "if the guesses don't contain soare return soare as the guess" {
        answerGuesserP4(listOf(), setOf()) shouldBe "soare"
    }
    "if the guesses contain soare but don't contain input return input as the guess" {
        answerGuesserP4(listOf(), setOf(Pair("soare",listOf<String>()))) shouldBe "input"
    }
    "if the guesses contain soare and contain input return the first word from the world list" {
        answerGuesserP4(listOf("word1","word2","word3"), setOf(Pair("soare",listOf<String>()),Pair("input",listOf<String>()))) shouldBe "word1"
    }
    "using the answer guesser for part four gives a score of 1755" {
        partFour(wordList) shouldBe 1755
    }
})