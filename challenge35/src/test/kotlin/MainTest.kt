import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec({
    "two empty strings returns an empty list" ({
        wordleResult("","") shouldBe listOf<String>()
    })
    "if guess contains no correct letters then return a list of GREY"() {
        wordleResult("abcd","efgh") shouldBe listOf<String>("GREY","GREY","GREY","GREY")
    }
    "if guess contains a correct letter in the correct position then return a list containing GREEN at the position and GREY for everything else"() {
        wordleResult("abcd","efch") shouldBe listOf<String>("GREY","GREY","GREEN","GREY")
    }
    "if guess contains a correct letter in the incorrect position then return a list containing YELLOW at the position and GREY for everything else"() {
        wordleResult("abcd","ecgh") shouldBe listOf<String>("GREY","GREY","YELLOW","GREY")
    }
    "if guess contains a correct letter in the correct position and in the incorrect position then return a list containing GREEN at the correct position and GREY for everything else"() {
        wordleResult("accd","ecgh") shouldBe listOf<String>("GREY","GREEN","GREY","GREY")
    }
    "if guess contains a correct letter in the the incorrect positions then return a list containing YELLOW at the first occurence and GREY for everything else"() {
        wordleResult("cacd","ecgh") shouldBe listOf<String>("YELLOW","GREY","GREY","GREY")
    }
    "if guess contains a correct letter two times but incorrect position both times then a list containing two YELLOWs and GREY for everything else"() {
        wordleResult("cacd","ecgc") shouldBe listOf<String>("YELLOW","GREY","YELLOW","GREY")
    }
})
