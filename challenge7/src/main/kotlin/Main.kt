
val numberIsEvenAndLessThanSomething = fun (something:Int, aNumber:Int)  = (aNumber % 2 == 0 && aNumber < something)

val numberIsEvenAndLessThan = fun (something:Int):(Int) -> Boolean {
        return {aNumber:Int -> numberIsEvenAndLessThanSomething(something, aNumber)  }
    }


//This is a more general purpose way. It converts any function that accepts two integers and returns a boolean into a function that takes one parameter and returns boolean
typealias functionThatTakesTwoInt = (Int, Int) -> Boolean
typealias functionThatTakesOneInt = (Int) -> Boolean

infix fun functionThatTakesTwoInt.curry(param1: Int):functionThatTakesOneInt {
    return {param2:Int -> this(param1,param2) }
}

//Made it more generic. P is the type if param1 and R is the type of param2 in the function above.
infix fun <P,Q,Output> ((P,Q)-> Output).curry(param1:P ):(Q)-> Output {
    return {param2:Q -> this(param1,param2) }
}

//A curry function that takes a function of one parameter and returns the result
infix fun <P,Output> ((P)-> Output).curry(param1:P ):Output {
    return this(param1)
}

//A curry function that takes a function of two parameters and returns a function of one parameter
infix fun <P,Q,R,Output> ((P,Q,R)-> Output).curry(param1:P ):(Q,R)-> Output {
    return {param2:Q, param3:R -> this(param1,param2, param3) }
}

