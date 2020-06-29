import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val testList = listOf(914,161,396,490,978,831,534,813,644,642,312,143,688,637,333,89,250,426,290,39,330,826,3,63,598,751,700,19,104,794,729,212,565,490,903,55,97,81,260,4,458,729,90,673,75,476,697,448,411,976,527,87,496,929,955,800,786,411,263,5,852,806,51,537,804,833,627,357,731,259,702,942,942,224,79,385,220,771,878,894,824,82,225,843,540,131,300,712,615,161,69,271,28,400,29,161,194,704,820,555)

class MainTest {
    val testData = (testList + testList + testList + testList + testList + testList + testList + testList + testList + testList).shuffled()
    val sortedTestData = testData.sorted()

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

    @Test
    fun `findMinimum obtains the values in the list with the smallest value`() {
        val list = listOf(4,8,3,6,3,8,2,8,2,9,5)
        assertEquals(listOf(2,2), list.findAllMinimums())
    }


    @Test
    fun `insertion sort returns an empty list if sorting an empty list`() {
        val list = emptyList<Int>()
        assertEquals(emptyList<Int>(), insertionSort(list))

    }
    @Test
    fun `insertion sort returns a list of one if sorting a list of one`() {
        val list = listOf(1)
        assertEquals(listOf(1), insertionSort(list))

    }
    @Test
    fun `insertion sort returns a list of two if sorting a list of two that is already in the correct order`() {
        val list = listOf(3,4)
        assertEquals(listOf(3,4), insertionSort(list))

    }
    @Test
    fun `insertion sort returns a list of two if sorting a list of two that is not already in the correct order`() {
        val list = listOf(4,3)
        assertEquals(listOf(3,4), insertionSort(list))

    }
    @Test
    fun `insertion sort returns a list of four if sorting a list of four that is not already in the correct order`() {
        val list = listOf(4,3,4,1)
        assertEquals(listOf(1,3,4,4), insertionSort(list))

    }
    @Test
    fun `insertion sort returns a long list of items sorted correctly`() {
        println("Starting insertion sort")
        val startTime = System.currentTimeMillis()
        val expectedResult = testList.sorted()
        assertEquals(expectedResult, insertionSort(testList))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }

    @Test
    fun `merging two lists with both empty gives an empty list`() {
        val list1 = emptyList<Int>()
        val list2 = emptyList<Int>()
        assertEquals(emptyList<Int>(), merge(list1, list2))
    }
    @Test
    fun `merging two lists with first empty gives second list, plus the result`() {
        val list1 = emptyList<Int>()
        val list2 = listOf(3,4,5)
        assertEquals(listOf(1,2,3,4,5), merge(list1, list2, listOf(1,2)))
    }
    @Test
    fun `merging two lists with second empty gives first list, plus the result`() {
        val list1 = listOf(3,4,5)
        val list2 = emptyList<Int>()
        assertEquals(listOf(1,2,3,4,5), merge(list1, list2, listOf(1,2)))
    }
    @Test
    fun `merging two lists each containing sorted lists creates a new sorted list`() {
        val list1 = listOf(3,4,5,9,15)
        val list2 = listOf(1,4,6,8,9,16)
        assertEquals(listOf(1,3,4,4,5,6,8,9,9,15,16), merge(list1, list2))
    }

    @Test
    fun `merge sort returns an empty list if sorting an empty list`() {
        val list = emptyList<Int>()
        assertEquals(emptyList<Int>(), mergeSort(list))

    }
    @Test
    fun `merge sort returns a list of one if sorting a list of one`() {
        val list = listOf(1)
        assertEquals(listOf(1), mergeSort(list))

    }
    @Test
    fun `merge sort returns a list of two if sorting a list of two that is already in the correct order`() {
        val list = listOf(3,4)
        assertEquals(listOf(3,4), mergeSort(list))

    }
    @Test
    fun `merge sort returns a list of two if sorting a list of two that is not already in the correct order`() {
        val list = listOf(4,3)
        assertEquals(listOf(3,4), mergeSort(list))

    }
    @Test
    fun `merge sort returns a list of four if sorting a list of four that is not already in the correct order`() {
        val list = listOf(4,3,4,1)
        assertEquals(listOf(1,3,4,4), mergeSort(list))

    }

    @Test
    fun `merge sort returns a long list of items sorted correctly`() {
        println("Starting merge sort")
        val startTime = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSort(testData))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }

    @Test
    fun `merge sort aysynch returns a long list of items sorted correctly`() {
        println("Starting merge sort using coroutine")
        val startTime = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortConcurrent(testData))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }

    @Test
    fun `merge sort not conccurrent returns a long list of items sorted correctly`() {
        println("Starting merge sort using coroutine but not processing each branch of the sort concurrently")
        val startTime = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortNotConcurrent(testData))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }

    @Test
    fun `merge sort with loop returns an empty list if sorting an empty list`() {
        val list = emptyList<Int>()
        assertEquals(emptyList<Int>(), mergeSort(list))

    }
    @Test
    fun `merge sort with loop returns a list of one if sorting a list of one`() {
        val list = listOf(1)
        assertEquals(listOf(1), mergeSortLoop(list))

    }
    @Test
    fun `merge sort with loop returns a list of two if sorting a list of two that is already in the correct order`() {
        val list = listOf(3,4)
        assertEquals(listOf(3,4), mergeSortLoop(list))

    }
    @Test
    fun `merge sort with loop returns a list of two if sorting a list of two that is not already in the correct order`() {
        val list = listOf(4,3)
        assertEquals(listOf(3,4), mergeSortLoop(list))

    }
    @Test
    fun `merge sort with loop returns a list of four if sorting a list of four that is not already in the correct order`() {
        val list = listOf(4,3,4,1)
        assertEquals(listOf(1,3,4,4), mergeSortLoop(list))

    }
    @Test
    fun `merge sort with loop returns a long list of items sorted correctly`() {
        println("Starting merge sort with loop")
        val startTime = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortLoop(testData))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }

}