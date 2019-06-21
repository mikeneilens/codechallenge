import com.sun.org.apache.xpath.internal.operations.Bool

fun main(args: Array<String>) {

}

val numberIsEvenAndLessThanSomething = fun (something:Int, aNumber:Int)  = (aNumber % 2 == 0 && aNumber < something)

val numberIsEvenAndLessThan = fun (something:Int):(Int) -> Boolean {
        return {aNumber:Int -> numberIsEvenAndLessThanSomething(something, aNumber)  }
    }

typealias functionThatTakesTwoParms = (Int, Int) -> Boolean
typealias functionThatTakesOneParm = (Int) -> Boolean

fun functionThatTakesTwoParms.curried(something: Int):functionThatTakesOneParm {
    return {aNumber:Int -> this(something,aNumber) }
}

