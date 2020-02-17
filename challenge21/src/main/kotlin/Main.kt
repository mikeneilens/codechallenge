typealias SudokuGrid = List<Int>
typealias GridIndex = Int

val GridIndex.col get() = this % 9
val GridIndex.row get() = this / 9
val GridIndex.region get() = col/3 + 3 * (row/3)

fun SudokuGrid.numbersInRow(row: Int)= chunked(9)[row]
fun SudokuGrid.numbersInCol(col: Int)= mapIndexedNotNull{index, value ->   if (index.col == col) value else null }
fun SudokuGrid.numbersInRegion(region: Int)= mapIndexedNotNull{index, value ->   if (index.region == region) value else null }

fun SudokuGrid.numbersAlreadyUsed(index:GridIndex)=  numbersInCol(index.col) union numbersInRow(index.row) union numbersInRegion(index.region)

fun SudokuGrid.potentialNumbers(index:GridIndex) = (1..9).toSet() - numbersAlreadyUsed(index)

fun SudokuGrid.indexesWithMoreThanOneAnswer()
        = mapIndexedNotNull { index, value -> if (value == 0) Pair(index, potentialNumbers(index)) else null }

fun SudokuGrid.numberToReplaceZero(index:GridIndex) = if (potentialNumbers(index).size == 1) potentialNumbers(index).first() else 0

fun SudokuGrid.replaceZeros():SudokuGrid = mapIndexed{ index, value -> if (value == 0) numberToReplaceZero(index) else value }

fun SudokuGrid.completeSoduku():SudokuGrid {
    val newSudoku = replaceZeros()
    return if (this == newSudoku) newSudoku else newSudoku.completeSoduku()
}

fun SudokuGrid.completeAllSodoku(alreadyCompleted:List<SudokuGrid> = listOf()):List<SudokuGrid> {
    val newSudoku = completeSoduku()

    if (newSudoku.isComplete)
        return if (newSudoku.isLegitimate) alreadyCompleted + listOf(newSudoku) else alreadyCompleted

    val indexesWithMoreThanOneAnswer = newSudoku.indexesWithMoreThanOneAnswer()
    if (indexesWithMoreThanOneAnswer.isEmpty()) return alreadyCompleted

    val (indexToTry, valuesToTry) = indexesWithMoreThanOneAnswer.sortedBy { it.second.size }.first()

    return valuesToTry.flatMap{valueToTry ->
         newSudoku.replaceValue(valueToTry, indexToTry).completeAllSodoku(alreadyCompleted)
    }
}

fun SudokuGrid.replaceValue(replaceWith:Int, replaceAt:GridIndex) = mapIndexed{index, value -> if (index == replaceAt) replaceWith else value }

val SudokuGrid.isComplete get() = !contains(0)

val SudokuGrid.isLegitimate get() = (0..8).map{ !columnRowOrRegionContainsDuplicates(it)}.all{ it }

fun SudokuGrid.columnRowOrRegionContainsDuplicates(digit:Int)
        = numbersInRow(digit).containsDuplicates || numbersInCol(digit).containsDuplicates || numbersInRegion(digit).containsDuplicates

val List<Int>.containsDuplicates get() =  (1..9).map{digit -> count{digit == it}}.any{it > 1}
