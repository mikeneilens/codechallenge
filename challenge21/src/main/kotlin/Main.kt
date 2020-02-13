typealias SudokuGrid = List<Int>

fun Int.region() = (this % 9) / 3 + 3 * (this / 27)

fun SudokuGrid.numbersInRow(row: Int)= chunked(9)[row].toSet()

fun SudokuGrid.numbersInCol(col: Int)= chunked(9).map{it[col]}.toSet()

fun SudokuGrid.numbersInRegion(region: Int)= mapIndexedNotNull(){index, value ->   if (index.region() == region) value else null }.toSet()

fun SudokuGrid.numbersAlreadyUsed(index:Int) : Set<Int> {
    val col = index % 9
    val row = index / 9
    val region = index.region()
    return  numbersInCol(col)
            .union(numbersInRow(row))
            .union(numbersInRegion(region))
}

fun SudokuGrid.potentialNumbers(index:Int):Set<Int> {
    val numbersAlreadyUsed = numbersAlreadyUsed(index)
    return (1..9).mapNotNull { if (numbersAlreadyUsed.contains(it)) null else it }.toSet()
}

fun SudokuGrid.numberToReplaceZero(index:Int):Int {
    val potentialNumbers = potentialNumbers(index)
    return if (potentialNumbers.size == 1) potentialNumbers.first() else 0
}

fun SudokuGrid.replaceZeros():SudokuGrid = mapIndexed{ index, value ->  if (value == 0) numberToReplaceZero(index) else value }

fun SudokuGrid.completeSoduku():SudokuGrid {
    val newSudoku = replaceZeros()
    if (this == newSudoku) return newSudoku
    return newSudoku.completeSoduku()
}