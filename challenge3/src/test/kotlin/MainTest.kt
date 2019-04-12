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
}