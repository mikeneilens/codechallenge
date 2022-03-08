import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec({

    "abc filtered with guess ade where a is green and d is grey and e is grey gives _bc " {
        "abc".filterGreen(Guess("ade",listOf(GuessResult.Green,GuessResult.Grey,GuessResult.Grey))) shouldBe "_bc"
    }
    "abc filtered with guess add where a is green and d is grey and e is green gives _b! " {
        "abc".filterGreen(Guess("ade",listOf(GuessResult.Green,GuessResult.Grey,GuessResult.Green))) shouldBe "_b!"
    }
    "abc filtered with guess dae where  d is grey and a is yellow and e is grey gives _bc " {
        "abc".filterYellow2(Guess("dae",listOf(GuessResult.Grey,GuessResult.Yellow,GuessResult.Grey))) shouldBe "_bc"
    }
    "aacc filtered with guess ddaa where d is grey and a is yellow gives __cc " {
        "aacc".filterYellow2(Guess("ddaa",listOf(GuessResult.Grey,GuessResult.Grey,GuessResult.Yellow,GuessResult.Yellow))) shouldBe "__cc"
    }
    "abcc filtered with guess ddaa where d is grey and a is yellow gives !!!! " {
        "abcc".filterYellow2(Guess("ddaa",listOf(GuessResult.Grey,GuessResult.Grey,GuessResult.Yellow,GuessResult.Yellow))) shouldBe "!!!!"
    }
    "abc filtered with guess adc where a is yellow gives !bc " {
        "abc".filterYellow(Guess("adc",listOf(GuessResult.Yellow,GuessResult.Grey,GuessResult.Grey))) shouldBe "!bc"
    }
    "abc filtered with guess def where all guesses are grey gives abc" {
        "abc".filterGrey(Guess("def",listOf(GuessResult.Grey,GuessResult.Grey,GuessResult.Grey))) shouldBe "abc"
    }
    "abc filtered with guess daf where all guesses are grey gives !bc" {
        "abc".filterGrey(Guess("daf",listOf(GuessResult.Grey,GuessResult.Grey,GuessResult.Grey))) shouldBe "!bc"
    }
    "abc filtered with guess ade where a is green and dad where a is yellow and ddd with all are grey gives abc"{
        val guessAde = Guess("ade", listOf(GuessResult.Green,GuessResult.Grey, GuessResult.Grey))
        val guessBac = Guess("dad", listOf(GuessResult.Grey,GuessResult.Yellow, GuessResult.Grey))
        val guessDdd = Guess("ddd", listOf(GuessResult.Grey,GuessResult.Grey, GuessResult.Grey))
        "abc".filterAllGuesses(listOf(guessAde,guessBac,guessDdd)) shouldBe "abc"
    }
    "abc filtered with guess ade where a is green and d is green and dad where a is yellow and ddd with all are grey gives empty string"{
        val guessAde = Guess("ade", listOf(GuessResult.Green,GuessResult.Green, GuessResult.Grey))
        val guessBac = Guess("dad", listOf(GuessResult.Grey,GuessResult.Yellow, GuessResult.Grey))
        val guessDdd = Guess("ddd", listOf(GuessResult.Grey,GuessResult.Grey, GuessResult.Grey))
        "abc".filterAllGuesses(listOf(guessAde,guessBac,guessDdd)) shouldBe ""
    }
    "abc filtered with guess ade where a is green and d is grey and daa where both a is yellow and ddd with all are grey gives empty string"{
        val guessAde = Guess("ade", listOf(GuessResult.Green,GuessResult.Green, GuessResult.Grey))
        val guessBac = Guess("daa", listOf(GuessResult.Grey,GuessResult.Yellow, GuessResult.Yellow))
        val guessDdd = Guess("ddd", listOf(GuessResult.Grey,GuessResult.Grey, GuessResult.Grey))
        "abc".filterAllGuesses(listOf(guessAde,guessBac,guessDdd)) shouldBe ""
    }
    "abc filtered with guess ade where a is green and d is grey and dad where second d is green and ddd with all are grey gives empty string"{
        val guessAde = Guess("ade", listOf(GuessResult.Green,GuessResult.Green, GuessResult.Grey))
        val guessBac = Guess("dad", listOf(GuessResult.Grey,GuessResult.Yellow, GuessResult.Green))
        val guessDdd = Guess("ddd", listOf(GuessResult.Grey,GuessResult.Grey, GuessResult.Grey))
        "abc".filterAllGuesses(listOf(guessAde,guessBac,guessDdd)) shouldBe ""
    }
    "abc filtered with guess ade where a is green and dad where a is yellow and dcd with all are grey gives empty string"{
        val guessAde = Guess("ade", listOf(GuessResult.Green,GuessResult.Grey, GuessResult.Grey))
        val guessBac = Guess("dad", listOf(GuessResult.Grey,GuessResult.Yellow, GuessResult.Grey))
        val guessDdd = Guess("dda", listOf(GuessResult.Grey,GuessResult.Grey, GuessResult.Grey))
        "abc".filterAllGuesses(listOf(guessAde,guessBac,guessDdd)) shouldBe ""
    }
    "answers abcd, abdc, wxyz match against guesses abaa (green, green, grey, grey) and adfa (green, yellow, grey, grey) gives abcd, abdc" {
        val guessAbaa = Guess("abaa", listOf(GuessResult.Green, GuessResult.Green, GuessResult.Grey, GuessResult.Grey))
        val guessAdfa = Guess("adfa", listOf(GuessResult.Green, GuessResult.Yellow, GuessResult.Grey, GuessResult.Grey))
        findAnswers(answers = listOf("abcd", "abdc", "wxyz"), guesses = listOf(guessAbaa, guessAdfa)) shouldBe listOf("abcd", "abdc")
    }
    "answers abcd, abdc, wxyz match against guesses abaa (green, green, grey, grey) and adfc (green, yellow, grey, green) gives abdc" {
        val guessAbaa = Guess("abaa", listOf(GuessResult.Green, GuessResult.Green, GuessResult.Grey, GuessResult.Grey))
        val guessAdfc = Guess("adfc", listOf(GuessResult.Green, GuessResult.Yellow, GuessResult.Grey, GuessResult.Green))
        findAnswers(answers = listOf("abcd", "abdc", "wxyz"), guesses = listOf(guessAbaa, guessAdfc)) shouldBe listOf("abdc")
    }
})