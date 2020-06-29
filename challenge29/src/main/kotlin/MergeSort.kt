import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

//--------------------  Simplest Version ---------------------------//
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

//--------------------  Version using coroutines --------------------//
fun mergeSortConcurrent(list:List<Int>):List<Int> = runBlocking {
    return@runBlocking mergeSortConcurrent(this, list)
}

suspend fun mergeSortConcurrent(coroutineScope: CoroutineScope, list:List<Int>):List<Int> {
    //delay(10)
    if (list.size < 2) return list
    val firstHalfSorted = coroutineScope.async { mergeSortConcurrent(coroutineScope, list.firstHalf()) }
    val secondHalfSorted = coroutineScope.async { mergeSortConcurrent(coroutineScope, list.secondHalf()) }
    return merge( firstHalfSorted.await(), secondHalfSorted.await())
}

//------------------ Version that uses a loop -----------------------//
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

//------------ Hybrid version using coroutines for first two legs  -----------//

fun mergeSortHybrid(list:List<Int>):List<Int> = runBlocking {
    return@runBlocking mergeSortHybrid(this, list)
}

suspend fun mergeSortHybrid(coroutineScope: CoroutineScope, list:List<Int>):List<Int> {
    //delay(10)
    if (list.size < 2) return list
    val firstHalfSorted = coroutineScope.async { mergeSortLoop( list.firstHalf()) }
    val secondHalfSorted = coroutineScope.async { mergeSortLoop( list.secondHalf()) }
    return merge( firstHalfSorted.await(), secondHalfSorted.await())
}