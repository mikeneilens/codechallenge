## Challenge 5 optional challenge 

#### Design decisions.
* I left the original challenge that created getGridStatus as it is.
* I added a helper function to Array to enable an element to be replaced, returning a new copy of the array.
* I added a helper function to String to enable an item in the string to be replaced, returning a new copy of the string.
* The general logic for addToken(grid) is:
..* Determin the last token played from the grid to determine what colour token to play.
..* Determine the grids resulting from adding a token to any of the columns in the grid.
..* Determine if any of these grids have won by using getGridStatus, and return the first winning grid.
..* If there are no winning grids, determine each of the seven grids resulting from adding a token of the opposite colour and whether any of those grids win. 
..* If there are grids where the opposite colour can't subsequently win, return one of the grids.
..* If all grids result in the opposite colour being able to win, play the first one.
* An alternative approach I thought of was to create a Grid Class or Struct that contained the grid plus an optional link to an array of all subsequent grids. I didn't go down that route as it seemed too complicated for the requirement.