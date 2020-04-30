
typealias Cube<T> = List<List<T>>
typealias Side<T> = List<T>

data class Position(val x:Int, val y:Int, private val width:Int = 3) {
    val toIndex get() = x + (y * width)
    val rotated get() = Position( width - 1 - y , x)
}
fun newPosition(ndx:Int, width:Int = 3) = Position(ndx % width, ndx / width)

val <T>Cube<T>.frontSide get() = get(0)
val <T>Cube<T>.backSide get() = get(1)
val <T>Cube<T>.leftSide get() = get(2)
val <T>Cube<T>.rightSide get() = get(3)
val <T>Cube<T>.topSide get() = get(4)
val <T>Cube<T>.bottomSide get() = get(5)

fun <T:Any>Side<T>.row(n:Int) = mapIndexedNotNull{ index,c -> if ((index / 3) == n) c else null }
fun <T:Any>Side<T>.column(n:Int) = mapIndexedNotNull{ index, c -> if (index % 3 == n) c else null }

infix fun <T>Side<T>.addColumn(other:Side<T>):Side<T> {
    val chunks = chunked(size/3)
    val otherChunks = other.chunked(other.size/3)
    return chunks[0] + otherChunks[0] + chunks[1] + otherChunks[1] + chunks[2] + otherChunks[2]
}

fun <T>Side<T>.rotateFaceRight(): Side<T> {
    return  mapIndexed{index, char -> Pair(newPosition(index).rotated, char) }
        .sortedBy { it.first.toIndex }
        .map{it.second}
}

fun <T:Any>Cube<T>.rotateTopLayer():Cube<T> {
    val top = topSide.rotateFaceRight()
    val front = rightSide.row(0) + frontSide.row(1) + frontSide.row(2)
    val left =  frontSide.row(0) + leftSide.row(1) + leftSide.row(2)
    val back =  leftSide.row(0) + backSide.row(1) + backSide.row(2)
    val right = backSide.row(0) + rightSide.row(1) + rightSide.row(2)

    return listOf(front, back, left, right, top, bottomSide)
}

fun <T:Any>Cube<T>.rotateBottomLayer():Cube<T> {
    val bottom = bottomSide.rotateFaceRight()
    val front = frontSide.row(0) + frontSide.row(1) + leftSide.row(2)
    val right = rightSide.row(0) + rightSide.row(1) + frontSide.row(2)
    val back = backSide.row(0) + backSide.row(1) + rightSide.row(2)
    val left = leftSide.row(0) + leftSide.row(1) + backSide.row(2)

    return listOf(front, back, left, right, topSide, bottom)
}

fun <T:Any>Cube<T>.rotateLeftLayer():Cube<T> {
    val left = leftSide.rotateFaceRight()
    val front = topSide.column(0) addColumn frontSide.column(1) addColumn frontSide.column(2)
    val back = backSide.column(0) addColumn backSide.column(1) addColumn bottomSide.column(2)
    val top = backSide.column(2).reversed() addColumn topSide.column(1) addColumn topSide.column(2)
    val bottom = bottomSide.column(0) addColumn bottomSide.column(1) addColumn frontSide.column(0).reversed()

    return listOf(front, back, left, rightSide, top, bottom)
}

fun <T:Any>Cube<T>.rotateRightLayer():Cube<T> {
    val right = rightSide.rotateFaceRight()
    val front = frontSide.column(0) addColumn frontSide.column(1) addColumn bottomSide.column(0).reversed()
    val back = topSide.column(2).reversed() addColumn backSide.column(1) addColumn backSide.column(2)
    val top = topSide.column(0) addColumn topSide.column(1) addColumn frontSide.column(2)
    val bottom = backSide.column(0) addColumn bottomSide.column(1) addColumn bottomSide.column(2)

    return listOf(front, back, leftSide, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateFrontLayer():Cube<T> {
    val front = frontSide.rotateFaceRight()
    val left = leftSide.column(0) addColumn leftSide.column(1) addColumn bottomSide.row(2).reversed()
    val right = topSide.row(2) addColumn rightSide.column(1) addColumn rightSide.column(2)
    val top = topSide.row(0) + topSide.row(1) + leftSide.column(2).reversed()
    val bottom = bottomSide.row(0) + bottomSide.row(1) + rightSide.column(0)

    return listOf(front, backSide, left, right, top, bottom)
}

fun <T:Any>Cube<T>.rotateBackLayer():Cube<T> {
    val back = backSide.rotateFaceRight()
    val left = topSide.row(0).reversed() addColumn leftSide.column(1) addColumn leftSide.column(2)
    val right = rightSide.column(0) addColumn rightSide.column(1) addColumn bottomSide.row(0)
    val top = rightSide.column(2) + topSide.row(1) + topSide.row(2)
    val bottom = leftSide.column(0).reversed() + bottomSide.row(1) + bottomSide.row(2)

    return listOf(frontSide, back, left, right, top,bottom)
}

val rotators:Map<String, Cube<Char>.()->Cube<Char>> = mapOf(
                "Front"  to Cube<Char>::rotateFrontLayer,
                "Back"   to Cube<Char>::rotateBackLayer,
                "Left"   to Cube<Char>::rotateLeftLayer,
                "Right"  to Cube<Char>::rotateRightLayer,
                "Top"    to Cube<Char>::rotateTopLayer,
                "Bottom" to Cube<Char>::rotateBottomLayer)

fun rotateCube(cube:List<String>, face:String, direction:String):List<String> {
    if (direction != "CW" && direction != "CCW") return cube
    val rotate = rotators[face] ?: return cube

    val cubeAsList = cube.map{it.toList()}
    val newCubeAsList =  if (direction == "CW") cubeAsList.rotate()  else cubeAsList.rotate().rotate().rotate()
    return  newCubeAsList.map{it.joinToString ("")}
}
