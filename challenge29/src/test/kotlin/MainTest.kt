import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

val testList = listOf(914,161,396,490,978,831,534,813,644,642,312,143,688,637,333,89,250,426,290,39,330,826,3,63,598,751,700,19,104,794,729,212,565,490,903,55,97,81,260,4,458,729,90,673,75,476,697,448,411,976,527,87,496,929,955,800,786,411,263,5,852,806,51,537,804,833,627,357,731,259,702,942,942,224,79,385,220,771,878,894,824,82,225,843,540,131,300,712,615,937,69,271,28,400,29,161,194,704,820,555)

class MainTest {

    @Test
    fun `naive searching a list returns false if list is empty`() {
        val list = emptyList<Int>()
        assertFalse(naiveSearch(list, 6))
    }
    @Test
    fun `naive searching a list returns false if list is not empty but doesn't contain the item`() {
        val list = listOf(5,4,3)
        assertFalse(naiveSearch(list, 6))
    }
    @Test
    fun `naive searching a list returns true if the list contain the item`() {
        val list = listOf(5,4,3)
        assertTrue(naiveSearch(list, 4))
    }

    @Test
    fun `binary search returns false if list is empty`() {
        val list = emptyList<Int>()
        assertFalse(binarySearch(list, 6))
    }
    @Test
    fun `binary searching a list returns false if list is not empty but doesn't contain the item`() {
        val list = listOf(3,4,5)
        assertFalse(binarySearch(list, 6))
    }
    @Test
    fun `binary searching a list returns true if the list contain the item at the start`() {
        val list = listOf(1,2,3,4,5,6)
        assertTrue(binarySearch(list, 1))
    }
    @Test
    fun `binary searching a list returns true if the list contain the item at the end`() {
        val list = listOf(1,2,3,4,5,6)
        assertTrue(binarySearch(list, 6))
    }
    @Test
    fun `binary searching a list returns true if the list contain the item at the middle`() {
        val list = listOf(1,2,3,4,5,6)
        assertTrue(binarySearch(list, 4))
    }

}