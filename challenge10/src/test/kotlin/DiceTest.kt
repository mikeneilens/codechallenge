import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DiceTest {

    @Test
    fun `test Dice Is Between Two And Twelve`(){
        val dice1 = Dice()
        assertTrue(dice1.totalValue >= 2 && dice1.totalValue <= 12)
    }

    @Test
    fun `test twenty dice are not all the same value`() {
        val dices = listOf(
            Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),
            Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
        )
        val dicesWithSameValueAsFirst = dices.filter{it.totalValue == dices[0].totalValue }
        assertFalse(dicesWithSameValueAsFirst.size == dices.size)
    }

    @Test
    fun `test 200 dice contain one of each possible value`() {
        val dices = listOf(
            Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
            ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
        )

        val dicesWithValue2 = dices.filter{it.totalValue == 2}
        assertTrue(dicesWithValue2.size > 0 )

        val dicesWithValue3 = dices.filter{it.totalValue == 3}
        assertTrue(dicesWithValue3.size > 0 )

        val dicesWithValue4 = dices.filter{it.totalValue == 4}
        assertTrue(dicesWithValue4.size > 0 )

        val dicesWithValue5 = dices.filter{it.totalValue == 5}
        assertTrue(dicesWithValue5.size > 0 )

        val dicesWithValue6 = dices.filter{it.totalValue == 6}
        assertTrue(dicesWithValue6.size > 0 )

        val dicesWithValue7 = dices.filter{it.totalValue == 7}
        assertTrue(dicesWithValue7.size > 0 )

        val dicesWithValue8 = dices.filter{it.totalValue == 8}
        assertTrue(dicesWithValue8.size > 0 )

        val dicesWithValue9 = dices.filter{it.totalValue == 9}
        assertTrue(dicesWithValue9.size > 0 )

        val dicesWithValue10 = dices.filter{it.totalValue == 10}
        assertTrue(dicesWithValue10.size > 0 )

        val dicesWithValue11 = dices.filter{it.totalValue == 11}
        assertTrue(dicesWithValue11.size > 0 )

        val dicesWithValue12 = dices.filter{it.totalValue == 12}
        assertTrue(dicesWithValue12.size > 0 )

        assertEquals(200,dicesWithValue2.size + dicesWithValue3.size + dicesWithValue4.size + dicesWithValue5.size +
        dicesWithValue6.size + dicesWithValue7.size + dicesWithValue8.size + dicesWithValue9.size + dicesWithValue10.size +
        dicesWithValue11.size + dicesWithValue12.size)
    }

    @Test
    fun `toString formats dice value correctly when a value of 11 is thrown`() {
        val dice5and6 = Dice(PredictableDiceValue(listOf(5,6)))
        assertTrue("$dice5and6" == "You threw a 5 and a 6" )

        val dice6and5 = Dice(PredictableDiceValue(listOf(6,5)))
        assertTrue("$dice6and5" == "You threw a 6 and a 5" )
    }
}