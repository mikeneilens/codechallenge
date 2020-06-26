
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
    if (value  < list.midValue ) return binarySearch(list.firstHalf(), value)
    else return  binarySearch(list.secondtHalf(), value)
}
val List<Int>.midValue:Int  get() = get(size/2)
fun List<Int>.firstHalf():List<Int> = dropLast(size - size / 2)
fun List<Int>.secondtHalf():List<Int> = drop(size / 2)

