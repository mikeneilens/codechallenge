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

