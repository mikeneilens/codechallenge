import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MainTestSG {
    @Test
    fun `completeAllSudoku should solve a hard problem with more than one answer`() {

        val results =  solve_sudoku(multipleAnswerProblem)

        results.forEachIndexed { index, result ->
            Assertions.assertTrue(result.isLegitimate)
        }
    }
}