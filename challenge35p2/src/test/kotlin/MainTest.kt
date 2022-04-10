import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec({
    "test eerie (yellow, grey, grey, yellow, green) and about (grey, grey, grey, grey, grey) using full word list " {
        val eerie = Pair("eerie",listOf("YELLOW", "GREY", "GREY", "YELLOW", "GREEN"))
        val about = Pair("about",listOf("GREY", "GREY", "GREY", "GREY", "GREY"))
        findAnswers(wordList, setOf(eerie, about)) shouldBe listOf("diene", "kieve", "liege", "lieve", "mieve", "niece", "nieve", "piece", "siege", "sieve")
    }
    "test least (grey, grey, grey, grey,yellow) torch (yellow,green,grey,grey,green) " {
        val least = Pair("least", listOf("GREY","GREY","GREY","GREY","YELLOW"))
        val torch = Pair("torch",listOf("YELLOW","GREEN","GREY","GREY","GREEN"))
        findAnswers(wordList, setOf(least, torch)) shouldBe listOf("booth", "fouth", "fowth", "month", "mouth", "youth")
    }
    "test least (grey, yello, grey, grey,grey) wreck (grey,grey,yellow,grey,grey), given (grey,yellow,green,yellow,grey) " {
        val least = Pair("least", listOf("GREY","YELLOW","GREY","GREY","GREY"))
        val wreck = Pair("wreck",listOf("GREY","GREY","YELLOW","GREY","GREY"))
        val given = Pair("given",listOf("GREY","YELLOW","GREEN","YELLOW","GREY"))
        findAnswers(wordList, setOf(least, wreck, given)) shouldBe listOf("dovie", "juvie", "movie")
    }
    "test " {
        val least = Pair("least", listOf("GREY","YELLOW","GREY","GREY","GREY"))
        val green = Pair("green",listOf("GREY","GREY","YELLOW","GREY","GREY"))
        val choke = Pair("choke",listOf("GREY","GREY","GREEN","GREY","YELLOW"))
        findAnswers(wordList, setOf(least, green,choke)) shouldBe listOf("emoji", "epoxy")
    }
})