fun myFold(list: List<Int>, acc:Int, myFunction:(String,Int)->String): String {
    if (list.isEmpty()) return ""
    else return "${list.first()}"
}