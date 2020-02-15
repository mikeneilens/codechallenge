typealias SudokuGrid = List<Int>

fun Int.region() = (this % 9) / 3 + 3 * (this / 27)

fun SudokuGrid.numbersInRow(row: Int)= chunked(9)[row]

fun SudokuGrid.numbersInCol(col: Int)= chunked(9).map{it[col]}

fun SudokuGrid.numbersInRegion(region: Int)= mapIndexedNotNull(){index, value ->   if (index.region() == region) value else null }

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

fun SudokuGrid.indexesWithMoreThanOneAnswer() = mapIndexedNotNull()
        {index, value -> if (value == 0) Pair(index, potentialNumbers(index)) else null }

fun SudokuGrid.numberToReplaceZero(index:Int):Int {
    val potentialNumbers = potentialNumbers(index)
    return if (potentialNumbers.size == 1) potentialNumbers.first() else 0
}

fun SudokuGrid.replaceZeros():SudokuGrid = mapIndexed{ index, value -> if (value == 0) numberToReplaceZero(index) else value }

fun SudokuGrid.completeSoduku():SudokuGrid {
    val newSudoku = replaceZeros()
    if (this == newSudoku) return newSudoku
    return newSudoku.completeSoduku()
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

fun SudokuGrid.replaceValue(replaceWith:Int, replaceAt:Int) = mapIndexed{index, value -> if (index == replaceAt) replaceWith else value }

fun SudokuGrid.isLegitimate():Boolean
        = (0..8).map{ !columnRowOrRegionContainsDuplicates(it)}
        .takeWhile { it == true }.size == 9

fun List<Int>.containsDuplicates():Boolean =  (1..9).fold(false){ result, value -> result || filter{value == it}.size > 1}

fun SudokuGrid.columnRowOrRegionContainsDuplicates(index:Int)
        = numbersInRow(index).containsDuplicates() || numbersInCol(index).containsDuplicates() || numbersInRegion(index).containsDuplicates()