fun myFold(list: List<Int>, acc:String, myFunction:(String,Int)->String): String {
    var output = acc
    for (item in list) {
        output += "$item"
    }
    return output
}