## Challenge 16

I decided to convert roman number to an Int, add the Ints and then convert the Ints to a roman number. This seemed simpler following a TDD approach.

 
To convert the string of roman symbols to an Int I did the following:
 
* Going from right to left, Roman digits ascend in value. If a digit descends in value then it is a negative value. Eg. reading XCV from right to left you get 5 + 100 - 10, which equals 95.

* So to calculate the value as a decimal integer, read each roman digit in the string and subtract it from the total if it is less than the previous digit, or add it to the total if it is greater or equal to the previous digit. 

To convert an Int to a string I did the following:
 
 * Work through all the roman symbols, starting with the highest. 

 * For each symbol determine how many multiples of it are in the Integer and add that many of the symbols to a cummulative string.
 
 * Substract the value of the symbols added to the string from the integer.
  
 
 