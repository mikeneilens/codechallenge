import kotlin.random.Random

class Dice(_numberProvider:ProvidesNumberBetweenOneAndSix = randomDice) {
    private val diceOne:Int = _numberProvider.numberBetweenOneAndSix // Random.nextInt(1, 7)
    private val diceTwo:Int = _numberProvider.numberBetweenOneAndSix //Random.nextInt(1, 7)

    val totalValue:Int

    init {
        totalValue = diceOne + diceTwo
    }

    override fun toString(): String {
        return "You threw a $diceOne and a $diceTwo"
    }
}

interface ProvidesNumberBetweenOneAndSix {
    val numberBetweenOneAndSix:Int
}

object randomDice:ProvidesNumberBetweenOneAndSix {
    override val numberBetweenOneAndSix: Int
        get() = Random.nextInt(1, 7)
}