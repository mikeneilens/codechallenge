const val FRONT = 0
const val BACK  = 1
const val LEFT = 2
const val RIGHT = 3
const val TOP = 4
const val BOTTOM = 5

fun Int.asX() = this % 3
fun Int.asY() = this / 3
fun Pair<Int, Int>.toInt() = first + second * 3

fun <T>List<T>.rotateFaceRight(): List<T> {
    return  mapIndexed{index, char -> Pair(Pair(2 - index.asY(), index.asX()), char) }
        .map{(position, char) -> Pair( position.toInt(), char ) }
        .sortedBy { it.first }
        .map{it.second}
}

fun <T>List<List<T>>.rotateTopLayer():List<List<T>> {
    val top = this[TOP].rotateFaceRight()
    val bottom = this[BOTTOM]
    val front = this[RIGHT].take(3) + this[FRONT].drop(3)
    val left = this[FRONT].take(3) + this[LEFT].drop(3)
    val back = this[LEFT].take(3) + this[BACK].drop(3)
    val right = this[BACK].take(3) + this[RIGHT].drop(3)

    return listOf(front, back, left, right, top,bottom)
}

fun <T>List<List<T>>.rotateBottomLayer():List<List<T>> {
    val top = this[TOP]
    val bottom = this[BOTTOM].rotateFaceRight()
    val front = this[FRONT].take(6) + this[LEFT].drop(6)
    val right = this[RIGHT].take(6) + this[FRONT].drop(6)
    val back = this[BACK].take(6) + this[RIGHT].drop(6)
    val left = this[LEFT].take(6) + this[BACK].drop(6)

    return listOf(front, back, left, right, top,bottom)
}

fun <T>List<List<T>>.rotateLeftLayer():List<List<T>> {
    val left = this[LEFT].rotateFaceRight()
    val right = this[RIGHT]
    val front = listOf(this[TOP][0], this[FRONT][1], this[FRONT][2],
                               this[TOP][3], this[FRONT][4], this[FRONT][5],
                               this[TOP][6], this[FRONT][7], this[FRONT][8])
    val back = listOf(this[BACK][0], this[BACK][1], this[BOTTOM][2],
                              this[BACK][3], this[BACK][4], this[BOTTOM][5],
                              this[BACK][6], this[BACK][7], this[BOTTOM][8])
    val top = listOf(this[BACK][8], this[TOP][1], this[TOP][2],
                             this[BACK][5], this[TOP][4], this[TOP][5],
                             this[BACK][2], this[TOP][7], this[TOP][8])
    val bottom = listOf(this[BOTTOM][0], this[BOTTOM][1], this[FRONT][6],
                                this[BOTTOM][3], this[BOTTOM][4], this[FRONT][3],
                                this[BOTTOM][6], this[BOTTOM][7], this[FRONT][0])

    return listOf(front, back, left, right, top,bottom)
}

fun <T>List<List<T>>.rotateRightLayer():List<List<T>> {
    val left = this[LEFT]
    val right = this[RIGHT].rotateFaceRight()
    val front = listOf(this[FRONT][0], this[FRONT][1], this[BOTTOM][6],
                               this[FRONT][3], this[FRONT][4], this[BOTTOM][3],
                               this[FRONT][6], this[FRONT][7], this[BOTTOM][0])
    val back = listOf(this[TOP][8], this[BACK][1], this[BACK][2],
                              this[TOP][5], this[BACK][4], this[BACK][5],
                              this[TOP][2], this[BACK][7], this[BACK][8])
    val top = listOf(this[TOP][0], this[TOP][1], this[FRONT][2],
                             this[TOP][3], this[TOP][4], this[FRONT][5],
                             this[TOP][6], this[TOP][7], this[FRONT][8])
    val bottom = listOf(this[BACK][0], this[BOTTOM][1], this[BOTTOM][2],
                                this[BACK][3], this[BOTTOM][4], this[BOTTOM][5],
                                this[BACK][6], this[BOTTOM][7], this[BOTTOM][8])

    return listOf(front, back, left, right, top,bottom)
}

fun <T>List<List<T>>.rotateFrontLayer():List<List<T>> {
    val back = this[BACK]
    val front = this[FRONT].rotateFaceRight()
    val left = listOf(this[LEFT][0], this[LEFT][1], this[BOTTOM][8],
                              this[LEFT][3], this[LEFT][4], this[BOTTOM][7],
                              this[LEFT][6], this[LEFT][7], this[BOTTOM][6])
    val right = listOf(this[TOP][6], this[RIGHT][1], this[RIGHT][2],
                               this[TOP][7], this[RIGHT][4], this[RIGHT][5],
                               this[TOP][8], this[RIGHT][7], this[RIGHT][8])
    val top = listOf(this[TOP][0], this[TOP][1], this[TOP][2],
                             this[TOP][3], this[TOP][4], this[TOP][5],
                             this[LEFT][8], this[LEFT][5], this[LEFT][2])
    val bottom = listOf(this[BOTTOM][0], this[BOTTOM][1], this[BOTTOM][2],
                                this[BOTTOM][3], this[BOTTOM][4], this[BOTTOM][5],
                                this[RIGHT][0], this[RIGHT][3], this[RIGHT][6])

    return listOf(front, back, left, right, top,bottom)
}

fun <T>List<List<T>>.rotateBackLayer():List<List<T>> {
    val back = this[BACK].rotateFaceRight()
    val front = this[FRONT]
    val left = listOf(this[TOP][2], this[LEFT][1], this[LEFT][2],
                              this[TOP][1], this[LEFT][4], this[LEFT][5],
                              this[TOP][0], this[LEFT][7], this[LEFT][8])
    val right = listOf(this[RIGHT][0], this[RIGHT][1], this[BOTTOM][0],
                               this[RIGHT][3], this[RIGHT][4], this[BOTTOM][1],
                               this[RIGHT][6], this[RIGHT][7], this[BOTTOM][2])
    val top = listOf(this[RIGHT][2], this[RIGHT][5], this[RIGHT][8],
                             this[TOP][3], this[TOP][4], this[TOP][5],
                             this[TOP][6], this[TOP][7], this[TOP][8])
    val bottom = listOf(this[LEFT][6], this[LEFT][3], this[LEFT][0],
                                this[BOTTOM][3], this[BOTTOM][4], this[BOTTOM][5],
                                this[BOTTOM][6], this[BOTTOM][7], this[BOTTOM][8])

    return listOf(front, back, left, right, top,bottom)
}

fun rotateCube(cube:List<String>, face:String, direction:String):List<String> {

    val cubeAsList = cube.map{it.toList()}
    val rotator = when (face) {
        "Front" ->  {cube:List<List<Char>> -> cube.rotateFrontLayer()}
        "Back" ->  {cube:List<List<Char>> -> cube.rotateBackLayer()}
        "Left" ->  {cube:List<List<Char>> -> cube.rotateLeftLayer()}
        "Right" ->  {cube:List<List<Char>> -> cube.rotateRightLayer()}
        "Top" ->  {cube:List<List<Char>> -> cube.rotateTopLayer()}
        "Bottom" ->  {cube:List<List<Char>> -> cube.rotateBottomLayer()}
        else ->  {cube:List<List<Char>> -> cube.rotateTopLayer()}
    }
    val newCubeAsList =  if (direction == "CW") rotator(cubeAsList)  else rotator(rotator(rotator(cubeAsList)))
    return newCubeAsList.map{it.joinToString ("")}

}
