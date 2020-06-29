import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun naiveSearch(list: List<Int>, value:Int): Boolean {
    if (list.isEmpty()) return false
    return if (list.first() == value) true
    else naiveSearch(list.drop(1), value)
}

fun binarySearch(list: List<Int>, value:Int): Boolean {
    if (list.isEmpty()) return false
    if (list.size == 1) return (list.first() == value)
    if (value == list.midValue ) return true
    return if (value  < list.midValue ) binarySearch(list.firstHalf(), value)
    else binarySearch(list.secondHalf(), value)
}
val List<Int>.midValue:Int  get() = get(size/2)
fun List<Int>.firstHalf():List<Int> = dropLast(size - size / 2)
fun List<Int>.secondHalf():List<Int> = drop(size / 2)

//==============================================================================================================

tailrec fun insertionSort(list: List<Int>, result:List<Int> = emptyList()): List<Int> {
    val itemsToAdd = list.findAllMinimums(result)
    if (itemsToAdd.isEmpty()) return result
    return insertionSort(list, result + itemsToAdd )
}

fun List<Int>.findAllMinimums(newList:List<Int> = emptyList()):List<Int> {
    var minimum:Int =  Int.MAX_VALUE
    var result = mutableListOf<Int>()
    forEach { value ->
        if (value == minimum) result.add((value))
        else if ((value < minimum) && (newList.isEmpty() || value > newList.last())) {
            minimum = value
            result = mutableListOf(value)
        }
    }
    return result
}

//==============================================================================================================

fun mergeSort(list:List<Int>):List<Int> {
    if (list.size < 2) return list
    return merge( mergeSort(list.firstHalf()), mergeSort(list.secondHalf()))
}

tailrec fun merge(list1:List<Int>, list2:List<Int>, result:List<Int> = listOf()) : List<Int> {
    if (list1.isEmpty() && list2.isEmpty()) return result
    if (list1.isEmpty()) return result + list2
    if (list2.isEmpty()) return result + list1
    return if (list1.first() < list2.first()) {
        merge(list1.drop(1), list2, result + list1.first())
    } else {
        merge(list1, list2.drop(1), result + list2.first())
    }
}


//-----  Version using coroutines ------//

fun mergeSortConcurrent(list:List<Int>):List<Int> = runBlocking {
    return@runBlocking mergeSortConcurrent(this, list)
}

suspend fun mergeSortConcurrent(coroutineScope:CoroutineScope, list:List<Int>):List<Int> {
    //delay(10)
    if (list.size < 2) return list
    val firstHalfSorted = coroutineScope.async { mergeSortConcurrent(coroutineScope, list.firstHalf()) }
    val secondHalfSorted = coroutineScope.async { mergeSortConcurrent(coroutineScope, list.secondHalf()) }
    return merge( firstHalfSorted.await(), secondHalfSorted.await())
}

//-----  Version using coroutines that process first and second half of the list sequentially------//

fun mergeSortNotConcurrent(list:List<Int>):List<Int> = runBlocking {
    return@runBlocking mergeSortNotConcurrent(this, list)
}

//suspend is not needed as this is non-blocking unless you want to add a delay.
suspend fun mergeSortNotConcurrent(coroutineScope:CoroutineScope, list:List<Int>):List<Int> {
    //delay(10)
    if (list.size < 2) return list
    val firstHalfSorted = mergeSortNotConcurrent(coroutineScope, list.firstHalf())
    val secondHalfSorted = mergeSortNotConcurrent(coroutineScope, list.secondHalf())
    return merge( firstHalfSorted, secondHalfSorted)
}

//----- Version that uses a loop ------//

fun mergeSortLoop(list:List<Int>):List<Int> {
    var listOfLists = list.map{listOf(it)}

    while (listOfLists.size > 1) {
        val mutableList = mutableListOf<List<Int>>()

        for (index in 0 until listOfLists.size step 2 ) {
            if (index < listOfLists.lastIndex) {
                mutableList.add(merge(listOfLists[index],listOfLists[index + 1]))
            } else {
                mutableList.add(listOfLists[index])
            }
        }
        listOfLists = mutableList
    }
    return listOfLists[0]
}
