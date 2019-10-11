fun myFold(list: List<Int>, acc:String, myFunction:(String,Int)->String): String {
    if (list.isEmpty()) return ""
    else {
        var output = acc
        for (item in list) {
            output += "$item"
        }
        return output
    }
}