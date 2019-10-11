fun myFold(list: List<Int>, initial:String, myFunction:(String,Int)->String): String {
    var output = initial
    for (item in list) {
        output = myFunction(output, item)
    }
    return output
}