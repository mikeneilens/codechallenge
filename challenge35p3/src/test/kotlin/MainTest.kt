import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec ({
    "guessing answer for piece gives a score of 2" {
        guessAnswer("piece", wordList, emptySet()){worldList, _ -> worldList.first()} shouldBe 2
    }
    "guessing answer for aahed gives a score of 5" {
        guessAnswer("aahed", wordList, emptySet()){worldList, _ -> worldList.first()} shouldBe 5
    }
    "guessing answer for all words gives a score of -19325" {
        partThree(wordList){worldList, _ -> worldList.first()} shouldBe -19325
    }
})