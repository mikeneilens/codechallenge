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

fun SudokuGrid.indexesWithMoreThanOneAnswer() = mapIndexedNotNull()
        {index, value -> if (value == 0) Pair(index, potentialNumbers(index)) else null }

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

fun SudokuGrid.completeAllSodoku(completed:List<SudokuGrid> = listOf()):List<SudokuGrid> {
    val sudoku = completeSoduku()

    if (!sudoku.contains(0))
        return if (sudoku.isLegitimate()) completed + listOf(sudoku)
        else completed

    val indexesWithMoreThanOneAnswer = sudoku.indexesWithMoreThanOneAnswer()

    if (indexesWithMoreThanOneAnswer.isEmpty()) return completed

    val (indexToTry, solutionsToTry) = indexesWithMoreThanOneAnswer.sortedBy { it.second.size }.first()

    return solutionsToTry.flatMap{solution ->
        val newSudoku = sudoku.mapIndexed{index, value -> if (index == indexToTry) solution else value }
        newSudoku.completeAllSodoku(completed)
    }
}

fun SudokuGrid.print() {
    println(this.chunked(9)[0])
    println(this.chunked(9)[1])
    println(this.chunked(9)[2])
    println(this.chunked(9)[3])
    println(this.chunked(9)[4])
    println(this.chunked(9)[5])
    println(this.chunked(9)[6])
}

fun SudokuGrid.isLegitimate():Boolean {
    var legitimate = true
    (0..8).forEach{ if  (numbersInRow(it).intersect( setOf(1,2,3,4,5,6,7,8,9)).size < 9 )  legitimate = false   }
    (0..8).forEach{ if  (numbersInCol(it).intersect( setOf(1,2,3,4,5,6,7,8,9)).size < 9) legitimate = false   }
    (0..8).forEach{ if  (numbersInRegion(it).intersect( setOf(1,2,3,4,5,6,7,8,9)).size < 9) legitimate = false   }
    return legitimate
}