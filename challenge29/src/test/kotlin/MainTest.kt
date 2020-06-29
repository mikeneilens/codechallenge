import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

class MainTest {
    val testData = List(1000) { Random.nextInt(0, 1000) }
    val sortedTestData = testData.sorted()

    val testData10000 = List(10000) { Random.nextInt(0, 10000) }
    val sortedTestData10000 = testData10000.sorted()

    val testData100000 =  List(100000) { Random.nextInt(0, 10000) }
    val sortedTestData100000 = testData100000.sorted()

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
        assertEquals(sortedTestData, insertionSort(testData))
        println("Approx elapsed ${System.currentTimeMillis() - startTime} ")
    }
    @Test
    fun `insertion sort with loop returns a longer list of items sorted correctly`() {
        println("Starting insertion sort with loop, 10000 items")
        val startTime = System.currentTimeMillis()
        assertEquals(sortedTestData10000, insertionSort(testData10000))
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
    fun `merge sort with coroutine returns a list of four if sorting a list of four that is not already in the correct order`() {
        val list = listOf(4,3,4,1)
        assertEquals(listOf(1,3,4,4), mergeSortConcurrent(list))
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
    fun `merge sort with coroutines that calls merge sort using loop`() {
        val list = listOf(4,3,4,1)
        assertEquals(listOf(1,3,4,4), mergeSortHybrid(list))
    }

    @Test
    fun `compare different types of merge sorts 1000 items`() {
        println("testing with 1000 items.....")
        val startTimeSimple = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSort(testData))
        println("Simple merge sort elapsed: ${System.currentTimeMillis() - startTimeSimple} ")
        val startTimeAsynch = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortConcurrent(testData))
        println("Concurrent merge sort elapsed: ${System.currentTimeMillis() - startTimeAsynch} ")
        val startTimeLoop = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortLoop(testData))
        println("Loop merge sort elapsed: ${System.currentTimeMillis() - startTimeLoop} ")
        val startTimeHybrid = System.currentTimeMillis()
        assertEquals(sortedTestData, mergeSortHybrid(testData))
        println("Part concurrent, part Loop merge sort elapsed: ${System.currentTimeMillis() - startTimeHybrid} ")
    }

    @Test
    fun `compare different types of merge sorts 10000 items`() {
        println("testing with 10000 items.....")
        val startTimeSimple = System.currentTimeMillis()
        assertEquals(sortedTestData10000, mergeSort(testData10000))
        println("Simple merge sort elapsed: ${System.currentTimeMillis() - startTimeSimple} ")
        val startTimeAsynch = System.currentTimeMillis()
        assertEquals(sortedTestData10000, mergeSortConcurrent(testData10000))
        println("Concurrent merge sort elapsed: ${System.currentTimeMillis() - startTimeAsynch} ")
        val startTimeLoop = System.currentTimeMillis()
        assertEquals(sortedTestData10000, mergeSortLoop(testData10000))
        println("Loop merge sort elapsed: ${System.currentTimeMillis() - startTimeLoop} ")
        val startTimeHybrid = System.currentTimeMillis()
        assertEquals(sortedTestData10000, mergeSortHybrid(testData10000))
        println("Part concurrent, part Loop merge sort elapsed: ${System.currentTimeMillis() - startTimeHybrid} ")

    }
}