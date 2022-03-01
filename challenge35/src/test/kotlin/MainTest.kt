import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec({
    "two empty strings returns an empty list" ({
        wordleResult("","") shouldBe listOf<String>()
    })
    "if guess contains no correct letters then return a list of grey"() {
        wordleResult("abcd","efgh") shouldBe listOf<String>("grey","grey","grey","grey")
    }
    "if guess contains a correct letter in the correct position then return a list containing green at the position and grey for everything else"() {
        wordleResult("abcd","efch") shouldBe listOf<String>("grey","grey","green","grey")
    }
    "if guess contains a correct letter in the incorrect position then return a list containing yellow at the position and grey for everything else"() {
        wordleResult("abcd","ecgh") shouldBe listOf<String>("grey","grey","yellow","grey")
    }
    "if guess contains a correct letter in the correct position and in the incorrect position then return a list containing green at the correct position and grey for everything else"() {
        wordleResult("accd","ecgh") shouldBe listOf<String>("grey","green","grey","grey")
    }
    "if guess contains a correct letter twice in the the incorrect positions then return a list containing yellow at the first occurence and grey for everything else"() {
        wordleResult("cacd","ecgh") shouldBe listOf<String>("yellow","grey","grey","grey")
    }
    "if guess contains a correct letter two times but incorrect position both times then a list containing two yellows and grey for everything else"() {
        wordleResult("cacd","ecgc") shouldBe listOf<String>("yellow","grey","yellow","grey")
    }
})
