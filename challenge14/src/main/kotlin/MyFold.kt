fun myFold(list: List<Int>, initial:String, myFunction:(String,Int)->String): String {
    var output = initial
    for (item in list) {
        output = myFunction(output, item)
    }
    return output
}

fun <ElementType, FoldedType>myFold(list: List<ElementType>, initial:FoldedType, myFunction:(FoldedType,ElementType)->FoldedType): FoldedType {
    var output = initial
    for (item in list) {
        output = myFunction(output, item)
    }
    return output
}

typealias FoldFunction<ElementType, FoldedType> = (FoldedType,ElementType)->FoldedType

fun <ElementType, FoldedType>FoldFunction<ElementType, FoldedType>.myFold(list: List<ElementType>, initial:FoldedType): FoldedType {
    var output = initial
    for (item in list) {
        output = this(output, item)
    }
    return output
}
