typealias SudokuGrid = List<Int>
typealias GridIndex = Int

val GridIndex.col get() = this % 9
val GridIndex.row get() = this / 9
val GridIndex.region get() = (this % 9) / 3 + 3 * (this / 27)

fun SudokuGrid.numbersInRow(row: Int)= chunked(9)[row]

fun SudokuGrid.numbersInCol(col: Int)= chunked(9).map{it[col]}

fun SudokuGrid.numbersInRegion(region: Int)= mapIndexedNotNull(){index, value ->   if (index.region == region) value else null }

fun SudokuGrid.numbersAlreadyUsed(index:GridIndex)=  numbersInCol(index.col).union(numbersInRow(index.row)).union(numbersInRegion(index.region))


fun SudokuGrid.potentialNumbers(index:GridIndex):Set<Int> {
    val numbersAlreadyUsed = numbersAlreadyUsed(index)
    return (1..9).mapNotNull { if (numbersAlreadyUsed.contains(it)) null else it }.toSet()
}

fun SudokuGrid.indexesWithMoreThanOneAnswer()
        = mapIndexedNotNull() {index, value -> if (value == 0) Pair(index, potentialNumbers(index)) else null }

fun SudokuGrid.numberToReplaceZero(index:GridIndex):Int {
    val potentialNumbers = potentialNumbers(index)
    return if (potentialNumbers.size == 1) potentialNumbers.first() else 0
}

fun SudokuGrid.replaceZeros():SudokuGrid = mapIndexed{ index, value -> if (value == 0) numberToReplaceZero(index) else value }

fun SudokuGrid.completeSoduku():SudokuGrid {
    val newSudoku = replaceZeros()
    return if (this == newSudoku) newSudoku else newSudoku.completeSoduku()
}

fun SudokuGrid.completeAllSodoku(alreadyCompleted:List<SudokuGrid> = listOf()):List<SudokuGrid> {
    val sudoku = completeSoduku()

    if (!sudoku.contains(0))
        return if (sudoku.isLegitimate()) alreadyCompleted + listOf(sudoku) else alreadyCompleted

    val indexesWithMoreThanOneAnswer = sudoku.indexesWithMoreThanOneAnswer()
    if (indexesWithMoreThanOneAnswer.isEmpty()) return alreadyCompleted

    val (indexToTry, valuesToTry) = indexesWithMoreThanOneAnswer.sortedBy { it.second.size }.first()

    return valuesToTry.flatMap{valueToTry ->
         sudoku.replaceValue(valueToTry, indexToTry).completeAllSodoku(alreadyCompleted)
    }
}

fun SudokuGrid.replaceValue(replaceWith:Int, replaceAt:GridIndex) = mapIndexed{index, value -> if (index == replaceAt) replaceWith else value }

fun SudokuGrid.isLegitimate():Boolean = (0..8).map{ !columnRowOrRegionContainsDuplicates(it)}.all{it == true}

fun List<Int>.containsDuplicates():Boolean =  (1..9).fold(false){ result, value -> result || filter{value == it}.size > 1}

fun SudokuGrid.columnRowOrRegionContainsDuplicates(digit:Int)
        = numbersInRow(digit).containsDuplicates() || numbersInCol(digit).containsDuplicates() || numbersInRegion(digit).containsDuplicates()