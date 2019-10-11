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
