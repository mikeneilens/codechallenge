import kotlin.random.Random

interface ProvidesNumberBetweenOneAndSix {
    val numberBetweenOneAndSix:Int
}

class Dice(_numberProvider:ProvidesNumberBetweenOneAndSix = RandomDiceValue) {
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

object RandomDiceValue:ProvidesNumberBetweenOneAndSix {
    override val numberBetweenOneAndSix: Int
        get() = Random.nextInt(1, 7)
}

class PredictableDiceValue(private val list:List<Int> = listOf(1,2)):ProvidesNumberBetweenOneAndSix {
    private var index = -1

    override val numberBetweenOneAndSix: Int
        get() = getNext()

    private fun getNext():Int {
        index += 1
        index %= list.size
        return list[index]
    }

}