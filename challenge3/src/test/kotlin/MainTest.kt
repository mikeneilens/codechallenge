import junit.framework.TestCase
import org.junit.Test

class MainTest {
    @Test
    fun `Test convert string to MapTile` () {
        TestCase.assertEquals(MapTile.Person, MapTile.fromString("p"))
        TestCase.assertEquals(MapTile.Wall, MapTile.fromString("#"))
        TestCase.assertEquals(MapTile.Block, MapTile.fromString("b"))
        TestCase.assertEquals(MapTile.Storage, MapTile.fromString("*"))
        TestCase.assertEquals(MapTile.PersonOnStorage, MapTile.fromString("P"))
        TestCase.assertEquals(MapTile.BlockOnStorage, MapTile.fromString("B"))
        TestCase.assertEquals(MapTile.Empty, MapTile.fromString(" "))
        TestCase.assertEquals(MapTile.Empty, MapTile.fromString("any old data"))
    }
    @Test
    fun `Test MutableMap fromString`() {
        val map1 = mutableMapOf<Position, MapTile>()
        map1.fromStringForRow(1,"p")
        TestCase.assertEquals(map1[Position(1,0)],MapTile.Person)
        map1.fromStringForRow(1,"# pb*PB")
        TestCase.assertEquals(map1[Position(1,0)],MapTile.Wall)
        TestCase.assertEquals(map1[Position(1,1)],MapTile.Empty)
        TestCase.assertEquals(map1[Position(1,2)],MapTile.Person)
        TestCase.assertEquals(map1[Position(1,3)],MapTile.Block)
        TestCase.assertEquals(map1[Position(1,4)],MapTile.Storage)
        TestCase.assertEquals(map1[Position(1,5)],MapTile.PersonOnStorage)
        TestCase.assertEquals(map1[Position(1,6)],MapTile.BlockOnStorage)
    }
    @Test
    fun `Test MutableMap toString`() {
        val map1 = mutableMapOf<Position, MapTile>()
        map1[Position(1,0)] = MapTile.Person
        map1[Position(1,1)] = MapTile.PersonOnStorage
        map1[Position(1,2)] = MapTile.Block
        map1[Position(1,3)] = MapTile.BlockOnStorage
        map1[Position(1,4)] = MapTile.Wall
        map1[Position(1,5)] = MapTile.Storage
        map1[Position(1,6)] = MapTile.Empty
        TestCase.assertEquals("pPbB#* ",map1.toStringForRow(1))
        val map2 = mutableMapOf<Position, MapTile>()
        map2[Position(1,1)] = MapTile.Person
        map2[Position(1,3)] = MapTile.PersonOnStorage
        map2[Position(1,2)] = MapTile.Block
        map2[Position(1,4)] = MapTile.BlockOnStorage
        map2[Position(1,6)] = MapTile.Wall
        map2[Position(1,5)] = MapTile.Storage
        map2[Position(1,0)] = MapTile.Empty
        TestCase.assertEquals(" pbPB*#",map2.toStringForRow(1))
    }
    @Test
    fun `Test List of String toMap`() {
        val listOfStrings = listOf("# p #", "B*Ppb")
        val map = listOfStrings.toMap()
        TestCase.assertEquals(map[Position(0,0)],MapTile.Wall)
        TestCase.assertEquals(map[Position(0,1)],MapTile.Empty)
        TestCase.assertEquals(map[Position(0,2)],MapTile.Person)
        TestCase.assertEquals(map[Position(0,3)],MapTile.Empty)
        TestCase.assertEquals(map[Position(0,4)],MapTile.Wall)

        TestCase.assertEquals(map[Position(1,0)],MapTile.BlockOnStorage)
        TestCase.assertEquals(map[Position(1,1)],MapTile.Storage)
        TestCase.assertEquals(map[Position(1,2)],MapTile.PersonOnStorage)
        TestCase.assertEquals(map[Position(1,3)],MapTile.Person)
        TestCase.assertEquals(map[Position(1,4)],MapTile.Block)
    }
    @Test
    fun `Test Map to ListOfString`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Person
        map[Position(1,1)] = MapTile.PersonOnStorage
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.BlockOnStorage
        map[Position(1,4)] = MapTile.Wall
        map[Position(1,5)] = MapTile.Storage
        map[Position(1,6)] = MapTile.Empty
        map[Position(2,0)] = MapTile.Empty
        map[Position(2,1)] = MapTile.Person
        map[Position(2,2)] = MapTile.Block
        map[Position(2,3)] = MapTile.PersonOnStorage
        map[Position(2,4)] = MapTile.BlockOnStorage
        map[Position(2,5)] = MapTile.Storage
        map[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(listOf("pPbB#* "," pbPB*#"),map.toListOfString())
    }
    @Test
    fun `Test position of person in Map of Positions and Map Tiles` () {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Block
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.BlockOnStorage
        map[Position(1,4)] = MapTile.Wall
        map[Position(1,5)] = MapTile.Storage
        map[Position(1,6)] = MapTile.Empty
        map[Position(2,0)] = MapTile.Empty
        map[Position(2,1)] = MapTile.Person
        map[Position(2,2)] = MapTile.Block
        map[Position(2,3)] = MapTile.Block
        map[Position(2,4)] = MapTile.BlockOnStorage
        map[Position(2,5)] = MapTile.Storage
        map[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(Position(2,1),map.positionOfPerson())
        val map2 = mutableMapOf<Position, MapTile>()
        map2[Position(1,0)] = MapTile.Wall
        map2[Position(1,1)] = MapTile.Block
        map2[Position(1,2)] = MapTile.Block
        map2[Position(1,3)] = MapTile.BlockOnStorage
        map2[Position(1,4)] = MapTile.PersonOnStorage
        map2[Position(1,5)] = MapTile.Storage
        map2[Position(1,6)] = MapTile.Empty
        map2[Position(2,0)] = MapTile.Empty
        map2[Position(2,1)] = MapTile.Empty
        map2[Position(2,2)] = MapTile.Block
        map2[Position(2,3)] = MapTile.Block
        map2[Position(2,4)] = MapTile.BlockOnStorage
        map2[Position(2,5)] = MapTile.Storage
        map2[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(Position(1,4),map2.positionOfPerson())
    }

    @Test
    fun `Test adding positions together`() {
        TestCase.assertEquals(Position(2,3),Position(1,1) + Position(1,2))
        TestCase.assertEquals(Position(0,-1),Position(1,1) + Position(-1,-2))
        TestCase.assertEquals(Position(4,6),Position(1,2) + Position(3,4))
    }

    @Test
    fun `Test canMoveOnto a square if it is empty or is storage and cant move onto any other squares`(){
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Person
        TestCase.assertEquals(false,map.canMoveOnto(Position(1,0)) )

        map[Position(1,1)] = MapTile.PersonOnStorage
        TestCase.assertEquals(false,map.canMoveOnto(Position(1,1)) )

        map[Position(1,2)] = MapTile.Block
        TestCase.assertEquals(false,map.canMoveOnto(Position(1,2)) )

        map[Position(1,3)] = MapTile.BlockOnStorage
        TestCase.assertEquals(false,map.canMoveOnto(Position(1,3)))

        map[Position(1,4)] = MapTile.Wall
        TestCase.assertEquals(false,map.canMoveOnto(Position(1,4)) )

        map[Position(1,5)] = MapTile.Storage
        TestCase.assertEquals(true,map.canMoveOnto(Position(1,5)) )

        map[Position(1,6)] = MapTile.Empty
        TestCase.assertEquals(true,map.canMoveOnto(Position(1,6)) )

    }
    @Test
    fun `Test if position on map contains a block`(){
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Person
        TestCase.assertEquals(false,map.containsBlock(Position(1,0)) )

        map[Position(1,2)] = MapTile.Block
        TestCase.assertEquals(true,map.containsBlock(Position(1,2)) )

        map[Position(1,3)] = MapTile.BlockOnStorage
        TestCase.assertEquals(true,map.containsBlock(Position(1,3)))

        map[Position(1,4)] = MapTile.Wall
        TestCase.assertEquals(false,map.containsBlock(Position(1,4)) )

    }
    @Test
    fun `Test moving a person to an empty square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.movePerson(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, map[Position(1,3)])
    }

    @Test
    fun `Test moving a person to an storage square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Storage
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.movePerson(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.PersonOnStorage, map[Position(1,3)])
    }

    @Test
    fun `Test moving a person form a storage square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.PersonOnStorage
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.movePerson(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Storage, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, map[Position(1,3)])
    }

    @Test
    fun `Test moving a block to an empty square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.moveBlock(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, map[Position(1,3)])
    }

    @Test
    fun `Test moving a block to an storage square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.Storage
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.moveBlock(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.BlockOnStorage, map[Position(1,3)])
    }

    @Test
    fun `Test moving a block from a storage square on the map`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.BlockOnStorage
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        map.moveBlock(positionToMoveTo,positionToMoveFrom)
        TestCase.assertEquals(MapTile.Storage, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, map[Position(1,3)])
    }

    @Test
    fun `Test moving a person left`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        map.movePerson(Direction.L)
        TestCase.assertEquals(MapTile.Wall, map[Position(1,0)])
        TestCase.assertEquals(MapTile.Person, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,4)])
    }
    @Test
    fun `Test moving a person right`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        map.movePerson(Direction.R)
        TestCase.assertEquals(MapTile.Wall, map[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, map[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,4)])
    }

    @Test
    fun `Test moving a person up`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(0,0)] = MapTile.Wall
        map[Position(0,1)] = MapTile.Empty
        map[Position(0,2)] = MapTile.Storage
        map[Position(0,3)] = MapTile.Empty
        map[Position(0,4)] = MapTile.Wall
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        map.movePerson(Direction.U)
        TestCase.assertEquals(MapTile.Wall, map[Position(0,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(0,1)])
        TestCase.assertEquals(MapTile.PersonOnStorage, map[Position(0,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(0,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(0,4)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,4)])
    }
    @Test
    fun `Test moving a person down`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(0,0)] = MapTile.Wall
        map[Position(0,1)] = MapTile.Empty
        map[Position(0,2)] = MapTile.Storage
        map[Position(0,3)] = MapTile.Empty
        map[Position(0,4)] = MapTile.Wall
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Empty
        map[Position(1,2)] = MapTile.Person
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall
        map[Position(2,0)] = MapTile.Wall
        map[Position(2,1)] = MapTile.Empty
        map[Position(2,2)] = MapTile.Empty
        map[Position(2,3)] = MapTile.Empty
        map[Position(2,4)] = MapTile.Wall
        map.movePerson(Direction.D)
        TestCase.assertEquals(MapTile.Wall, map[Position(0,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(0,1)])
        TestCase.assertEquals(MapTile.Storage, map[Position(0,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(0,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(0,4)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(1,4)])
        TestCase.assertEquals(MapTile.Wall, map[Position(2,0)])
        TestCase.assertEquals(MapTile.Empty, map[Position(2,1)])
        TestCase.assertEquals(MapTile.Person, map[Position(2,2)])
        TestCase.assertEquals(MapTile.Empty, map[Position(2,3)])
        TestCase.assertEquals(MapTile.Wall, map[Position(2,4)])
    }

    @Test
    fun `Test moving a person into a block that can move`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Person
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.Empty
        map[Position(1,4)] = MapTile.Wall

        map.movePerson(Direction.R)
        TestCase.assertEquals(MapTile.Empty, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Person, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, map[Position(1,3)])
    }

    @Test
    fun `Test moving a person into a block that cannot move`() {
        val map = mutableMapOf<Position, MapTile>()
        map[Position(1,0)] = MapTile.Wall
        map[Position(1,1)] = MapTile.Person
        map[Position(1,2)] = MapTile.Block
        map[Position(1,3)] = MapTile.Block
        map[Position(1,4)] = MapTile.Wall

        map.movePerson(Direction.R)
        TestCase.assertEquals(MapTile.Person, map[Position(1,1)])
        TestCase.assertEquals(MapTile.Block, map[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, map[Position(1,3)])
    }

    @Test
    fun `Test processSokabanMove with simple move right` () {
        val inputList = listOf(
            "#############",
            "#p        * #",
            "#     b  b  #",
            "# *         #",
            "#############")
        val afterMoveRight = listOf(
            "#############",
            "# p       * #",
            "#     b  b  #",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveRight, processSokobanMove(inputList,"R"))
    }
    @Test
    fun `Test processSokabanMove with simple move down` () {
        val inputList = listOf(
            "#############",
            "#p        * #",
            "#     b  b  #",
            "# *         #",
            "#############")
        val afterMoveDown = listOf(
            "#############",
            "#         * #",
            "#p    b  b  #",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveDown, processSokobanMove(inputList,"D"))
    }
    @Test
    fun `Test processSokabanMove move block` () {
        val inputList = listOf(
            "#############",
            "#         * #",
            "#     b  b  #",
            "# *   p     #",
            "#############")
        val afterMoveUp = listOf(
            "#############",
            "#     b   * #",
            "#     p  b  #",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveUp, processSokobanMove(inputList,"U"))
    }
    @Test
    fun `Test processSokabanMove move block onto storage` () {
        val inputList = listOf(
            "#############",
            "#         * #",
            "#    *bp b  #",
            "# *         #",
            "#############")
        val afterMoveLeft = listOf(
            "#############",
            "#         * #",
            "#    Bp  b  #",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveLeft, processSokobanMove(inputList,"L"))
    }
    @Test
    fun `Test processSokabanMove move block off storage` () {
        val inputList = listOf(
            "#############",
            "#         * #",
            "#    Bp  b  #",
            "# *         #",
            "#############")
        val afterMoveLeft = listOf(
            "#############",
            "#         * #",
            "#   bP   b  #",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveLeft, processSokobanMove(inputList,"L"))
    }
    @Test
    fun `Test processSokabanMove cannot move person off the board` () {
        val inputList = listOf(
            "#############",
            "#         * #",
            "#    *   b  p",
            "# *         #",
            "#############")
        val afterMoveRight = listOf(
            "#############",
            "#         * #",
            "#    *   b  p",
            "# *         #",
            "#############")
        TestCase.assertEquals(afterMoveRight, processSokobanMove(inputList,"R"))
    }
}