import XCTest



enum MapTile:String {
    case person = "p"
    case wall = "#"
    case block = "b"
    case storage = "*"
    case personOnStorage = "P"
    case blockOnStorage = "B"
    case empty = " "
    
    init(string:String) {
        self =  MapTile(rawValue:string) ?? MapTile.empty
    }
    var text:String { return self.rawValue}
    var onStorage:MapTile {
        switch self {
            case .person: return .personOnStorage
            case .block: return .blockOnStorage
            default: return self
        }
    }
    var notOnStorage:MapTile {
        switch self {
        case .personOnStorage: return .person
        case .blockOnStorage: return .block
        default: return self
        }
    }
}

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

typealias GameMap = Dictionary<Position, MapTile>
typealias GameArray = Array<String>

extension GameArray {
    func toGameMap() -> GameMap {
        var gameMap = GameMap()
        for (row, string) in self.enumerated() {
            gameMap.update(atRow: row, using: string)
        }
        return gameMap
    }
}

extension GameMap {
    mutating func update(atRow row:Int, using string:String) {
        let stringChars = string.map{String($0)}
        for (column, stringChar) in stringChars.enumerated() {
            self[Position(row,column )] = MapTile(string: stringChar)
        }
    }
    
    func toGameArray() -> GameArray {
        let rows = self.keys.map{$0.row}
        let uniqueRows = Array(Set(rows)).sorted()
        return uniqueRows.map{self.toString(forRow: $0)}
    }
    
    func toString(forRow row:Int) -> String {
        return self.filter{position, value in position.row == row}
            .map{position, mapTile in (position, mapTile)}
            .sorted(by: { $0.0.column < $1.0.column})
            .map{(position, mapTile) in return mapTile.text}
            .reduce("",+)
    }
    
    func positionOfPerson() -> Position {
        let position = self.first{(position, mapTile) in mapTile == MapTile.person || mapTile == MapTile.personOnStorage }?.key
        return position ?? Position(0,0)
    }
    
    func canMoveOnto(position:Position) -> Bool {
        return self[position] == MapTile.empty || self[position] == MapTile.storage
    }
    
    func containsBlock(atPosition position:Position) -> Bool {
        return self[position] == MapTile.block || self[position] == MapTile.blockOnStorage
    }

    mutating func moveMapTile(direction: Direction) {
        let positionOfPerson = self.positionOfPerson()
        let positionToMoveTo = positionOfPerson + direction.move
    
        if canMoveOnto(position:positionToMoveTo) {
            moveMapTile(fromPosition: positionOfPerson, toPostion: positionToMoveTo)
        } else {
            if containsBlock(atPosition:positionToMoveTo) {
                tryAndMoveBlock(fromPosition:positionToMoveTo, direction:direction, positionOfPerson:positionOfPerson)
            }
        }
    }

    mutating func moveMapTile(fromPosition positionToMoveFrom: Position, toPostion positionToMoveTo: Position) {
        guard let mapTile = self[positionToMoveFrom] else { return }
        
        let mapTileForOldPosition = (self[positionToMoveFrom] == mapTile.notOnStorage) ? MapTile.empty : MapTile.storage
        let mapTileForNewPosition = (self[positionToMoveTo] == MapTile.empty) ? mapTile.notOnStorage : mapTile.onStorage
        self[positionToMoveFrom] = mapTileForOldPosition
        self[positionToMoveTo] = mapTileForNewPosition
    }
    
    mutating func tryAndMoveBlock(fromPosition positionOfBlock:Position, direction: Direction, positionOfPerson: Position) {
        let positionAdjacentToBlock = positionOfBlock + direction.move
        if canMoveOnto(position:positionAdjacentToBlock) {
            moveMapTile(fromPosition: positionOfBlock, toPostion: positionAdjacentToBlock)
            moveMapTile(fromPosition: positionOfPerson, toPostion: positionOfBlock)
        }
    }

}

func processSokobanMove(_ listOfStrings:GameArray, _ direction:String) -> GameArray {
    var gameMap = listOfStrings.toGameMap()
    let directionToMove = Direction(string: direction)
    gameMap.moveMapTile(direction:directionToMove)
    return gameMap.toGameArray()
}

func puzzleIsSolved(_ listOfStrings:GameArray) -> Bool {
    return listOfStrings.map{!($0.contains(MapTile.block.text))}.reduce(true){ acc,value in value && acc}
}

class CodeChallenge3Tests: XCTestCase {
    func test_convertString_to_MapTile() {
        let person = MapTile(string:"p")
        let wall = MapTile(string:"#")
        let block = MapTile(string:"b")
        let storage = MapTile(string:"*")
        let personOnStorage = MapTile(string:"P")
        let blockOnStorage = MapTile(string:"B")
        let empty = MapTile(string:" ")
        let empty2 = MapTile(string:"some bad data")
        XCTAssertEqual(MapTile.person,person)
        XCTAssertEqual(MapTile.wall,wall)
        XCTAssertEqual(MapTile.block,block)
        XCTAssertEqual(MapTile.storage,storage)
        XCTAssertEqual(MapTile.personOnStorage,personOnStorage)
        XCTAssertEqual(MapTile.blockOnStorage,blockOnStorage)
        XCTAssertEqual(MapTile.empty,empty)
        XCTAssertEqual(MapTile.empty,empty2)
    }
    
    func testUpdatingMapFromAString() {
        var gameMap1 = GameMap()
        gameMap1.update(atRow:1,using:"p")
        XCTAssertEqual(gameMap1[Position(1,0)],MapTile.person)
        gameMap1.update(atRow:1,using:"# pb*PB")
        XCTAssertEqual(gameMap1[Position(1,0)],MapTile.wall)
        XCTAssertEqual(gameMap1[Position(1,1)],MapTile.empty)
        XCTAssertEqual(gameMap1[Position(1,2)],MapTile.person)
        XCTAssertEqual(gameMap1[Position(1,3)],MapTile.block)
        XCTAssertEqual(gameMap1[Position(1,4)],MapTile.storage)
        XCTAssertEqual(gameMap1[Position(1,5)],MapTile.personOnStorage)
        XCTAssertEqual(gameMap1[Position(1,6)],MapTile.blockOnStorage)
    }
    
    func testArrayOfStringtoGameMap() {
        let arrayOfStrings = ["# p #", "B*Ppb"]
        let gameMap = arrayOfStrings.toGameMap()
        XCTAssertEqual(gameMap[Position(0,0)],MapTile.wall)
        XCTAssertEqual(gameMap[Position(0,1)],MapTile.empty)
        XCTAssertEqual(gameMap[Position(0,2)],MapTile.person)
        XCTAssertEqual(gameMap[Position(0,3)],MapTile.empty)
        XCTAssertEqual(gameMap[Position(0,4)],MapTile.wall)
        XCTAssertEqual(gameMap[Position(1,0)],MapTile.blockOnStorage)
        XCTAssertEqual(gameMap[Position(1,1)],MapTile.storage)
        XCTAssertEqual(gameMap[Position(1,2)],MapTile.personOnStorage)
        XCTAssertEqual(gameMap[Position(1,3)],MapTile.person)
        XCTAssertEqual(gameMap[Position(1,4)],MapTile.block)
    }
    
    func testGameMapToStringAtRow() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.person
        gameMap[Position(1,1)] = MapTile.personOnStorage
        gameMap[Position(1,2)] = MapTile.block
        gameMap[Position(1,3)] = MapTile.blockOnStorage
        gameMap[Position(1,4)] = MapTile.wall
        gameMap[Position(1,5)] = MapTile.storage
        gameMap[Position(1,6)] = MapTile.empty
        gameMap[Position(2,0)] = MapTile.empty
        gameMap[Position(2,1)] = MapTile.person
        gameMap[Position(2,2)] = MapTile.block
        gameMap[Position(2,3)] = MapTile.personOnStorage
        gameMap[Position(2,4)] = MapTile.blockOnStorage
        gameMap[Position(2,5)] = MapTile.storage
        gameMap[Position(2,6)] = MapTile.wall
        XCTAssertEqual("pPbB#* ", gameMap.toString(forRow: 1))
        XCTAssertEqual(["pPbB#* "," pbPB*#"],gameMap.toGameArray())
    }
    
    func testPositionOfPersonInGameMap () {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.block
        gameMap[Position(1,2)] = MapTile.block
        gameMap[Position(1,3)] = MapTile.blockOnStorage
        gameMap[Position(1,4)] = MapTile.wall
        gameMap[Position(1,5)] = MapTile.storage
        gameMap[Position(1,6)] = MapTile.empty
        gameMap[Position(2,0)] = MapTile.empty
        gameMap[Position(2,1)] = MapTile.person
        gameMap[Position(2,2)] = MapTile.block
        gameMap[Position(2,3)] = MapTile.block
        gameMap[Position(2,4)] = MapTile.blockOnStorage
        gameMap[Position(2,5)] = MapTile.storage
        gameMap[Position(2,6)] = MapTile.wall
        XCTAssertEqual(Position(2,1),gameMap.positionOfPerson())
        var gameMap2 = GameMap()
        gameMap2[Position(1,0)] = MapTile.wall
        gameMap2[Position(1,1)] = MapTile.block
        gameMap2[Position(1,2)] = MapTile.block
        gameMap2[Position(1,3)] = MapTile.blockOnStorage
        gameMap2[Position(1,4)] = MapTile.personOnStorage
        gameMap2[Position(1,5)] = MapTile.storage
        gameMap2[Position(1,6)] = MapTile.empty
        gameMap2[Position(2,0)] = MapTile.empty
        gameMap2[Position(2,1)] = MapTile.empty
        gameMap2[Position(2,2)] = MapTile.block
        gameMap2[Position(2,3)] = MapTile.block
        gameMap2[Position(2,4)] = MapTile.blockOnStorage
        gameMap2[Position(2,5)] = MapTile.storage
        gameMap2[Position(2,6)] = MapTile.wall
        XCTAssertEqual(Position(1,4),gameMap2.positionOfPerson())
    }
    
    func testAddingPositionsTogether() {
        XCTAssertEqual(Position(2,3),Position(1,1) + Position(1,2))
        XCTAssertEqual(Position(0,-1),Position(1,1) + Position(-1,-2))
        XCTAssertEqual(Position(4,6),Position(1,2) + Position(3,4))
    }

    func testCanMoveOntoASquareIfItIsEmptyOrIsStorage(){
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.person
        XCTAssertEqual(false,gameMap.canMoveOnto(position:Position(1,0)) )
    
        gameMap[Position(1,1)] = MapTile.personOnStorage
        XCTAssertEqual(false,gameMap.canMoveOnto(position:Position(1,1)) )
    
        gameMap[Position(1,2)] = MapTile.block
        XCTAssertEqual(false,gameMap.canMoveOnto(position:Position(1,2)) )
    
        gameMap[Position(1,3)] = MapTile.blockOnStorage
        XCTAssertEqual(false,gameMap.canMoveOnto(position:Position(1,3)))
    
        gameMap[Position(1,4)] = MapTile.wall
        XCTAssertEqual(false,gameMap.canMoveOnto(position:Position(1,4)) )
    
        gameMap[Position(1,5)] = MapTile.storage
        XCTAssertEqual(true,gameMap.canMoveOnto(position:Position(1,5)) )
    
        gameMap[Position(1,6)] = MapTile.empty
        XCTAssertEqual(true,gameMap.canMoveOnto(position:Position(1,6)) )
    
    }
    func testIfPositionOnGameMapContainsABlock(){
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.person
        XCTAssertEqual(false,gameMap.containsBlock(atPosition:Position(1,0)) )
    
        gameMap[Position(1,2)] = MapTile.block
        XCTAssertEqual(true,gameMap.containsBlock(atPosition:Position(1,2)) )
    
        gameMap[Position(1,3)] = MapTile.blockOnStorage
        XCTAssertEqual(true,gameMap.containsBlock(atPosition:Position(1,3)))
    
        gameMap[Position(1,4)] = MapTile.wall
        XCTAssertEqual(false,gameMap.containsBlock(atPosition:Position(1,4)) )
    
    }

    func testMovingAPersonToAnEmptySquareOnTheGameMap() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
        
        let positionToMoveFrom = Position(1, 2)
        let positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(fromPosition:positionToMoveFrom, toPostion:positionToMoveTo)
        
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.person, gameMap[Position(1,3)])
    }
    
    func testMovingAPersonToAStorageSquareOnTheGameMap() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.storage
        gameMap[Position(1,4)] = MapTile.wall
    
        let positionToMoveFrom = Position(1, 2)
        let positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(fromPosition:positionToMoveFrom, toPostion:positionToMoveTo)

        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.personOnStorage, gameMap[Position(1,3)])
    }

    func testMovingAPersonFromAStorageSquareOnTheGameMap() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.personOnStorage
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
        
        let positionToMoveFrom = Position(1, 2)
        let positionToMoveTo = Position(1,3)
        gameMap.moveMapTile(fromPosition:positionToMoveFrom, toPostion:positionToMoveTo)

        XCTAssertEqual(MapTile.storage, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.person, gameMap[Position(1,3)])
    }
    
    func testMovingAPersonLeft() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
    
        gameMap.moveMapTile(direction:Direction.left)
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,0)])
        XCTAssertEqual(MapTile.person, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,4)])
    }
    
    func testMovingAPersonRight() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
    
        gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.person, gameMap[Position(1,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,4)])
    }
    
    func testMovingAPersonUp() {
        var gameMap = GameMap()
        gameMap[Position(0,0)] = MapTile.wall
        gameMap[Position(0,1)] = MapTile.empty
        gameMap[Position(0,2)] = MapTile.storage
        gameMap[Position(0,3)] = MapTile.empty
        gameMap[Position(0,4)] = MapTile.wall
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
    
        gameMap.moveMapTile(direction:Direction.up)
        XCTAssertEqual(MapTile.wall, gameMap[Position(0,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(0,1)])
        XCTAssertEqual(MapTile.personOnStorage, gameMap[Position(0,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(0,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(0,4)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,4)])
    }
    func testMovingAPersonDown() {
        var gameMap = GameMap()
        gameMap[Position(0,0)] = MapTile.wall
        gameMap[Position(0,1)] = MapTile.empty
        gameMap[Position(0,2)] = MapTile.storage
        gameMap[Position(0,3)] = MapTile.empty
        gameMap[Position(0,4)] = MapTile.wall
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.empty
        gameMap[Position(1,2)] = MapTile.person
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
        gameMap[Position(2,0)] = MapTile.wall
        gameMap[Position(2,1)] = MapTile.empty
        gameMap[Position(2,2)] = MapTile.empty
        gameMap[Position(2,3)] = MapTile.empty
        gameMap[Position(2,4)] = MapTile.wall
        gameMap.moveMapTile(direction:Direction.down)
        XCTAssertEqual(MapTile.wall, gameMap[Position(0,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(0,1)])
        XCTAssertEqual(MapTile.storage, gameMap[Position(0,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(0,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(0,4)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(1,4)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(2,0)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(2,1)])
        XCTAssertEqual(MapTile.person, gameMap[Position(2,2)])
        XCTAssertEqual(MapTile.empty, gameMap[Position(2,3)])
        XCTAssertEqual(MapTile.wall, gameMap[Position(2,4)])
    }

    func testMovingAPersonIntoABlockThatCanMove() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.person
        gameMap[Position(1,2)] = MapTile.block
        gameMap[Position(1,3)] = MapTile.empty
        gameMap[Position(1,4)] = MapTile.wall
    
        gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.empty, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.person, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.block, gameMap[Position(1,3)])
    }
    
    func testMovingAPersonIntoABlockThatCannotMove() {
        var gameMap = GameMap()
        gameMap[Position(1,0)] = MapTile.wall
        gameMap[Position(1,1)] = MapTile.person
        gameMap[Position(1,2)] = MapTile.block
        gameMap[Position(1,3)] = MapTile.block
        gameMap[Position(1,4)] = MapTile.wall
    
        gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.person, gameMap[Position(1,1)])
        XCTAssertEqual(MapTile.block, gameMap[Position(1,2)])
        XCTAssertEqual(MapTile.block, gameMap[Position(1,3)])
    }

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

CodeChallenge3Tests.defaultTestSuite.run()
