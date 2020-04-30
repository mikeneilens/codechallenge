import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    private val front = listOf( 1, 2, 3,
                                4, 5, 6,
                                7, 8, 9)
    private val back =  listOf(10,11,12,
                               13,14,15,
                               16,17,18)
    private val left =  listOf(19,20,21,
                               22,23,24,
                               25,26,27)
    private val right = listOf(28,29,30,
                               31,32,33,
                               34,35,36)
    private val top =   listOf(37,38,39,
                               40,41,42,
                               43,44,45)
    private val bottom =listOf(46,47,48,
                               49,50,51,
                               52,53,54)
    private val cube = listOf(front, back, left, right, top, bottom)

    @Test
    fun `new position creates a position correctly`() {
        assertEquals(Position(0,0), newPosition(0))
        assertEquals(Position(1,0), newPosition(1))
        assertEquals(Position(2,0), newPosition(2))
        assertEquals(Position(0,1), newPosition(3))
        assertEquals(Position(1,2), newPosition(7))
    }
    @Test
    fun `rotating a position creates a new position 90 degrees cw from the original`() {
        assertEquals(Position(2,0), Position(0,0).rotatedCW)
        assertEquals(Position(2,1), Position(1,0).rotatedCW)
        assertEquals(Position(2,2), Position(2,0).rotatedCW)
        assertEquals(Position(1,0), Position(0,1).rotatedCW)
        assertEquals(Position(1,1), Position(1,1).rotatedCW)
        assertEquals(Position(1,2), Position(2,1).rotatedCW)
    }

    @Test
    fun `rotating a side gives a side rotated by 90 degrees`() {
        val expectedResult = listOf( 7, 4, 1,
                                              8, 5, 2,
                                              9, 6, 3)
        assertEquals(expectedResult, front.rotateFaceCW())
    }
    @Test
    fun `rotating the top layer results in a cube with a top rotated 90 degrees and top row of front, left, back and right moved`() {
        val newFront = listOf(28,29,30,
                                        4, 5, 6,
                                        7, 8, 9)
        val newBack =  listOf(19,20,21,
                                       13,14,15,
                                       16,17,18)
        val newLeft =  listOf( 1, 2, 3,
                                       22,23,24,
                                       25,26,27)
        val newRight = listOf(10,11,12,
                                       31,32,33,
                                       34,35,36)
        val newTop =   listOf( 43,40,37,
                                        44,41,38,
                                        45,42,39)
        val newBottom = bottom
        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)

        assertEquals(expectedResult, cube.rotateTopLayerCW())
    }
    @Test
    fun `rotating the bottom layer results in a cube with a bottom rotated 90 degrees and bottom row of front, left, back and right moved`() {
        val newFront = listOf( 1, 2, 3,
                                        4, 5, 6,
                                       25,26,27)
        val newBack =  listOf(10,11,12,
                                       13,14,15,
                                       34,35,36)
        val newLeft =  listOf(19,20,21,
                                       22,23,24,
                                       16,17,18)
        val newRight = listOf(28,29,30,
                                       31,32,33,
                                        7, 8, 9)
        val newTop =   top
        val newBottom = listOf(52,49,46,
                                        53,50,47,
                                        54,51,48)
        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)
        assertEquals(expectedResult, cube.rotateBottomLayerCW())
    }
    @Test
    fun `rotating the left layer results in the left rotated 90 degrees with the left column of the top, front, bottom and back moved`() {
        val newLeft    = listOf(25,22,19,
                                         26,23,20,
                                         27,24,21)
        val newRight   = right
        val newFront   = listOf(37, 2, 3,
                                         40, 5, 6,
                                         43, 8, 9)
        val newBack    = listOf(10,11,48,
                                         13,14,51,
                                         16,17,54)
        val newTop     = listOf(18,38,39,
                                         15,41,42,
                                         12,44,45)
        val newBottom  = listOf(46,47, 7,
                                         49,50, 4,
                                         52,53, 1)

        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)

        assertEquals(expectedResult, cube.rotateLeftLayerCW())

    }

    @Test
    fun `rotating the right layer results in the right rotated 90 degrees with the right column of the top, front, bottom and back moved`() {
        val newLeft    = left
        val newRight   = listOf(34,31,28,
                                         35,32,29,
                                         36,33,30)
        val newFront   = listOf( 1, 2,52,
                                          4, 5,49,
                                          7, 8,46)
        val newBack    = listOf(45,11,12,
                                         42,14,15,
                                         39,17,18)
        val newTop     = listOf(37,38, 3,
                                         40,41, 6,
                                         43,44, 9)
        val newBottom  = listOf(10,47,48,
                                         13,50,51,
                                         16,53,54)
        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)
        assertEquals(expectedResult, cube.rotateRightLayerCW())

    }

    @Test
    fun `rotating the front layer results in the front rotated 90 degrees with the front column of the top, right, bottom and left moved`() {
        val newBack    = back
        val newFront   = listOf( 7, 4, 1,
                                          8, 5, 2,
                                          9, 6, 3)
        val newLeft   = listOf( 19,20,54,
                                         22,23,53,
                                         25,26,52)
        val newRight    = listOf(43,29,30,
                                          44,32,33,
                                          45,35,36)
        val newTop     = listOf(37,38,39,
                                         40,41,42,
                                         27,24,21)
        val newBottom  = listOf(46,47,48,
                                         49,50,51,
                                         28,31,34)
        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)
        assertEquals(expectedResult, cube.rotateFrontLayerCW())

    }
    @Test
    fun `rotating the back layer results in the back rotated 90 degrees with the back column of the top, left, bottom and right moved`() {
        val newFront    = front
        val newBack   = listOf(16,13,10,
                                        17,14,11,
                                        18,15,12)
        val newLeft   = listOf(39,20,21,
                                        38,23,24,
                                        37,26,27)
        val newRight  = listOf(28,29,46,
                                        31,32,47,
                                        34,35,48)
        val newTop     = listOf(30,33,36,
                                         40,41,42,
                                         43,44,45)
        val newBottom  = listOf(25,22,19,
                                         49,50,51,
                                         52,53,54)
        val expectedResult = listOf(newFront, newBack, newLeft, newRight, newTop, newBottom)
        assertEquals(expectedResult, cube.rotateBackLayerCW())

    }

    @Test
    fun `rotating the top of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Top","CW")
        val expectedResult = listOf("RRRGGGGGG","OOOYYYYYY","GGGOOOOOO","YYYRRRRRR","WWWWWWWWW","BBBBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the top of a cube CCW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Top","CCW")
        val expectedResult = listOf("OOOGGGGGG","RRRYYYYYY","YYYOOOOOO","GGGRRRRRR","WWWWWWWWW","BBBBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the bottom of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Bottom","CW")
        val expectedResult = listOf("GGGGGGOOO","YYYYYYRRR","OOOOOOYYY","RRRRRRGGG","WWWWWWWWW","BBBBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the left of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Left","CW")
        val expectedResult = listOf("WGGWGGWGG","YYBYYBYYB","OOOOOOOOO","RRRRRRRRR","YWWYWWYWW","BBGBBGBBG")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the right of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Right","CW")
        val expectedResult = listOf("GGBGGBGGB","WYYWYYWYY","OOOOOOOOO","RRRRRRRRR","WWGWWGWWG","YBBYBBYBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the front of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Front","CW")
        val expectedResult = listOf("GGGGGGGGG","YYYYYYYYY","OOBOOBOOB","WRRWRRWRR","WWWWWWOOO","BBBBBBRRR")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the back of a cube CW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Back","CW")
        val expectedResult = listOf("GGGGGGGGG", "YYYYYYYYY", "WOOWOOWOO", "RRBRRBRRB", "RRRWWWWWW", "OOOBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the back of a cube CCW in original state` () {
        val result = rotateCube(listOf("GGGGGGGGG","YYYYYYYYY","OOOOOOOOO","RRRRRRRRR","WWWWWWWWW","BBBBBBBBB"),"Back","CCW")
        val expectedResult = listOf("GGGGGGGGG", "YYYYYYYYY", "BOOBOOBOO", "RRWRRWRRW", "OOOWWWWWW", "RRRBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the back of a cube in an invalid direction` () {
        val result = rotateCube(listOf("GGGGGGGGG", "YYYYYYYYY", "BOOBOOBOO", "RRWRRWRRW", "OOOWWWWWW", "RRRBBBBBB"),"Back","X")
        val expectedResult = listOf("GGGGGGGGG", "YYYYYYYYY", "BOOBOOBOO", "RRWRRWRRW", "OOOWWWWWW", "RRRBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rotating the face of a cube using an invalid face` () {
        val result = rotateCube(listOf("GGGGGGGGG", "YYYYYYYYY", "BOOBOOBOO", "RRWRRWRRW", "OOOWWWWWW", "RRRBBBBBB"),"b","CW")
        val expectedResult = listOf("GGGGGGGGG", "YYYYYYYYY", "BOOBOOBOO", "RRWRRWRRW", "OOOWWWWWW", "RRRBBBBBB")
        assertEquals(expectedResult, result)
    }
    @Test
    fun `column returns the correct column from a cube face`() {
        assertEquals(listOf(1,4,7), front.column(0))
        assertEquals(listOf(2,5,8), front.column(1))
        assertEquals(listOf(3,6,9), front.column(2))
    }

    @Test
    fun `row returns the correct row from a cube face`() {
        assertEquals(listOf(1,2,3), front.row(0))
        assertEquals(listOf(4,5,6), front.row(1))
        assertEquals(listOf(7,8,9), front.row(2))
    }

    @Test
    fun `adding columns together`() {
        val col = listOf(1,
                                  2,
                                  3)
        val otherCol = listOf(4,
                                       5,
                                       6)
        val result = col addTo otherCol
        val expectedResult = listOf(1,4,
                                             2,5,
                                             3,6)
        assertEquals(expectedResult, result)

        val col2 = listOf(1,4,
                                   2,5,
                                   3,6)
        val otherCol2 =  listOf(7,
                                         8,
                                         9)
        val result2 = col2 addTo otherCol2
        val expectedResult2 = listOf(1,4,7,
                                              2,5,8,
                                              3,6,9)
        assertEquals(expectedResult2, result2)
    }
}
