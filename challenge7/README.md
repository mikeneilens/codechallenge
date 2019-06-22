## Challenge 7 - Kotlin

To begin with I created an anonymous function called __isEvenAndLessThanSomething__ that takes two integers and returns a boolean with some tests to make sure it worked.

I then created the tests and then the function required for the excercise called __numberIsEvenAndLessThan__. I used the function I created and tested previously inside of this funciton.

It was quite easy to make this more general purpose currying function by creating a type of (Int,Int)->Bool and then extending it with a function called __curried__.

I created tests that did the same as the original __numberIsEvenAndLessThan__ and then created an additional test that converted a different function to a function that accepts one parameter and returns a boolean.

I then tried to make the curry function more general purpose by intead of using __(Int, Int)__ -> Bool used __<P,Q,Output> (P,Q)->Output__. Must admit I had to look up how to do this as I couldn't make it work using typealias instead of hard to read type definitions.

## Challenge 7 - Swift

I copied my Kotlin solution and jumped straight into making a curried function. 

Like in Kotlin I created an anonymous function called __isEvenAndLessThanSomething__ that takes two integers and returns a boolean with some tests to make sure it worked.

Unlike Kotlin you can not extend any Type, you can only extend Class or Struct so the curried function initially had a signature of __curried((Int,Int)->Bool, Int) -> (Int)->Bool__. To make this is easier to get your head around think of it as __curried(functionType1, Int) -> functionType2__ where function1 has a signature of (Int,Int) -> Bool and function2 has a signature of (Int) -> Bool.

So to use it to create a new function did let __numberIsEvenAndLessThan5 = curried(numberIsEvenAndLessThanSomething,5)__.

After I got that working I substitued the first Int with a generic type of P, the second Int with a generic type of Q and the Bool with a generic type of Output and reran the tests.

Finally I used the curried function to create __numberIsEvenAndLessThan__. I've squeezed this onto one line!