## Challenge 7 - Kotlin

To begin with I created an anonymous function called __isEvenAndLessThanSomething__ that takes two integers and returns a boolean with some tests to make sure it worked.

I then created the tests and then the function required for the excercise called __numberIsEvenAndLessThan__. I used the function I created and tested previously inside of this funciton.

It was quite easy to make this more general purpose currying function by creating a type of (Int,Int)->Bool and then extending it with an infix function called __curry__.

I created tests that did the same as the original __numberIsEvenAndLessThan__ and then created an additional test that converted a different function to a function that accepts one parameter and returns a boolean.

I then tried to make the curry function more general purpose by intead of using ```(Int, Int) -> Bool``` used ```<P,Q,Output> (P,Q) -> Output```. Must admit I had to look up how to do this as I couldn't make it work using typealias instead of hard to read type definitions.

I replicated this in Swift (see below) and then created a curry function in Kotlin to convert any function that takes three parameteres into a function that takes two parameters and also created a curry finction to convert a function that takes one parameter directly into a result. To use the curry function on a function with 3 parameters you can use:

```yourFunction curry "first parameter" curry "second parameter" curry "third parameter"```

## Challenge 7 - Swift

I copied my Kotlin solution and jumped straight into making a curry function. 

Like in Kotlin I created an anonymous function called __isEvenAndLessThanSomething__ that takes two integers and returns a boolean with some tests to make sure it worked.

Unlike Kotlin you can not extend any Type, you can only extend Class, Struct or Enum so the curried function initially had a signature of ```curried(Int, (Int,Int)->Bool) -> (Int)->Bool```. 
To make this is easier to get your head around think of it as ```curried(Int, functionType1) -> functionType2``` where function1 has a signature of ```(Int,Int) -> Bool``` and function2 has a signature of ```(Int) -> Bool```.

So to use it to create a new function:

```let numberIsEvenAndLessThan5 = curry(5, numberIsEvenAndLessThanSomething)```.

After I got that working I substitued the first Int with a generic type of P, the second Int with a generic type of Q and the Bool with a generic type of Output and reran the tests.

Finally I used the curried function to create __numberIsEvenAndLessThan__. I've squeezed this onto one line!

To make this even more confusing I thought it would be good to replace the curry function with an infix. Unfortunately in Swift you can only use certain special characters for an infix so could not just call the infix 'curry' or 'curried'. The infix I used is called <= so you can use 

```let numberIsEvenAndLessThan5 = numberIsEvenAndLessThanSomething <= 5```.   

I then set about making a version of <= which operates on a single parameter function and returns the result. I also made a version of <= which operates on a three parameter function and converts it into a two parameter function. To test this I created a three paramter function called __scoreboard__ which takes some text, a name and an integer score and writes out a string. To get the result using currying the syntax is then: 

```let scoreText = scoreBoard <= "Your score:" <= "Mike" <= 5 //returhs "Your score: Mike 5" ```.

