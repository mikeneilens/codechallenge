import XCTest

class MapTile:Equatable {
    let mapTileUnderneath:MapTile?
    
    init() {self.mapTileUnderneath = nil}
    init( _ onMapTile:MapTile? = nil) { self.mapTileUnderneath = onMapTile}
    
    static func == (lhs: MapTile, rhs: MapTile) -> Bool {
        return type(of:lhs) === type(of:rhs)
    }
    //override this if it is needed
    func place(onMapTile mapTile:MapTile) -> MapTile {
        return MapTile(mapTile)
    }
    //override this
    var toString:String { return Empty.identifier }
}

class Empty:MapTile {
    static let identifier = " "
    override var toString: String { return Empty.identifier}
}
class Storage:MapTile {
    static let identifier = "*"
    override var toString: String { return Storage.identifier}
}
class Person:MapTile {
    static let identifier = "p"
    static let onStorageIdentifier = "P"
    override var toString: String {return (self.mapTileUnderneath is Storage) ? Person.onStorageIdentifier : Person.identifier}
    
    override func place(onMapTile mapTile:MapTile) -> Person {
        return Person(mapTile)
    }
}
class Block:MapTile {
    static let identifier = "b"
    static let onStorageIdentifier = "B"
    override var toString: String {return (self.mapTileUnderneath is Storage) ? Block.onStorageIdentifier : Block.identifier}
    
    override func place(onMapTile mapTile:MapTile) -> Block {
        return Block(mapTile)
    }
}
class Wall:MapTile {
    static let identifier = "#"
    override var toString: String { return Wall.identifier}
}

//=====================================================================================================
class MapTileCreator{
    static let emptyFloor = MapTileCreator.create(using: Empty.identifier)
    static let storage = MapTileCreator.create(using: Storage.identifier)
    static let wall = MapTileCreator.create(using: Wall.identifier)
    
    static func create(using string:String) -> MapTile {
        switch string {
        case Empty.identifier: return Empty()
        case Wall.identifier: return Wall()
        case Storage.identifier: return Storage()
        case Person.identifier: return Person(emptyFloor)
        case Person.onStorageIdentifier: return Person(storage)
        case Block.identifier: return Block(emptyFloor)
        case Block.onStorageIdentifier: return Block(storage)
        default: return Empty()
        }
    }
}

//=====================================================================================================
struct Position:Hashable {
    let row:Int
    let column:Int
    init (_ row:Int, _ column:Int) {
        self.row = row
        self.column = column
    }
    static func +(left:Position, right:Position) -> Position {
        return Position(left.row + right.row, left.column + right.column)
    }
}

//=====================================================================================================
enum Direction {
    case left
    case right
    case up
    case down
    
    var move:Position {
        switch self {
            case .left: return Position(0,-1)
            case .right: return Position(0,1)
            case .up: return Position(-1,0)
            case .down: return Position(1,0)
        }
    }
    init(string:String) {
        switch string {
            case "U" : self = Direction.up
            case "D" : self = Direction.down
            case "L" : self = Direction.left
            case "R" : self = Direction.right
            default:  self = Direction.right
        }
    }
}

//=====================================================================================================
typealias GameArray = Array<String>
typealias Grid  = Dictionary<Position, MapTile>

protocol MapTileMover {
    func moveMapTile(grid:Grid, direction: Direction) -> Grid
}

struct GameMap {
    private let grid:Grid
    private let mapTileMover:MapTileMover
    
    subscript(row:Int, column:Int) -> MapTile? {
        return grid[Position(row,column)]
    }
    
    init (gameArray:GameArray, mapTileMover:MapTileMover){
        var newGrid = Grid()
        for (row, gameString) in gameArray.enumerated() {
            let stringChars = gameString.map{String($0)}
            for (column, stringChar) in stringChars.enumerated() {
                newGrid[Position(row, column)] = MapTileCreator.create(using: stringChar)
            }
        }
        self.mapTileMover = mapTileMover
        self.grid = newGrid
    }
    
    private init(grid newGrid:Grid, mapTileMover:MapTileMover) {
        self.mapTileMover = mapTileMover
        self.grid = newGrid
    }
    
    func toGameArray() -> GameArray {
        let rows = grid.keys.map{$0.row}
        let uniqueRows = Array(Set(rows)).sorted()
        return uniqueRows.map{self.toString(forRow: $0)}
    }
    
    private func toString(forRow row:Int) -> String {
        return grid.filter{position, value in position.row == row}
            .map{position, mapTile in (position, mapTile)}
            .sorted(by: { $0.0.column < $1.0.column})
            .map{(position, mapTile) in return mapTile.toString}
            .reduce("",+)
    }
    
    func moveMapTile(direction: Direction) -> GameMap {
        return GameMap(grid: mapTileMover.moveMapTile(grid: grid, direction: direction), mapTileMover: mapTileMover)
    }
}

//=====================================================================================================
struct PlayerMover:MapTileMover {
    
    func moveMapTile(grid:Grid, direction: Direction) -> Grid {
        let positionOfPerson = self.positionOfPerson(onGrid:grid)
        let positionAdjacentToPlayer = positionOfPerson + direction.move
        
        let updatedGrid = tryAndMoveTile(fromPosition: positionOfPerson, direction: direction, onGrid: grid)
        
        switch (true) {
        case updatedGrid != grid: return updatedGrid
        case grid[positionAdjacentToPlayer] is Block:
            return tryAndMoveBlockAndPerson(positionOfPerson: positionOfPerson, direction: direction, onGrid:grid)
        default: return grid
        }
    }
    
    private func positionOfPerson(onGrid grid:Grid) -> Position {
        let position = grid.first{(position, mapTile) in return (mapTile is Person)}?.key
        return position ?? Position(0,0)
    }
    
    private func tryAndMoveBlockAndPerson(positionOfPerson: Position,direction: Direction, onGrid grid:Grid) -> Grid{
        let positionOfBlock = positionOfPerson + direction.move
        let newGrid = tryAndMoveTile(fromPosition: positionOfBlock, direction: direction, onGrid:grid)
        if newGrid != grid {
            let updatedGrid = moveMapTile(from: positionOfPerson, to: positionOfBlock, onGrid:newGrid)
            return updatedGrid
        }
        else {
            return grid
        }
    }
    
    private func tryAndMoveTile(fromPosition: Position, direction: Direction, onGrid grid:Grid) -> Grid {
        let adjacentPosition = fromPosition + direction.move
        if canMoveOnto(position:adjacentPosition, onGrid:grid) {
            return moveMapTile(from: fromPosition, to: adjacentPosition, onGrid: grid)
        } else {
            return grid
        }
    }
    
    private func canMoveOnto(position:Position, onGrid grid:Grid) -> Bool {
        guard let mapTile = grid[position] else {return false}
        return (mapTile is Empty) || (mapTile is Storage)
    }
    
    private func moveMapTile(from positionToMoveFrom: Position, to positionToMoveTo: Position, onGrid grid:Grid) -> Grid {
        guard let mapTile = grid[positionToMoveFrom], let mapTileToMoveTo = grid[positionToMoveTo] else { return grid }
        
        let mapTileForOldPosition = mapTile.mapTileUnderneath
        let mapTileForNewPosition = mapTile.place(onMapTile: mapTileToMoveTo)
        
        var resultingGrid = grid
        resultingGrid[positionToMoveFrom] = mapTileForOldPosition
        resultingGrid[positionToMoveTo] = mapTileForNewPosition
        return resultingGrid
    }
}

//=====================================================================================================
func processSokobanMove(_ listOfStrings:GameArray, _ direction:String) -> GameArray {
    let gameMap = GameMap(gameArray: listOfStrings, mapTileMover: PlayerMover())
    let directionToMove = Direction(string: direction)
    let updatedGameMap = gameMap.moveMapTile(direction:directionToMove)
    return updatedGameMap.toGameArray()
}

//=====================================================================================================
func puzzleIsSolved(_ listOfStrings:GameArray) -> Bool {
    return listOfStrings.map{!($0.contains("b"))}.reduce(true){ acc,value in value && acc}
}

//=====================================================================================================
// Tests
//=====================================================================================================
class GameMapTests: XCTestCase {
    
    func test_convertString_to_MapTile() {
        let person = MapTileCreator.create(using:"p")
        let wall = MapTileCreator.create(using:"#")
        let block = MapTileCreator.create(using:"b")
        let storage = MapTileCreator.create(using:"*")
        let personOnStorageSquare = MapTileCreator.create(using:"P")
        let blockOnStorageSquare = MapTileCreator.create(using:"B")
        let empty = MapTileCreator.create(using:" ")
        let empty2 = MapTileCreator.create(using:"some bad data")
        XCTAssertEqual(Person(), person)
        XCTAssertEqual(Wall(), wall)
        XCTAssertEqual(Block(), block)
        XCTAssertEqual(Empty(), block.mapTileUnderneath)
        XCTAssertEqual(Storage(), storage)
        XCTAssertEqual(Person(), personOnStorageSquare)
        XCTAssertEqual(Storage(), personOnStorageSquare.mapTileUnderneath)
        XCTAssertEqual(Block(), blockOnStorageSquare)
        XCTAssertEqual(Storage(), blockOnStorageSquare.mapTileUnderneath)
        XCTAssertEqual(Empty(),empty)
        XCTAssertEqual(Empty(),empty2)
    }
    
    func testUpdatingMapFromAString() {
        let gameMap = GameMap(gameArray: ["p",
                                          "# pb*PB"], mapTileMover: PlayerMover())
        XCTAssertEqual(gameMap[1,0], Wall())
        XCTAssertEqual(gameMap[1,1], Empty())
        XCTAssertEqual(gameMap[1,2], Person())
        XCTAssertEqual(gameMap[1,2]?.mapTileUnderneath, Empty())
        XCTAssertEqual(gameMap[1,3], Block())
        XCTAssertEqual(gameMap[1,3]?.mapTileUnderneath, Empty())
        XCTAssertEqual(gameMap[1,4], Storage())
        XCTAssertEqual(gameMap[1,5], Person())
        XCTAssertEqual(gameMap[1,5]?.mapTileUnderneath, Storage())
        XCTAssertEqual(gameMap[1,6], Block())
        XCTAssertEqual(gameMap[1,6]?.mapTileUnderneath, Storage())
        
    }
    
    func testInitialisingGameMap2WithArrayofStrings() {
        let arrayOfStrings = ["# p #",
                              "B*Ppb"]
        let gameMap = GameMap(gameArray: arrayOfStrings, mapTileMover: PlayerMover() )
        XCTAssertEqual(gameMap[0,0], Wall())
        XCTAssertEqual(gameMap[0,1], Empty())
        XCTAssertEqual(gameMap[0,2], Person())
        XCTAssertEqual(gameMap[0,2]?.mapTileUnderneath, Empty())
        XCTAssertEqual(gameMap[0,3], Empty())
        XCTAssertEqual(gameMap[0,4], Wall())
        XCTAssertEqual(gameMap[1,0], Block())
        XCTAssertEqual(gameMap[1,0]?.mapTileUnderneath, Storage())
        XCTAssertEqual(gameMap[1,1], Storage())
        XCTAssertEqual(gameMap[1,2], Person())
        XCTAssertEqual(gameMap[1,2]?.mapTileUnderneath, Storage())
        XCTAssertEqual(gameMap[1,3], Person())
        XCTAssertEqual(gameMap[1,3]?.mapTileUnderneath, Empty())
        XCTAssertEqual(gameMap[1,4], Block())
        XCTAssertEqual(gameMap[1,4]?.mapTileUnderneath, Empty())
    }
    
    
    func testGameMap2ToStringAtRow() {
        let gameMap = GameMap(gameArray: ["pPbB#* ",
                                          " pbPB*#"], mapTileMover: PlayerMover())
        XCTAssertEqual(["pPbB#* "," pbPB*#"],gameMap.toGameArray())
    }
    
    func testMovingAPersonToAnEmptySquareOnGameMap2UsungPlayerMover() {
        let gameMap = GameMap(gameArray:["# p #"], mapTileMover: PlayerMover())
        let updatedGameMap2 = gameMap.moveMapTile(direction: Direction.right)
        
        XCTAssertEqual(Empty(), updatedGameMap2[0,2])
        XCTAssertEqual(Person(), updatedGameMap2[0,3])
        XCTAssertEqual(Empty(), updatedGameMap2[0,3]?.mapTileUnderneath)
    }
}

class ProcessSokobanMoveTests:XCTestCase {
    func testProcessSokabanMoveWithSimpleMoveRight() {
        let inputList = [
            "#############",
            "#p        * #",
            "#     b  b  #",
            "# *         #",
            "#############"]
        let afterMoveRight = [
            "#############",
            "# p       * #",
            "#     b  b  #",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveRight, processSokobanMove(inputList,"R"))
    }
    
    func testProcessSokabanMoveWithSimpleMoveDown() {
        let inputList = [
            "#############",
            "#p        * #",
            "#     b  b  #",
            "# *         #",
            "#############"]
        let afterMoveDown = [
            "#############",
            "#         * #",
            "#p    b  b  #",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveDown, processSokobanMove(inputList,"D"))
    }
    
    func testProcessSokabanMoveMoveBlock () {
        let inputList = [
            "#############",
            "#         * #",
            "#     b  b  #",
            "# *   p     #",
            "#############"]
        let afterMoveUp = [
            "#############",
            "#     b   * #",
            "#     p  b  #",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveUp, processSokobanMove(inputList,"U"))
    }
    
    func testProcessSokabanMovMoveBlockOntoStorage() {
        let inputList = [
            "#############",
            "#         * #",
            "#    *bp b  #",
            "# *         #",
            "#############"]
        let afterMoveLeft = [
            "#############",
            "#         * #",
            "#    Bp  b  #",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveLeft, processSokobanMove(inputList,"L"))
    }
    
    func testProcessSokabanMoveMoveBlockOffStorage() {
        let inputList = [
            "#############",
            "#         * #",
            "#    Bp  b  #",
            "# *         #",
            "#############"]
        let afterMoveLeft = [
            "#############",
            "#         * #",
            "#   bP   b  #",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveLeft, processSokobanMove(inputList,"L"))
    }
    
    func testProcessSokabanMoveCannotMovePersonOffTheBoard() {
        let inputList = [
            "#############",
            "#         * #",
            "#    *   b  p",
            "# *         #",
            "#############"]
        let afterMoveRight = [
            "#############",
            "#         * #",
            "#    *   b  p",
            "# *         #",
            "#############"]
        XCTAssertEqual(afterMoveRight, processSokobanMove(inputList,"R"))
    }
    
}

class testPuzzleIsSolved:XCTestCase {
    func testPuzzelIsNotSolvedOneBlock() {
        let inputList = [
            "#############",
            "#         * #",
            "#    *   b  p",
            "# *         #",
            "#############"]
        XCTAssertEqual(false, puzzleIsSolved(inputList))
    }
    
    func testPuzzelIsNotSolvedManyBlock() {
        let inputList = [
            "#############",
            "#    b    * #",
            "#    *   b  p",
            "# *   B     #",
            "#############"]
        XCTAssertEqual(false, puzzleIsSolved(inputList))
    }
    
    func testPuzzelIsSolvedManyBlock() {
        let inputList = [
            "#############",
            "#    B    * #",
            "#    *   B  p",
            "# *   B     #",
            "#############"]
        XCTAssertEqual(true, puzzleIsSolved(inputList))
    }
}

class PositionTests:XCTestCase {
    func testAddingsTogetherPosition() {
        XCTAssertEqual(Position(2,3),Position(1,1) + Position(1,2))
        XCTAssertEqual(Position(0,-1),Position(1,1) + Position(-1,-2))
        XCTAssertEqual(Position(4,6),Position(1,2) + Position(3,4))
    }
}

class PlayerMoverTests: XCTestCase {
    func testMovingAPersonToAnEmptySquareOnGrid2() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Person(Empty())
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)]?.mapTileUnderneath)
    }
    
    func testMovingAPersonToAStorageSquareOnGrid2() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Person(Empty())
        grid[Position(0,3)] = Storage(Empty())
        grid[Position(0,4)] = Wall()
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Storage(), updatedGrid[Position(0,3)]?.mapTileUnderneath)
    }
    
    func testMovingAPersonFromAStorageSquareOnGrid2() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Person(Storage())
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Storage(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)]?.mapTileUnderneath)
    }
    
    func testMovingAPersonLeft() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Person(Empty())
        grid[Position(0,3)] = Storage()
        grid[Position(0,4)] = Wall()
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.left)
        
        XCTAssertEqual(Person(), updatedGrid[Position(0,1)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,1)]?.mapTileUnderneath)
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)])
    }
    
    func testMovingAPersonRight() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Person(Empty())
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)]?.mapTileUnderneath)
    }
    
    func testMovingAPersonUp() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Storage()
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        grid[Position(1,0)] = Wall()
        grid[Position(1,1)] = Empty()
        grid[Position(1,2)] = Person(Empty())
        grid[Position(1,3)] = Empty()
        grid[Position(1,4)] = Wall()
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.up)
        
        XCTAssertEqual(Wall(), updatedGrid[Position(0,0)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,1)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Storage(), updatedGrid[Position(0,2)]?.mapTileUnderneath)
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Wall(), updatedGrid[Position(0,4)])
        XCTAssertEqual(Wall(), updatedGrid[Position(1,0)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,1)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,3)])
        XCTAssertEqual(Wall(), updatedGrid[Position(1,4)])
    }
    
    
    func testMovingAPersonDown() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Empty()
        grid[Position(0,2)] = Storage()
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        grid[Position(1,0)] = Wall()
        grid[Position(1,1)] = Empty()
        grid[Position(1,2)] = Person(Empty())
        grid[Position(1,3)] = Empty()
        grid[Position(1,4)] = Wall()
        grid[Position(2,0)] = Wall()
        grid[Position(2,1)] = Empty()
        grid[Position(2,2)] = Empty()
        grid[Position(2,3)] = Empty()
        grid[Position(2,4)] = Wall()
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.down)
        
        XCTAssertEqual(Wall(), updatedGrid[Position(0,0)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,1)])
        XCTAssertEqual(Storage(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Wall(), updatedGrid[Position(0,4)])
        XCTAssertEqual(Wall(), updatedGrid[Position(1,0)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,1)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(1,3)])
        XCTAssertEqual(Wall(), updatedGrid[Position(1,4)])
        XCTAssertEqual(Wall(), updatedGrid[Position(2,0)])
        XCTAssertEqual(Empty(), updatedGrid[Position(2,1)])
        XCTAssertEqual(Person(), updatedGrid[Position(2,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(2,2)]?.mapTileUnderneath)
        XCTAssertEqual(Empty(), updatedGrid[Position(2,3)])
        XCTAssertEqual(Wall(), updatedGrid[Position(2,4)])
    }
    
    func testMovingAPersonIntoABlockThatCanMove() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Person(Empty())
        grid[Position(0,2)] = Block(Empty())
        grid[Position(0,3)] = Empty()
        grid[Position(0,4)] = Wall()
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Empty(), updatedGrid[Position(0,1)])
        XCTAssertEqual(Person(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)]?.mapTileUnderneath)
        XCTAssertEqual(Block(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)]?.mapTileUnderneath)
    }
    
    func testMovingAPersonIntoABlockThatCannotMove() {
        var grid = Grid()
        grid[Position(0,0)] = Wall()
        grid[Position(0,1)] = Person(Empty())
        grid[Position(0,2)] = Block(Empty())
        grid[Position(0,3)] = Block(Empty())
        grid[Position(0,4)] = Empty()
        grid[Position(0,5)] = Wall()
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(Person(), updatedGrid[Position(0,1)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,1)]?.mapTileUnderneath)
        XCTAssertEqual(Block(), updatedGrid[Position(0,2)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,2)]?.mapTileUnderneath)
        XCTAssertEqual(Block(), updatedGrid[Position(0,3)])
        XCTAssertEqual(Empty(), updatedGrid[Position(0,3)]?.mapTileUnderneath)    }
}

GameMapTests.defaultTestSuite.run()
PositionTests.defaultTestSuite.run()
ProcessSokobanMoveTests.defaultTestSuite.run()
PlayerMoverTests.defaultTestSuite.run()

