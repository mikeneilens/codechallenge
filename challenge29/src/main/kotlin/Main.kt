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



