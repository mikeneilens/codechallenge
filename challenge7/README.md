## Challenge 7

To begin with I created an anonymous function called __isEvenAndLessThanSomething__ that takes two integers and returns a boolean with some tests to make sure it worked.

I then created the tests and then the function required for the excercise called __numberIsEvenAndLessThan__. I used the function I created and tested previously inside of this funciton.

It was quite easy to make this more general purpose currying function by creating a type of (Int,Int)->Bool and then extending it with a function called __curried__.

I created tests that did the same as the original __numberIsEvenAndLessThan__ and then created an additional test that converted a different function to a function that accepts one parameter and returns a boolean.

I then tried to make the curry function more general purpose by intead of using __(Int, Int)__ -> Bool used __<P,Q,Output> (P,Q)->Output__. Must admit I had to look up how to do this as I couldn't make it work using typealias instead of hard to read type definitions.
