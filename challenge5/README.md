## Challenge 5

#### Design decisions.
* For the first part of the challenge I've returned fairly primative data structures rather than turning anything into a struct or class. I thought the code would be self explanatory without create a new model. I've used typealias to help provide some clarity, even though this doesn't provide very strong type safety.

* I extended the Swift String struct so that a character in a String can be accessed simply and efficiently using a subscript. This means to access an item in the Grid (my name for the playing area) you use grid[row][col]. The subscript returns an empty string rather than raising an exception if you go out of bounds as that made some other code simpler.

* I decided to validate whether someone had won was simplest if you find the row and col of the last token played and then create a string containing the contents of horizontal, vertical and diagonal intersections with the last token played. You can then just check whether the token contains 4 tokens that are the same colour as the last token played. 


* Swift doesn't come with a wide range of collection iterators (unlike Kotlin). You can do most things using map/filter/reduce but the code ends up difficult to read. Not having a mapIndex() function was badly missed. For that reason I've used for/in loops and the odd mutable variable. The loops are simple with small scope so that seemed a good trade-off.

* I noticed that for a given row you can determine the column of any diagonal or vertical line that intersects a (row,col). For a vertical that is obvious as the column is constant, but for one diagonal  row + col is always the same as row + col of any other point on the same diagonal. For the other diagonal row - col is always the same. So I created a single function that extracts a line from the grid that accepts a function to calculate the column for any given row. 
e.g. if the (row,col) of the last token played is (3,4) then for one diagonal the elements are at (0,7),(1,6),(2,5),(3,4),(4,3),(5,2) and for the other diagonal the elements are at (0,1),(1,2),(2,3),(3,4),(4,5),(5,6). The vertical line is (0,4),(1,4),(2,4),(3,4),(4,4),(5,4).
