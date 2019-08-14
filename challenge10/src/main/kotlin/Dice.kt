import kotlin.random.Random

class Dice {
    private val diceOne:Int = Random.nextInt(1, 7)
    private val diceTwo:Int = Random.nextInt(1, 7)

    val totalValue:Int

    init {
        totalValue = diceOne + diceTwo
    }

    override fun toString(): String {
        return "You threw a $diceOne and a $diceTwo"
    }
}
