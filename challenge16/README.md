## Challenge 16

I decided to convert roman number to an Int, add the Ints and then convert the Ints to a roman number. This seemed simpler following a TDD approach.

 
To convert the string of roman symbols to an Int I did the following:
 
* Give every roman symbol a "parse value".

* For symbols like "I", "V", "X" etc, the parseValue is simply 1,5,10 etc. 

* For symbols like "IV", "IX", "XC" the parse value is the net of the roman value with the individual symbol value subtracted. Eg. for IV it has a value of 4 less 1 (for "I") and less 5 (for the "V') so the parseValue is 4 - 5 - 1 = -2.

* For each symbol count how many times it appears in the string and add occurrences X parseValue to a cummalitve total.

So for MMXXXIV the numbers added together will be 2000 (for MM), 30 (for XXX), -2 (for IV), 5 (for V) and 1 (for I). Total is therefore 2000 + 30 - 2 + 5 + 1 = 2034.

To convert an Int to a string I did the following:
 
 * Work through all the roman symbols, starting with the highest. 

 * For each symbol determine how many multiples of it are in the Integer and add that many of the symbols to a cummulative string.
 
 * Substract the value of the symbols added to the string from the integer.
  
 
 