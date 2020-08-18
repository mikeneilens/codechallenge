### Challenge 31

After trying to solve this using a linked list I abandoned that and just processed the string.

The algorithm is:

For each symbol (i.e. **, X, /, +, -):
+ Find each occuracnce of the symbol.
+ Repeat until the symbol isn't foud in the string:
    + Find the text containing the number before and after the symbol. 
    + Obtain a new number by applying the operator for the symbol to the two numbers.
    + Replace $firstNumber$symbol$secondNumber with the new number.

e.g.
+ "2 + 3 X 5" has spaces removed so it is "2+3X5".
+ The X symbol is at position 3. The number before position 3 is "3" and the number after position 3 is "5".
+ 3 X 5 is 15.0.
+ The string containing "2+3X5" has "3X5" replaced with "15.0" resulting in "2+15.0".
+ The string containing "2+15.0" then has "2+15.0" replaced with "17.0".

