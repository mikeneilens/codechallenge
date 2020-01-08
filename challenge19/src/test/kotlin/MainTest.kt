import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `content outside of map is a fixture`() {
        val mapOfShop = " "
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(-1,0)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(0,-1)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(1,0)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(0,1)))
    }

    @Test
    fun `content on map which is a star is a fixture and content with a space is open`() {
        val mapOfShop =
                "***\n" +
                "* *\n" +
                "  *\n"
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(0,0)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(1,0)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(2,0)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(0,1)))
        assertEquals(Content.Open, mapOfShop.contentAt(Position(1,1)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(2,1)))
        assertEquals(Content.Open, mapOfShop.contentAt(Position(0,2)))
        assertEquals(Content.Open, mapOfShop.contentAt(Position(1,2)))
        assertEquals(Content.Fixture, mapOfShop.contentAt(Position(2,2)))
    }

    @Test
    fun `view ahead is empty if map is one character`() {
        val trolley = Trolley(Position(0,0),Orientation.East,5312)
        val mapData = " "
        assertEquals(listOf<String>(), trolley.viewAhead(mapData))
    }

    @Test
    fun `view ahead is one O if map is two characters and trolley is facing towards a space from any direction`() {
        val trolleyEast = Trolley(Position(0,0),Orientation.East,5312)
        val mapDataForEast = "  "
        assertEquals(listOf<String>("O"), trolleyEast.viewAhead(mapDataForEast))

        val trolleyWest= Trolley(Position(1,0),Orientation.West,5312)
        val mapDataForWest = "  "
        assertEquals(listOf<String>("O"), trolleyWest.viewAhead(mapDataForWest))

        val trolleySouth = Trolley(Position(0,0),Orientation.South,5312)
        val mapDataForSouth = "  \n" + " *"
        assertEquals(listOf<String>("O"), trolleySouth.viewAhead(mapDataForSouth))

        val trolleyNorth = Trolley(Position(0,1),Orientation.North,5312)
        val mapDataForNorth = " *\n" + "  "
        assertEquals(listOf<String>("O"), trolleyNorth.viewAhead(mapDataForNorth))
    }

    @Test
    fun `view ahead is more than one O if trolley is facing into a dead end corridor of more than one space`() {
        val trolley = Trolley(Position(1,0),Orientation.South,5312)
        val mapData = "* *\n" + "* *\n" + "* *\n" + "* *\n"
        assertEquals(listOf("O","O","O"), trolley.viewAhead(mapData))
    }

    @Test
    fun `view ahead contains a left turn if there is a left turn`() {
        val trolley = Trolley(Position(1,0),Orientation.South,5312)
        val mapData = "* *\n" + "* *\n" + "*  \n" + "* *\n"
        assertEquals(listOf("O","OL","O"), trolley.viewAhead(mapData))
    }

    @Test
    fun `view ahead contains a right turn if there is a right turn`() {
        val trolley = Trolley(Position(1,0),Orientation.South,5312)
        val mapData = "* *\n" + "* *\n" + "  *\n" + "* *\n"
        assertEquals(listOf("O","OR","O"), trolley.viewAhead(mapData))
    }

    @Test
    fun `view ahead contains a right and left turn if there is both`() {
        val trolley = Trolley(Position(1,0),Orientation.South,5312)
        val mapData = "* *\n" + "*  \n" + "  *\n" + "* *\n"
        assertEquals(listOf("OL","OR","O"), trolley.viewAhead(mapData))
    }

    @Test
    fun `view ahead contains a right and left turn and a cross roads if there is all three`() {
        val trolley = Trolley(Position(1,0),Orientation.South,5312)
        val mapData = "* *\n" + "*  \n" + "  *\n" + "   \n" + "* *\n"
        assertEquals(listOf("OL","OR","OLR","O"), trolley.viewAhead(mapData))
    }

    @Test
    fun `attempting to move a trolley into a fixture leaves the trolley in the same position`() {
        val trolley = Trolley(Position(0,0),Orientation.East,5312)
        val mapData = " *"
        val newTrolley = trolley.moveOrRotate(Command.M,mapData)
        assertEquals(trolley.position, newTrolley.position)
    }
    @Test
    fun `rotating the trolley facing East Left leaves it facing North` () {
        val trolley = Trolley(Position(0,0),Orientation.East,5312)
        val mapData = " *"
        val (newPosition, newOrientation) = trolley.moveOrRotate(Command.L,mapData)
        assertEquals(Orientation.North, newOrientation)
    }

    @Test
    fun `calling function to move trolley with no initial referenceId returns a view from an initial position of 1,1`() {
        val mapData =
                            "************\n" +
                            "*          *\n" +
                            "************\n"
        val (viewAhead, referenceId ) = moveTrolley(mapData)
        assertEquals(listOf("O", "O", "O", "O", "O", "O", "O", "O", "O"),viewAhead)
    }
    @Test
    fun `calling function to move trolley with an initial referenceId returns a view that is one space shorter than before`() {
        val mapData =
                    "************\n" +
                    "           *\n" +
                    "************\n"
        val (viewAhead, referenceId ) = moveTrolley(mapData)
        val (nextViewAhead, nextReferenceId) = moveTrolley(Command.M, referenceId, mapData)
        assertEquals(viewAhead.size - 1, nextViewAhead.size)
        assertEquals(listOf("O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),viewAhead)
        assertEquals(listOf("O", "O", "O", "O", "O", "O", "O", "O", "O"),nextViewAhead)
    }

}