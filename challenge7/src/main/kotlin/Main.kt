
val numberIsEvenAndLessThanSomething = fun (something:Int, aNumber:Int)  = (aNumber % 2 == 0 && aNumber < something)

val numberIsEvenAndLessThan = fun (something:Int):(Int) -> Boolean {
        return {aNumber:Int -> numberIsEvenAndLessThanSomething(something, aNumber)  }
    }


//This is a more general purpose way. It converts any function that accepts two integers and returns a boolean into a function that takes one parameter and returns boolean
typealias functionThatTakesTwoParams = (Int, Int) -> Boolean
typealias functionThatTakesOneParam = (Int) -> Boolean

fun functionThatTakesTwoParams.curried(something: Int):functionThatTakesOneParam {
    return {aNumber:Int -> this(something,aNumber) }
}

