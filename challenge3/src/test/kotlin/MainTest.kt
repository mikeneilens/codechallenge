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
        map1.fromString(1,"p")
        TestCase.assertEquals(map1[Position(1,0)],MapTile.Person)
        map1.fromString(1,"# pb*PB")
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
        TestCase.assertEquals("pPbB#* ",map1.toString(1))
        val map2 = mutableMapOf<Position, MapTile>()
        map2[Position(1,1)] = MapTile.Person
        map2[Position(1,3)] = MapTile.PersonOnStorage
        map2[Position(1,2)] = MapTile.Block
        map2[Position(1,4)] = MapTile.BlockOnStorage
        map2[Position(1,6)] = MapTile.Wall
        map2[Position(1,5)] = MapTile.Storage
        map2[Position(1,0)] = MapTile.Empty
        TestCase.assertEquals(" pbPB*#",map2.toString(1))
    }
    @Test
    fun `Test List of String toMap`() {
        val listOfStrings = listOf<String>("# p #", "B*Ppb")
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
}