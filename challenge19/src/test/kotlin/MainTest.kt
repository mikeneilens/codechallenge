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
        val position = Position(0,0)
        val orientation = Orientation.East
        val mapData = " "
        assertEquals(listOf<String>(), viewAhead(position,orientation,mapData))
    }

    @Test
    fun `view ahead is one O if map is two characters and trolley is facing towards a space from any direction`() {
        val positionFacingEast = Position(0,0)
        val mapDataForEast = "  "
        assertEquals(listOf<String>("O"), viewAhead(positionFacingEast,Orientation.East,mapDataForEast))

        val positionFacingWest = Position(1,0)
        val mapDataForWest = "  "
        assertEquals(listOf<String>("O"), viewAhead(positionFacingWest,Orientation.West,mapDataForWest))

        val positionFacingSouth = Position(0,0)
        val mapDataForSouth = "  \n" + " *"
        assertEquals(listOf<String>("O"), viewAhead(positionFacingSouth,Orientation.South,mapDataForSouth))

        val positionFacingNorth = Position(0,1)
        val mapDataForNorth = " *\n" + "  "
        assertEquals(listOf<String>("O"), viewAhead(positionFacingNorth,Orientation.North,mapDataForNorth))
    }

    @Test
    fun `view ahead is more than one O if trolley is facing into a dead end corridor of more than one space`() {
        val position = Position(1,0)
        val mapData = "* *\n" + "* *\n" + "* *\n" + "* *\n"
        assertEquals(listOf("O","O","O"), viewAhead(position,Orientation.South,mapData))
    }

    @Test
    fun `view ahead contains a left turn if there is a left turn`() {
        val position = Position(1,0)
        val mapData = "* *\n" + "* *\n" + "*  \n" + "* *\n"
        assertEquals(listOf("O","OL","O"), viewAhead(position,Orientation.South,mapData))
    }

    @Test
    fun `view ahead contains a right turn if there is a right turn`() {
        val position = Position(1,0)
        val mapData = "* *\n" + "* *\n" + "  *\n" + "* *\n"
        assertEquals(listOf("O","OR","O"), viewAhead(position,Orientation.South,mapData))
    }

    @Test
    fun `view ahead contains a right and left turn if there is both`() {
        val position = Position(1,0)
        val mapData = "* *\n" + "*  \n" + "  *\n" + "* *\n"
        assertEquals(listOf("OL","OR","O"), viewAhead(position,Orientation.South,mapData))
    }

    @Test
    fun `view ahead contains a right and left turn and a cross roads if there is all three`() {
        val position = Position(1,0)
        val mapData = "* *\n" + "*  \n" + "  *\n" + "   \n" + "* *\n"
        assertEquals(listOf("OL","OR","OLR","O"), viewAhead(position,Orientation.South,mapData))
    }

    @Test
    fun `attempting to move a trolley into a fixture leaves the trolley in the same position`() {
        val position = Position(0,0)
        val mapData = " *"
        val (newPosition, newOrientation) = moveOrRotate(Command.M,position, Orientation.East, mapData)
        assertEquals(position, newPosition)
    }
    @Test
    fun `rotating the trolley facing East Left leaves it facing North` () {
        val position = Position(0,0)
        val mapData = " *"
        val (newPosition, newOrientation) = moveOrRotate(Command.L,position, Orientation.East, mapData)
        assertEquals(Orientation.North, newOrientation)
    }

    @Test
    fun `calling function to move trolley with no initial referenceId returns a view from an initial position of 1,1`() {
        val mapData =
                            "************\n" +
                            "           *\n" +
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
        assertEquals(listOf("O", "O", "O", "O", "O", "O", "O", "O", "O"),viewAhead)
        assertEquals(listOf("O", "O", "O", "O", "O", "O", "O", "O"),nextViewAhead)
    }

}