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

    "A list of one Apple and one Oranges and one Pear creates 7 combinations" {
        possibleLunch(listOf(Apple, Orange, Pear)) shouldBe
                setOf(
                    setOf(Apple),
                    setOf(Orange),
                    setOf(Pear),
                    setOf(Apple, Orange),
                    setOf(Apple, Pear),
                    setOf(Orange, Pear),
                    setOf(Apple, Orange, Pear))
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

    "fruit of first digit of '10' when there are 5 fruits is Grapes" {
        fruitForDigit(0, "10".length, '1', listOf(Apple, Orange, Pear, Grapes, Nectarine)) shouldBe Grapes
    }
    "fruit of second digit of '11' when there are 5 fruits is Nectarine" {
        fruitForDigit(1, "11".length, '1', listOf(Apple, Orange, Pear, Grapes, Nectarine)) shouldBe Nectarine
    }
    "fruit of second digit of '10' when there are 5 fruits is null" {
        fruitForDigit(1, "10".length, '0', listOf(Apple, Orange, Pear, Grapes, Nectarine)) shouldBe null
    }
    "fruit of first digit of '100' when there are 5 fruits is Pear" {
        fruitForDigit(0, "100".length, '1', listOf(Apple, Orange, Pear, Grapes, Nectarine)) shouldBe Pear
    }

    //optimised
    "(Optimised) A list of one Apple and one Oranges and one Pear creates 7 combinations" {
        possibleLunch2(listOf(Apple, Orange, Pear)) shouldBe
                listOf(
                    listOf(Pear),
                    listOf(Orange),
                    listOf(Orange, Pear),
                    listOf(Apple),
                    listOf(Apple, Pear),
                    listOf(Apple, Orange),
                    listOf(Apple, Orange, Pear))
    }
    "(Optimised) A list of twenty fruit generates 1048575 different combinations" {
        possibleLunch2(listOf(Apple, Orange, Pear, Grapes, Nectarine, Strawberries, Clementine, Plums, Blueberries, Banana, Melon, Mango, Kiwifriut, Pomegranate, Apricot, Coconut, Grapefruit, Tangerine, Cherries, Tomato)).size shouldBe 1048575
    }


})

