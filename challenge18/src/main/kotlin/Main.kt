class Node(val item: String, var next:Node? = null)
class IntNode(val item: Int, var next:IntNode? = null)

fun getDescription(node: Node):String = node.next?.let{next -> "${node.item} " + getDescription(next)} ?: "${node.item} Null"

fun getDescription(node: IntNode):String = node.next?.let{next-> "${node.item} " + getDescription(next)}  ?: "${node.item} Null"

fun addToList(node:Node, newItem:String):Unit = node.next?.let {next -> addToList(next, newItem) } ?: node.run { next = Node(newItem) }

fun convertToInteger(node:Node):IntNode = node.next?.let{next -> IntNode(node.itemToInt(), convertToInteger(next))}  ?:  run{IntNode(node.itemToInt())}

private fun Node.itemToInt() = item.toIntOrNull() ?: 0

fun reverse(node:Node, previous:Node? = null):Node {
    val newNode = Node(node.item, previous)
    return node.next?.let{next -> reverse(next, newNode)} ?: newNode
}