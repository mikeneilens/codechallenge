import junit.framework.TestCase.assertEquals
import org.junit.Test

class MainTest {
    @Test
    fun `Test the function  that takes two parameters called numberIsEvenAndLessThanSomething` () {
        assertEquals(true, numberIsEvenAndLessThanSomething(5,2))
        assertEquals(false, numberIsEvenAndLessThanSomething(5,3))
        assertEquals(false, numberIsEvenAndLessThanSomething(5,6))
    }

    @Test
    fun `test the function called isEvenAndLessThan that returns a function that accepts one parameter` () {
        val numberIsEvenAndLessThan5 = numberIsEvenAndLessThan(5)

        assertEquals(true, numberIsEvenAndLessThan5(2))
        assertEquals(false, numberIsEvenAndLessThan5(3))
        assertEquals(false, numberIsEvenAndLessThan5(6))
    }

    @Test
    fun `Test the function called isEvenAndLessThan with a filter function` () {
        assertEquals(listOf(2,4), listOf(1,2,3,4,5,6,7,8,9,10).filter (numberIsEvenAndLessThan(5)))
    }

    @Test
    fun `test the curried function to make sure it produces a function the same as isEvenAndLessThan does` () {
        val numberIsEvenAndLessThan5 = numberIsEvenAndLessThanSomething.curried(5)
        assertEquals(true, numberIsEvenAndLessThan5(2))
        assertEquals(false, numberIsEvenAndLessThan5(3))
        assertEquals(false, numberIsEvenAndLessThan5(6))
    }

    @Test
    fun `test the curried function with a function that does something else` () {
        val numberIsAMultipleOfSomething = fun (something:Int, aNumber:Int)  = (aNumber % something == 0)

        val numberIsAMultipleOf3 = numberIsAMultipleOfSomething.curried(3)

        assertEquals(true, numberIsAMultipleOf3(6))
        assertEquals(true, numberIsAMultipleOf3(72))
        assertEquals(false, numberIsAMultipleOf3(13))

    }

    @Test
    fun `test the curried function with a function that takes a string and an Int and writes out a string`() {
        val createScoreText = fun (text:String, score:Int):String = "$text $score"

        assertEquals ("Your score is 180", createScoreText("Your score is", 180))

        val createYouScoreText = createScoreText.curried("Your score is")
        assertEquals("Your score is 180", createYouScoreText(180))
    }
}
