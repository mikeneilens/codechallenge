import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TestBonus {

    val samplePuzzle =
    "AZUBGCICKLNPKM" +
    "QRADLEIFHTAEHD" +
    "BTTCJMAHSKLEMG" +
    "HLMAYLESBURYGO" +
    "BFULHAMYZAHVSJ" +
    "ZRNIIBMVJKVFOL" +
    "TRUVSIEINFSNKJ" +
    "HRBARBICANEMBY" +
    "QSOMTTVRCTGMEQ" +
    "NTSPGILLINGHAM" +
    "YLIYWWATERSIDE" +
    "HBDPIELXUGOJRZ" +
    "GPRDOCNUAMVWGP" +
    "AMPTHILLITUZOY"
    val sampleClues = listOf("GILLINGHAM", "WATERSIDE", "BATH", "NEWPORT", "AYLESBURY", "MELKSHAM", "AMPTHILL", "FULHAM", "HEATHFIELD", "BARBICAN")

    @Test
    fun `String letterAt returns letter at a position in the string`() {
        val string =
                            "ABCDEFGHIJKLMN" +
                            "NMLKJIHGFEDCBA" +
                            "OPQRSTUVWXYZAB"
        assertEquals("A", string.letterAt(Position(0,0)))
        assertEquals("E", string.letterAt(Position(4,0)))
        assertEquals("N", string.letterAt(Position(13,0)))
        assertEquals("N", string.letterAt(Position(0,1)))
        assertEquals("J", string.letterAt(Position(4,1)))
        assertEquals("A", string.letterAt(Position(13,1)))
        assertEquals("O", string.letterAt(Position(0,2)))
        assertEquals("S", string.letterAt(Position(4,2)))
        assertEquals("B", string.letterAt(Position(13,2)))

    }
    @Test
    fun `String letterAt returns empty string if position is outside of permitted range`() {
        assertEquals("",samplePuzzle.letterAt(Position(0,14)))
        assertEquals("",samplePuzzle.letterAt(Position(-1,13)))
        assertEquals("",samplePuzzle.letterAt(Position(0,14)))
        assertEquals("",samplePuzzle.letterAt(Position(0,-1)))
    }

    @Test
    fun `String textAt returns an empty string in the main string for a given position, direction and length when length is 0`() {
        val result = samplePuzzle.textAt(Position(0,0), Direction.HorizontalRight, 0)
        assertEquals("", result)
    }
    @Test
    fun `String textAt returns the string in the main string for a given position, direction and length`() {
        val watersideResult = samplePuzzle.textAt(Position(5,10), Direction.HorizontalRight, 9)
        assertEquals("WATERSIDE", watersideResult)
        val melkshamResult = samplePuzzle.textAt(Position(12,2), Direction.HorizontalLeft, 8)
        assertEquals("MELKSHAM", melkshamResult)
    }
    @Test
    fun `String textAt returns a one character string in the main string for a given position, direction and length when the position is at an edge and the direction is towards the edge`() {
        val result = samplePuzzle.textAt(Position(13,0),Direction.HorizontalRight,5)
        assertEquals("M", result)
    }
    @Test
    fun `String textAt returns a truncated string in the main string for a given position, direction and length when the position is close to an edge and the direction is towards the edge`() {
        val result = samplePuzzle.textAt(Position(10,0),Direction.HorizontalRight,5)
        assertEquals("NPKM", result)
    }
    @Test
    fun `wordFound returns true if a word is foud the main string for a given position direction`() {
        val resultHeathField = "HEATHFIELD".wordFound(samplePuzzle, Position(12,1),Direction.HorizontalLeft)
        assertTrue(resultHeathField)
        val resultBath = "BATH".wordFound(samplePuzzle, Position(3,0),Direction.DiagonalDownLeft)
        assertTrue(resultBath)
    }

    @Test
    fun ` positionOf returns the position and orientation of some text` () {
        val positionAndDirection1 = samplePuzzle.positionOf("GILLINGHAM")
        assertEquals(Position(4,9), positionAndDirection1?.first)
        assertEquals(Direction.HorizontalRight, positionAndDirection1?.second)

        val positionAndDirection2 = samplePuzzle.positionOf("BATH")
        assertEquals(Position(3,0), positionAndDirection2?.first)
        assertEquals(Direction.DiagonalDownLeft, positionAndDirection2?.second)

        val positionAndDirection3 = samplePuzzle.positionOf("BARBICAN")
        assertEquals(Position(2,7), positionAndDirection3?.first)
        assertEquals(Direction.HorizontalRight, positionAndDirection3?.second)
    }
    @Test
    fun ` positionOf returns the null if text isnt found` () {
        val positionAndDirection1 = samplePuzzle.positionOf("HILLINGHAM")
        assertEquals(null, positionAndDirection1)
    }

    @Test
    fun ` findAllPositions returns a list of positions and directions containing 10 items` () {
        val result = samplePuzzle.findPositionOfEachClue(sampleClues)
        assertEquals(10, result.size)
        result.forEach { println(it) }
    }

}