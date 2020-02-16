## Challenge 21 ##

### SudokuGrid.completeSoduku() 
 
This solves a simple sudoku. It maps over each value in the list and replaces any zeros if there is a single value that could replace it. It then repeats itself until the grid remains unchanged.
If there are no zeros left then the sudoku is solved, otherwise it remains incomplete.
 
### SudokuGrid.completeAllSodoku(alreadyComplete:List<SudokuGrid>)  
(the bonus part) 

This solves more complex sudokus. It uses completeSoduku to solve the puzzle as far as it can.
If a complete sudoku is returned it returns the complete sudoku plus the list *alreadyComplete* 
 
It then finds all positions that have more than one potential value.
If there are no positions with a potential value then the puzzle can't be solved so it returns the list *alreadyComplete*.

If there are positions then using only the position with the least number of potential values it then for each value it:

+ Replaces the value in the current sudoku grid

+ Calls itself to find the solution using the updated grid. 

n.b. its not strictly necessary to only try the position with the least number of values but using that approach the permutations generated may be extremely large.