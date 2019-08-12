import kotlin.random.Random

class Dice {
    private val diceOne:Int
    private val diceTwo:Int

    val totalValue:Int

    init {
        diceOne = Random.nextInt(1, 7)
        diceTwo = Random.nextInt(1, 7)
        totalValue = diceOne + diceTwo
    }

    override fun toString(): String {
        return "You threw a $diceOne and a $diceTwo"
    }
}
