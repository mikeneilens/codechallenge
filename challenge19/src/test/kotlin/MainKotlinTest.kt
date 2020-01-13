import io.kotlintest.data.forall
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import io.kotlintest.tables.row

class MainKotlinTest : WordSpec({

    "MapData.contentAt()" should {
        "return a fixture for any positions not on the map " {
            val mapOfShop = " "
            forall(
                row(mapOfShop.contentAt(Position(-1, 0))),
                row(mapOfShop.contentAt(Position(0, -1))),
                row(mapOfShop.contentAt(Position(1, 0))),
                row(mapOfShop.contentAt(Position(0, 1)))
            ) { outOfBoundsContent ->
                outOfBoundsContent shouldBe MapContent.Fixture
            }
        }
        "return MapContent.Fixture for any stars on the map and MapContent.Open for any spaces on the map" {
            val mapOfShop =
                        "***\n" +
                        "* *\n" +
                        "  *\n"
            forall(
                row(mapOfShop.contentAt(Position(0, 0))),
                row(mapOfShop.contentAt(Position(1, 0))),
                row(mapOfShop.contentAt(Position(2, 0))),
                row(mapOfShop.contentAt(Position(0, 1))),
                row(mapOfShop.contentAt(Position(2, 1))),
                row(mapOfShop.contentAt(Position(2, 2)))
            ){ contentAtfixturePositon ->
                contentAtfixturePositon shouldBe MapContent.Fixture
            }

            forall(
                row(mapOfShop.contentAt(Position(1, 1))),
                row(mapOfShop.contentAt(Position(0, 2))),
                row(mapOfShop.contentAt(Position(1, 2)))
            ) { contentAtOpenPosition ->
                contentAtOpenPosition shouldBe MapContent.Open
            }
        }
    }
    "Trolley.viewAhead()" should {
        "return empty if map is one character" {
            val trolley = Trolley(Position(0,0),Orientation.East,5312)
            val mapData = " "
            trolley.viewAhead(mapData).shouldBeEmpty()
        }
        "return one 'O' if map is two characters and trolley is facing towards a space from any direction" {
            forall(
                row( Trolley(Position(0,0),Orientation.East,5312), "  "),
                row( Trolley(Position(1,0),Orientation.West,5312), "  "),
                row( Trolley(Position(0,0),Orientation.South,5312), "  \n" + " *"),
                row( Trolley(Position(0,1),Orientation.North,5312), " *\n" + "  ")
            ) { trolley, mapData ->
                trolley.viewAhead(mapData) shouldBe listOf("O")
            }
        }
        "return more than one 'O' if trolley is facing into a dead end corridor of more than one space" {
            val trolley = Trolley(Position(1,0),Orientation.South,5312)
            val mapData =
                        "* *\n" +
                        "* *\n" +
                        "* *\n" +
                        "* *\n"
            trolley.viewAhead(mapData) shouldBe listOf("O","O","O")
        }
        "return a left turn if there is a left turn" {
            val trolley = Trolley(Position(1,0),Orientation.South,5312)
            val mapData =
                        "* *\n" +
                        "* *\n" +
                        "*  \n" +
                        "* *\n"
            trolley.viewAhead(mapData) shouldBe listOf("O","OL","O")
        }
        "return a right turn if there is a right turn" {
            val trolley = Trolley(Position(1,0),Orientation.South,5312)
            val mapData =
                        "* *\n" +
                        "* *\n" +
                        "  *\n" +
                        "* *\n"
            trolley.viewAhead(mapData) shouldBe listOf("O","OR","O")
        }
        "return a right and left turn if there is both" {
            val trolley = Trolley(Position(1,0),Orientation.South,5312)
            val mapData =
                        "* *\n" +
                        "*  \n" +
                        "  *\n" +
                        "* *\n"
            trolley.viewAhead(mapData) shouldBe listOf("OL","OR","O")
        }
        "return a right and left turn and a cross roads if there is all three"{
            val trolley = Trolley(Position(1,0),Orientation.South,5312)
            val mapData =
                        "* *\n" +
                        "*  \n" +
                        "  *\n" +
                        "   \n" +
                        "* *\n"
            trolley.viewAhead(mapData) shouldBe listOf("OL","OR","OLR","O")
        }
    }
    "MapData.viewAhead()" should {
        "return one 'O' from a position where there is two characters and is facing towards a space from any direction" {
            forall(
                row(Position(0,0), Orientation.East,"  " ),
                row(Position(1,0), Orientation.West,"  " ),
                row(Position(0,0), Orientation.South,"  \n" + " *"),
                row(Position(0,1), Orientation.North," *\n" + "  ")
            ) { position, orientation, mapData ->
                mapData.viewAheadAt(position, orientation) shouldBe listOf("O")
            }
        }
    }

    "Trolley.moveOrRotate()" should {
        "create a trolley in the same position as the original if its way ahead is blocked by a fixture" {
            val originalTrolley = Trolley(Position(0,0),Orientation.East,5312)
            val mapData = " *"
            val newTrolley = originalTrolley.moveOrRotate(Command.M,mapData)

            newTrolley.position shouldBe originalTrolley.position
        }
        "craete a trolley facing North if the original trolley facing East is rotated left"{
            val trolley = Trolley(Position(0,0),Orientation.East,5312)
            val mapData = " *"
            trolley.moveOrRotate(Command.L, mapData).orientation shouldBe Orientation.North
        }
        "create a trolley in a new position and a view that is one space shorter than before if the trolley can be moved" {
            val mapData =
                        "************\n" +
                        "           *\n" +
                        "************\n"
            val (originalViewAhead, referenceId ) = moveTrolley(mapData)
            val (nextViewAhead, _) = moveTrolley(Command.M, referenceId, mapData)

            nextViewAhead.size shouldBe originalViewAhead.size - 1
            originalViewAhead shouldBe listOf("O", "O", "O", "O", "O", "O", "O", "O", "O", "O")
            nextViewAhead shouldBe listOf("O", "O", "O", "O", "O", "O", "O", "O", "O")
        }

    }
})

