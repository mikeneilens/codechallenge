class Node(val item: String, var next:Node? = null)
class IntNode(val item: Int, var next:IntNode? = null)

fun getDescription(node: Node):String{
    val next = node.next
    return if (next == null) "${node.item} Null" else "${node.item} " + getDescription(next)
}
fun getDescription(node: IntNode):String{
    val next = node.next
    return if (next == null) "${node.item} Null" else "${node.item} " + getDescription(next)
}

fun addToList(node:Node, newItem:String) {
    val next = node.next
    if (next == null) node.next = Node(newItem) else addToList(next,newItem)
}

fun convertToInteger(node:Node):IntNode {
    val intItem = node.item.toIntOrNull() ?: 0
    val next = node.next
    return if (next == null) IntNode(intItem)
    else IntNode(intItem, convertToInteger(next))
}

fun reverse(node:Node, previous:Node? = null):Node {
    val newNode = Node(node.item, previous)
    val next = node.next
    return if (next == null) newNode else reverse(next, newNode)
}