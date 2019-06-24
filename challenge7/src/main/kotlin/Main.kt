
val numberIsEvenAndLessThanSomething = fun (something:Int, aNumber:Int)  = (aNumber % 2 == 0 && aNumber < something)

val numberIsEvenAndLessThan = fun (something:Int):(Int) -> Boolean {
        return {aNumber:Int -> numberIsEvenAndLessThanSomething(something, aNumber)  }
    }


//This is a more general purpose way. It converts any function that accepts two integers and returns a boolean into a function that takes one parameter and returns boolean
typealias functionThatTakesTwoParams = (Int, Int) -> Boolean
typealias functionThatTakesOneParam = (Int) -> Boolean

infix fun functionThatTakesTwoParams.curry(something: Int):functionThatTakesOneParam {
    return {aNumber:Int -> this(something,aNumber) }
}

//made it more generic, couldn't make it work with typealias
infix fun <P,Q,Output> ((P,Q)-> Output).curry(param1:P ):(Q)-> Output {
    return {param2:Q -> this(param1,param2) }
}
