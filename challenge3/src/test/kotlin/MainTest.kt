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
        val gameMap1 = newGameMap()
        gameMap1.fromStringForRow(1,"p")
        TestCase.assertEquals(gameMap1[Position(1,0)],MapTile.Person)
        gameMap1.fromStringForRow(1,"# pb*PB")
        TestCase.assertEquals(gameMap1[Position(1,0)],MapTile.Wall)
        TestCase.assertEquals(gameMap1[Position(1,1)],MapTile.Empty)
        TestCase.assertEquals(gameMap1[Position(1,2)],MapTile.Person)
        TestCase.assertEquals(gameMap1[Position(1,3)],MapTile.Block)
        TestCase.assertEquals(gameMap1[Position(1,4)],MapTile.Storage)
        TestCase.assertEquals(gameMap1[Position(1,5)],MapTile.PersonOnStorage)
        TestCase.assertEquals(gameMap1[Position(1,6)],MapTile.BlockOnStorage)
    }
    @Test
    fun `Test MutableMap toString`() {
        val gameMap1 = newGameMap()
        gameMap1[Position(1,0)] = MapTile.Person
        gameMap1[Position(1,1)] = MapTile.PersonOnStorage
        gameMap1[Position(1,2)] = MapTile.Block
        gameMap1[Position(1,3)] = MapTile.BlockOnStorage
        gameMap1[Position(1,4)] = MapTile.Wall
        gameMap1[Position(1,5)] = MapTile.Storage
        gameMap1[Position(1,6)] = MapTile.Empty
        TestCase.assertEquals("pPbB#* ",gameMap1.toStringForRow(1))
        val gameMap2 = newGameMap()
        gameMap2[Position(1,1)] = MapTile.Person
        gameMap2[Position(1,3)] = MapTile.PersonOnStorage
        gameMap2[Position(1,2)] = MapTile.Block
        gameMap2[Position(1,4)] = MapTile.BlockOnStorage
        gameMap2[Position(1,6)] = MapTile.Wall
        gameMap2[Position(1,5)] = MapTile.Storage
        gameMap2[Position(1,0)] = MapTile.Empty
        TestCase.assertEquals(" pbPB*#",gameMap2.toStringForRow(1))
    }
    @Test
    fun `Test List of String toMap`() {
        val listOfStrings = listOf("# p #", "B*Ppb")
        val gameMap = listOfStrings.toGameMap()
        TestCase.assertEquals(gameMap[Position(0,0)],MapTile.Wall)
        TestCase.assertEquals(gameMap[Position(0,1)],MapTile.Empty)
        TestCase.assertEquals(gameMap[Position(0,2)],MapTile.Person)
        TestCase.assertEquals(gameMap[Position(0,3)],MapTile.Empty)
        TestCase.assertEquals(gameMap[Position(0,4)],MapTile.Wall)

        TestCase.assertEquals(gameMap[Position(1,0)],MapTile.BlockOnStorage)
        TestCase.assertEquals(gameMap[Position(1,1)],MapTile.Storage)
        TestCase.assertEquals(gameMap[Position(1,2)],MapTile.PersonOnStorage)
        TestCase.assertEquals(gameMap[Position(1,3)],MapTile.Person)
        TestCase.assertEquals(gameMap[Position(1,4)],MapTile.Block)
    }
    @Test
    fun `Test Map to ListOfString`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Person
        gameMap[Position(1,1)] = MapTile.PersonOnStorage
        gameMap[Position(1,2)] = MapTile.Block
        gameMap[Position(1,3)] = MapTile.BlockOnStorage
        gameMap[Position(1,4)] = MapTile.Wall
        gameMap[Position(1,5)] = MapTile.Storage
        gameMap[Position(1,6)] = MapTile.Empty
        gameMap[Position(2,0)] = MapTile.Empty
        gameMap[Position(2,1)] = MapTile.Person
        gameMap[Position(2,2)] = MapTile.Block
        gameMap[Position(2,3)] = MapTile.PersonOnStorage
        gameMap[Position(2,4)] = MapTile.BlockOnStorage
        gameMap[Position(2,5)] = MapTile.Storage
        gameMap[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(listOf("pPbB#* "," pbPB*#"),gameMap.toListOfString())
    }
    @Test
    fun `Test position of person in Map of Positions and Map Tiles` () {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Block
        gameMap[Position(1,2)] = MapTile.Block
        gameMap[Position(1,3)] = MapTile.BlockOnStorage
        gameMap[Position(1,4)] = MapTile.Wall
        gameMap[Position(1,5)] = MapTile.Storage
        gameMap[Position(1,6)] = MapTile.Empty
        gameMap[Position(2,0)] = MapTile.Empty
        gameMap[Position(2,1)] = MapTile.Person
        gameMap[Position(2,2)] = MapTile.Block
        gameMap[Position(2,3)] = MapTile.Block
        gameMap[Position(2,4)] = MapTile.BlockOnStorage
        gameMap[Position(2,5)] = MapTile.Storage
        gameMap[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(Position(2,1),gameMap.positionOfPerson())
        val gameMap2 = newGameMap()
        gameMap2[Position(1,0)] = MapTile.Wall
        gameMap2[Position(1,1)] = MapTile.Block
        gameMap2[Position(1,2)] = MapTile.Block
        gameMap2[Position(1,3)] = MapTile.BlockOnStorage
        gameMap2[Position(1,4)] = MapTile.PersonOnStorage
        gameMap2[Position(1,5)] = MapTile.Storage
        gameMap2[Position(1,6)] = MapTile.Empty
        gameMap2[Position(2,0)] = MapTile.Empty
        gameMap2[Position(2,1)] = MapTile.Empty
        gameMap2[Position(2,2)] = MapTile.Block
        gameMap2[Position(2,3)] = MapTile.Block
        gameMap2[Position(2,4)] = MapTile.BlockOnStorage
        gameMap2[Position(2,5)] = MapTile.Storage
        gameMap2[Position(2,6)] = MapTile.Wall
        TestCase.assertEquals(Position(1,4),gameMap2.positionOfPerson())
    }

    @Test
    fun `Test adding positions together`() {
        TestCase.assertEquals(Position(2,3),Position(1,1) + Position(1,2))
        TestCase.assertEquals(Position(0,-1),Position(1,1) + Position(-1,-2))
        TestCase.assertEquals(Position(4,6),Position(1,2) + Position(3,4))
    }

    @Test
    fun `Test canMoveOnto a square if it is empty or is storage and cant move onto any other squares`(){
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Person
        TestCase.assertEquals(false,gameMap.canMoveOnto(Position(1,0)) )

        gameMap[Position(1,1)] = MapTile.PersonOnStorage
        TestCase.assertEquals(false,gameMap.canMoveOnto(Position(1,1)) )

        gameMap[Position(1,2)] = MapTile.Block
        TestCase.assertEquals(false,gameMap.canMoveOnto(Position(1,2)) )

        gameMap[Position(1,3)] = MapTile.BlockOnStorage
        TestCase.assertEquals(false,gameMap.canMoveOnto(Position(1,3)))

        gameMap[Position(1,4)] = MapTile.Wall
        TestCase.assertEquals(false,gameMap.canMoveOnto(Position(1,4)) )

        gameMap[Position(1,5)] = MapTile.Storage
        TestCase.assertEquals(true,gameMap.canMoveOnto(Position(1,5)) )

        gameMap[Position(1,6)] = MapTile.Empty
        TestCase.assertEquals(true,gameMap.canMoveOnto(Position(1,6)) )

    }
    @Test
    fun `Test if position on gameMap contains a block`(){
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Person
        TestCase.assertEquals(false,gameMap.containsBlock(Position(1,0)) )

        gameMap[Position(1,2)] = MapTile.Block
        TestCase.assertEquals(true,gameMap.containsBlock(Position(1,2)) )

        gameMap[Position(1,3)] = MapTile.BlockOnStorage
        TestCase.assertEquals(true,gameMap.containsBlock(Position(1,3)))

        gameMap[Position(1,4)] = MapTile.Wall
        TestCase.assertEquals(false,gameMap.containsBlock(Position(1,4)) )

    }
    @Test
    fun `Test moving a person to an empty square on the gameMap`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(positionToMoveFrom, positionToMoveTo)
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,3)])
    }

    @Test
    fun `Test moving a person to an storage square on the gameMap`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Storage
        gameMap[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(positionToMoveFrom, positionToMoveTo)
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.PersonOnStorage, gameMap[Position(1,3)])
    }

    @Test
    fun `Test moving a person form a storage square on the gameMap`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.PersonOnStorage
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        val positionToMoveFrom = Position(1, 2)
        val positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(positionToMoveFrom, positionToMoveTo)
        TestCase.assertEquals(MapTile.Storage, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,3)])
    }

    @Test
    fun `Test moving a person left`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        gameMap.moveMapTile(Direction.L)
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,0)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,4)])
    }
    @Test
    fun `Test moving a person right`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        gameMap.moveMapTile(Direction.R)
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,4)])
    }

    @Test
    fun `Test moving a person up`() {
        val gameMap = newGameMap()
        gameMap[Position(0,0)] = MapTile.Wall
        gameMap[Position(0,1)] = MapTile.Empty
        gameMap[Position(0,2)] = MapTile.Storage
        gameMap[Position(0,3)] = MapTile.Empty
        gameMap[Position(0,4)] = MapTile.Wall
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        gameMap.moveMapTile(Direction.U)
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(0,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(0,1)])
        TestCase.assertEquals(MapTile.PersonOnStorage, gameMap[Position(0,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(0,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(0,4)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,4)])
    }
    @Test
    fun `Test moving a person down`() {
        val gameMap = newGameMap()
        gameMap[Position(0,0)] = MapTile.Wall
        gameMap[Position(0,1)] = MapTile.Empty
        gameMap[Position(0,2)] = MapTile.Storage
        gameMap[Position(0,3)] = MapTile.Empty
        gameMap[Position(0,4)] = MapTile.Wall
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Empty
        gameMap[Position(1,2)] = MapTile.Person
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall
        gameMap[Position(2,0)] = MapTile.Wall
        gameMap[Position(2,1)] = MapTile.Empty
        gameMap[Position(2,2)] = MapTile.Empty
        gameMap[Position(2,3)] = MapTile.Empty
        gameMap[Position(2,4)] = MapTile.Wall
        gameMap.moveMapTile(Direction.D)
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(0,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(0,1)])
        TestCase.assertEquals(MapTile.Storage, gameMap[Position(0,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(0,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(0,4)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(1,4)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(2,0)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(2,1)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(2,2)])
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(2,3)])
        TestCase.assertEquals(MapTile.Wall, gameMap[Position(2,4)])
    }

    @Test
    fun `Test moving a person into a block that can move`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Person
        gameMap[Position(1,2)] = MapTile.Block
        gameMap[Position(1,3)] = MapTile.Empty
        gameMap[Position(1,4)] = MapTile.Wall

        gameMap.moveMapTile(Direction.R)
        TestCase.assertEquals(MapTile.Empty, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, gameMap[Position(1,3)])
    }

    @Test
    fun `Test moving a person into a block that cannot move`() {
        val gameMap = newGameMap()
        gameMap[Position(1,0)] = MapTile.Wall
        gameMap[Position(1,1)] = MapTile.Person
        gameMap[Position(1,2)] = MapTile.Block
        gameMap[Position(1,3)] = MapTile.Block
        gameMap[Position(1,4)] = MapTile.Wall

        gameMap.moveMapTile(Direction.R)
        TestCase.assertEquals(MapTile.Person, gameMap[Position(1,1)])
        TestCase.assertEquals(MapTile.Block, gameMap[Position(1,2)])
        TestCase.assertEquals(MapTile.Block, gameMap[Position(1,3)])
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