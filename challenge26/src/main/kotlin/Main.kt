
typealias Cube<T> = List<List<T>>
typealias Side<T> = List<T>

data class Position(val x:Int, val y:Int, private val width:Int = 3) {
    val toIndex get() = x + (y * width)
    val rotatedCW get() = Position( width - 1 - y , x)
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

infix fun <T>Side<T>.addTo(other:Side<T>):Side<T> {
    val chunks = chunked(size/3)
    val otherChunks = other.chunked(other.size/3)
    return chunks[0] + otherChunks[0] + chunks[1] + otherChunks[1] + chunks[2] + otherChunks[2]
}

fun <T>Side<T>.rotateFaceCW(): Side<T> {
    return  mapIndexed{index, char -> Pair(newPosition(index).rotatedCW, char) }
        .sortedBy { it.first.toIndex }
        .map{it.second}
}

fun <T:Any>Cube<T>.rotateTopLayerCW():Cube<T> {
    val top = topSide.rotateFaceCW()
    val front = rightSide.row(0) + frontSide.row(1) + frontSide.row(2)
    val left =  frontSide.row(0) + leftSide.row(1) + leftSide.row(2)
    val back =  leftSide.row(0) + backSide.row(1) + backSide.row(2)
    val right = backSide.row(0) + rightSide.row(1) + rightSide.row(2)

    return listOf(front, back, left, right, top, bottomSide)
}

fun <T:Any>Cube<T>.rotateBottomLayerCW():Cube<T> {
    val bottom = bottomSide.rotateFaceCW()
    val front = frontSide.row(0) + frontSide.row(1) + leftSide.row(2)
    val right = rightSide.row(0) + rightSide.row(1) + frontSide.row(2)
    val back = backSide.row(0) + backSide.row(1) + rightSide.row(2)
    val left = leftSide.row(0) + leftSide.row(1) + backSide.row(2)

    return listOf(front, back, left, right, topSide, bottom)
}

fun <T:Any>Cube<T>.rotateLeftLayerCW():Cube<T> {
    val left = leftSide.rotateFaceCW()
    val front = topSide.column(0) addTo frontSide.column(1) addTo frontSide.column(2)
    val back = backSide.column(0) addTo backSide.column(1) addTo bottomSide.column(2)
    val top = backSide.column(2).reversed() addTo topSide.column(1) addTo topSide.column(2)
    val bottom = bottomSide.column(0) addTo bottomSide.column(1) addTo frontSide.column(0).reversed()

    return listOf(front, back, left, rightSide, top, bottom)
}

fun <T:Any>Cube<T>.rotateRightLayerCW():Cube<T> {
    val right = rightSide.rotateFaceCW()
    val front = frontSide.column(0) addTo frontSide.column(1) addTo bottomSide.column(0).reversed()
    val back = topSide.column(2).reversed() addTo backSide.column(1) addTo backSide.column(2)
    val top = topSide.column(0) addTo topSide.column(1) addTo frontSide.column(2)
    val bottom = backSide.column(0) addTo bottomSide.column(1) addTo bottomSide.column(2)

    return listOf(front, back, leftSide, right, top,bottom)
}

fun <T:Any>Cube<T>.rotateFrontLayerCW():Cube<T> {
    val front = frontSide.rotateFaceCW()
    val left = leftSide.column(0) addTo leftSide.column(1) addTo bottomSide.row(2).reversed()
    val right = topSide.row(2) addTo rightSide.column(1) addTo rightSide.column(2)
    val top = topSide.row(0) + topSide.row(1) + leftSide.column(2).reversed()
    val bottom = bottomSide.row(0) + bottomSide.row(1) + rightSide.column(0)

    return listOf(front, backSide, left, right, top, bottom)
}

fun <T:Any>Cube<T>.rotateBackLayerCW():Cube<T> {
    val back = backSide.rotateFaceCW()
    val left = topSide.row(0).reversed() addTo leftSide.column(1) addTo leftSide.column(2)
    val right = rightSide.column(0) addTo rightSide.column(1) addTo bottomSide.row(0)
    val top = rightSide.column(2) + topSide.row(1) + topSide.row(2)
    val bottom = leftSide.column(0).reversed() + bottomSide.row(1) + bottomSide.row(2)

    return listOf(frontSide, back, left, right, top,bottom)
}

val rotators:Map<String, Cube<Char>.()->Cube<Char>> = mapOf(
                "Front"  to Cube<Char>::rotateFrontLayerCW,
                "Back"   to Cube<Char>::rotateBackLayerCW,
                "Left"   to Cube<Char>::rotateLeftLayerCW,
                "Right"  to Cube<Char>::rotateRightLayerCW,
                "Top"    to Cube<Char>::rotateTopLayerCW,
                "Bottom" to Cube<Char>::rotateBottomLayerCW)

fun rotateCube(cube:List<String>, face:String, direction:String):List<String> {
    if (direction != "CW" && direction != "CCW") return cube
    val rotateClockwise = rotators[face] ?: return cube

    val cubeAsList = cube.map{it.toList()}
    val newCubeAsList =  if (direction == "CW") cubeAsList.rotateClockwise()  else cubeAsList.rotateClockwise().rotateClockwise().rotateClockwise()
    return  newCubeAsList.map{it.joinToString ("")}
}
