
fun naiveSearch(list: List<Int>, value:Int): Boolean {
    if (list.isEmpty()) return false
    return if (list.first() == value) true
    else naiveSearch(list.drop(1), value)
}
