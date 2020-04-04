var solutions = mutableListOf<List<Int>>()


fun solve_sudoku(grid:List<Int>):List<List<Int>> {
    solutions = mutableListOf()
    val board = convert_list_to_board(grid)
    solve_it(board)
    println("solutions found:")
    solutions.forEach { solution->
        println("solutions found: $solution")

    }
    return solutions

}

fun solve_it(_board:MutableList<MutableList<Int>> ):MutableList<MutableList<Int>> {
    var board = _board
    for (x in 0..8) {
        for (y in 0..8){
            if (board[y][x] == 0) {
                for (num in 1..9) {
                    if (check_if_number_allowed(board, x, y, num)) {
                        board[y][x] = num
                        board = solve_it(board)
                        board[y][x] = 0

                    }
                }
                return board
            }

        }
    }
    add_solution(board)
    return board

}

fun add_solution(solution:List<List<Int>>) {
    solutions.add(convert_board_to_list(solution))
    println("solution=${solution} ${solutions.size}")
}

fun convert_list_to_board(grid:List<Int>):MutableList<MutableList<Int>> = grid.chunked(9).map{it.toMutableList()}.toMutableList()

fun convert_board_to_list(board:List<List<Int>>):MutableList<Int>{
    val flat_list = mutableListOf<Int>()
    for (row in board) {
        for (col in row) {
            flat_list.add(col)
        }
    }
    return flat_list
}


fun check_if_number_allowed(board:List<List<Int>>, x:Int, y:Int, num:Int):Boolean{
    for (col in 0..8) {
        if (board[y][col] == num) return false
    }
    for (row in 0..8) {
        if (board[row][x] == num) return false
    }
    val x_square = (x / 3) * 3
    val y_square = (y / 3) * 3
    for (col_square in 0..2) {
        for (row_square in 0..2) {
            if (board[y_square + row_square][x_square + col_square] == num) return false
        }
    }
    return true
}
