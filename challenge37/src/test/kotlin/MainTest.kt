import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class MainTest: StringSpec({

    "An empty list of fruit returns an empty set of lunches" {
        possibleLunch(listOf()) shouldBe setOf<Fruit>()
    }

    "A list of one Apple creates a set of combinations containing a list of the one Apple" {
        possibleLunch(listOf(Apple)) shouldBe setOf(setOf(Apple))
    }

    "A list of one Apple and one Orange creates a set of combinations containing a list of one Apple, one Orange and (Apple, Orange)" {
        possibleLunch(listOf(Apple, Orange)) shouldBe
                setOf(
                    setOf(Apple),
                    setOf(Orange),
                    setOf(Apple, Orange))
    }

    "A list of one Apple and two Oranges creates a set of combinations containing a list of one Apple, one Orange and (Apple, Orange)" {
        possibleLunch(listOf(Apple, Orange, Orange)) shouldBe
                setOf(
                    setOf(Apple),
                    setOf(Orange),
                    setOf(Apple, Orange))
    }

    "A list of five fruit generates 31 different combinations" {
        possibleLunch(listOf(Apple, Orange, Pear, Grapes, Nectarine)).size shouldBe 31
    }

    "A list of ten fruit generates 1023 different combinations" {
        possibleLunch(listOf(Apple, Orange, Pear, Grapes, Nectarine, Strawberries, Clementine, Plums, Blueberries, Banana)).size shouldBe 1023
    }

    "A list of twenty fruit generates 1048575 different combinations" {
        possibleLunch(listOf(Apple, Orange, Pear, Grapes, Nectarine, Strawberries, Clementine, Plums, Blueberries, Banana, Melon, Mango, Kiwifriut, Pomegranate, Apricot, Coconut, Grapefruit, Tangerine, Cherries, Tomato)).size shouldBe 1048575
    }
})

