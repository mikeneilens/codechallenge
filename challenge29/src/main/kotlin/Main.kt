
fun naiveSearch(list: List<Int>, value:Int): Boolean {
    if (list.isEmpty()) return false
    return if (list.first() == value) true
    else naiveSearch(list.drop(1), value)
}

fun binarySearch(list: List<Int>, value:Int): Boolean {
    println(".")
    if (list.isEmpty()) return false
    if (list.size == 1) return (list.first() == value)
    if (value == list.midValue ) return true
    return if (value  < list.midValue ) binarySearch(list.firstHalf(), value)
    else binarySearch(list.secondHalf(), value)
}
val List<Int>.midValue:Int  get() = get(size/2)
fun List<Int>.firstHalf():List<Int> = dropLast(size - size / 2)
fun List<Int>.secondHalf():List<Int> = drop(size / 2)

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

fun mergeSort(list:List<Int>):List<Int> {
    if (list.size < 2) return list
    return merge( mergeSort(list.firstHalf()), mergeSort(list.secondHalf()))
}

fun merge(list1:List<Int>, list2:List<Int>, result:List<Int> = listOf()) : List<Int> {
    if (list1.isEmpty() && list2.isEmpty()) return result
    if (list1.isEmpty()) return result + list2
    if (list2.isEmpty()) return result + list1
    return if (list1.first() < list2.first()) {
        merge(list1.drop(1), list2, result + list1.first())
    } else {
        merge(list1, list2.drop(1), result + list2.first())
    }
}