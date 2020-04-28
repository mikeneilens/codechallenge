
typealias Cube<T> = List<List<T>>
typealias Side<T> = List<T>

val Int.x get() = this % 3
val Int.y get() = this / 3
fun Pair<Int, Int>.toInt() = first + second * 3

val <T>Cube<T>.frontSide get() = this[0]
val <T>Cube<T>.backSide get() = this[1]
val <T>Cube<T>.leftSide get() = this[2]
val <T>Cube<T>.rightSide get() = this[3]
val <T>Cube<T>.topSide get() = this[4]
val <T>Cube<T>.bottomSide get() = this[5]

fun <T:Any>Side<T>.row(n:Int) = mapIndexedNotNull{ index,c -> if ((index / 3) == n) c else null }
fun <T:Any>Side<T>.column(n:Int) = mapIndexedNotNull{ index, c -> if (index % 3 == n) c else null }

infix fun <T>Side<T>.addColumn(other:Side<T>):Side<T> {
    val chunks = chunked(size/3)
    val otherChunks = other.chunked(other.size/3)
    return chunks[0] + otherChunks[0] + chunks[1] + otherChunks[1] + chunks[2] + otherChunks[2]
}

fun <T>Side<T>.rotateFaceRight(): Side<T> {
    return  mapIndexed{index, char -> Pair(Pair(2 - index.y, index.x), char) }
        .map{(position, char) -> Pair( position.toInt(), char ) }
        .sortedBy { it.first }
        .map{it.second}
}

fun <T:Any>Cube<T>.rotateTopLayer():List<List<T>> {
    val top = topSide.rotateFaceRight()
    val bottom = bottomSide
    val front = rightSide.row(0) + frontSide.row(1) + frontSide.row(2)
    val left =  frontSide.row(0) + leftSide.row(1) + leftSide.row(2)
    val back =  leftSide.row(0) + backSide.row(1) + backSide.row(2)
    val right = backSide.row(0) + rightSide.row(1) + rightSide.row(2)

    return listOf(front, back, left, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateBottomLayer():List<List<T>> {
    val top = topSide
    val bottom = bottomSide.rotateFaceRight()
    val front = frontSide.row(0) + frontSide.row(1) + leftSide.row(2)
    val right = rightSide.row(0) + rightSide.row(1) + frontSide.row(2)
    val back = backSide.row(0) + backSide.row(1) + rightSide.row(2)
    val left = leftSide.row(0) + leftSide.row(1) + backSide.row(2)

    return listOf(front, back, left, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateLeftLayer():List<List<T>> {
    val left = leftSide.rotateFaceRight()
    val right = rightSide

    val front = topSide.column(0) addColumn frontSide.column(1) addColumn frontSide.column(2)
    val back = backSide.column(0) addColumn backSide.column(1) addColumn bottomSide.column(2)
    val top = backSide.column(2).reversed() addColumn topSide.column(1) addColumn topSide.column(2)
    val bottom = bottomSide.column(0) addColumn bottomSide.column(1) addColumn frontSide.column(0).reversed()

    return listOf(front, back, left, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateRightLayer():List<List<T>> {
    val left = leftSide
    val right = rightSide.rotateFaceRight()
    val front = frontSide.column(0) addColumn frontSide.column(1) addColumn bottomSide.column(0).reversed()
    val back = topSide.column(2).reversed() addColumn backSide.column(1) addColumn backSide.column(2)
    val top = topSide.column(0) addColumn topSide.column(1) addColumn frontSide.column(2)
    val bottom = backSide.column(0) addColumn bottomSide.column(1) addColumn bottomSide.column(2)

    return listOf(front, back, left, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateFrontLayer():List<List<T>> {
    val back = backSide
    val front = frontSide.rotateFaceRight()

    val left = leftSide.column(0) addColumn leftSide.column(1) addColumn bottomSide.row(2).reversed()
    val right = topSide.row(2) addColumn rightSide.column(1) addColumn rightSide.column(2)
    val top = topSide.row(0) + topSide.row(1) + leftSide.column(2).reversed()
    val bottom = bottomSide.row(0) + bottomSide.row(1) + rightSide.column(0)

    return listOf(front, back, left, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateBackLayer():List<List<T>> {
    val back = backSide.rotateFaceRight()
    val front = frontSide
    val left = topSide.row(0).reversed() addColumn leftSide.column(1) addColumn leftSide.column(2)
    val right = rightSide.column(0) addColumn rightSide.column(1) addColumn bottomSide.row(0)
    val top = rightSide.column(2) + topSide.row(1) + topSide.row(2)
    val bottom = leftSide.column(0).reversed() + bottomSide.row(1) + bottomSide.row(2)

    return listOf(front, back, left, right, top,bottom)
}

fun rotateCube(cube:List<String>, face:String, direction:String):List<String> {

    val cubeAsList = cube.map{it.toList()}
    val rotateLayer = when (face) {
        "Front" ->  {cube:Cube<Char> -> cube.rotateFrontLayer()}
        "Back" ->  {cube:Cube<Char> -> cube.rotateBackLayer()}
        "Left" ->  {cube:Cube<Char> -> cube.rotateLeftLayer()}
        "Right" ->  {cube:Cube<Char> -> cube.rotateRightLayer()}
        "Top" ->  {cube:Cube<Char> -> cube.rotateTopLayer()}
        "Bottom" ->  {cube:Cube<Char> -> cube.rotateBottomLayer()}
        else ->  {cube:Cube<Char> -> cube.rotateTopLayer()}
    }
    val newCubeAsList =  if (direction == "CW") rotateLayer(cubeAsList)  else rotateLayer(rotateLayer(rotateLayer(cubeAsList)))
    return newCubeAsList.map{it.joinToString ("")}

}
