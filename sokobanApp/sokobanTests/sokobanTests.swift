//
//  sokobanTests.swift
//  sokobanTests
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import XCTest
@testable import sokoban

class sokobanTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    
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
        let gameMap = GameMap(gameArray: ["p",
                                          "# pb*PB"], mapTileMover: PlayerMover())
        XCTAssertEqual(gameMap[1,0],MapTile.wall)
        XCTAssertEqual(gameMap[1,1],MapTile.empty)
        XCTAssertEqual(gameMap[1,2],MapTile.person)
        XCTAssertEqual(gameMap[1,3],MapTile.block)
        XCTAssertEqual(gameMap[1,4],MapTile.storage)
        XCTAssertEqual(gameMap[1,5],MapTile.personOnStorage)
        XCTAssertEqual(gameMap[1,6],MapTile.blockOnStorage)
    }

    func testInitialisingGameMapWithArrayofStrings() {
        let arrayOfStrings = ["# p #",
                              "B*Ppb"]
        let gameMap = GameMap(gameArray: arrayOfStrings, mapTileMover: PlayerMover() )
        XCTAssertEqual(gameMap[0,0],MapTile.wall)
        XCTAssertEqual(gameMap[0,1],MapTile.empty)
        XCTAssertEqual(gameMap[0,2],MapTile.person)
        XCTAssertEqual(gameMap[0,3],MapTile.empty)
        XCTAssertEqual(gameMap[0,4],MapTile.wall)
        XCTAssertEqual(gameMap[1,0],MapTile.blockOnStorage)
        XCTAssertEqual(gameMap[1,1],MapTile.storage)
        XCTAssertEqual(gameMap[1,2],MapTile.personOnStorage)
        XCTAssertEqual(gameMap[1,3],MapTile.person)
        XCTAssertEqual(gameMap[1,4],MapTile.block)
    }

    
    func testGameMapToStringAtRow() {
        let gameMap = GameMap(gameArray: ["pPbB#* ",
                                          " pbPB*#"], mapTileMover: PlayerMover())
        XCTAssertEqual(["pPbB#* "," pbPB*#"],gameMap.toGameArray())
    }
    
    func testAddingsTogetherPosition() {
        XCTAssertEqual(Position(2,3),Position(1,1) + Position(1,2))
        XCTAssertEqual(Position(0,-1),Position(1,1) + Position(-1,-2))
        XCTAssertEqual(Position(4,6),Position(1,2) + Position(3,4))
    }
    
    func testMovingAPersonToAnEmptySquareOnTheGameMap() {
        let gameMap = GameMap(gameArray: ["# p #"], mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,3])
    }
    
    func testMovingAPersonToAStorageSquareOnTheGameMap() {
        let gameMap = GameMap(gameArray:["# p*#"] , mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.personOnStorage, updatedGameMap[0,3])
    }
    
    func testMovingAPersonFromAStorageSquareOnTheGameMap() {
        let gameMap = GameMap(gameArray: ["# P #"], mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction: Direction.right)
        
        XCTAssertEqual(MapTile.storage, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,3])
    }
    
    func testMovingAPersonLeft() {
        let gameMap = GameMap(gameArray: ["# p #"], mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction: Direction.left)

        XCTAssertEqual(MapTile.wall, updatedGameMap[0,0])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,4])
    }
    
    func testMovingAPersonRight() {
        let gameMap = GameMap(gameArray: ["# p #"], mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,4])
    }
    
    func testMovingAPersonUp() {
        let gameMap = GameMap(gameArray: ["# * #",
                                          "# p #"], mapTileMover: PlayerMover())
        
        let updatedGameMap = gameMap.moveMapTile(direction:Direction.up)
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.personOnStorage, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,4])
        XCTAssertEqual(MapTile.wall, updatedGameMap[1,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,1])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[1,4])
    }
    func testMovingAPersonDown() {
        let gameMap = GameMap(gameArray: ["# * #",
                                          "# p #",
                                          "#   #"], mapTileMover: PlayerMover())

        let updatedGameMap = gameMap.moveMapTile(direction:Direction.down)
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.storage, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[0,4])
        XCTAssertEqual(MapTile.wall, updatedGameMap[1,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,1])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[1,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[1,4])
        XCTAssertEqual(MapTile.wall, updatedGameMap[2,0])
        XCTAssertEqual(MapTile.empty, updatedGameMap[2,1])
        XCTAssertEqual(MapTile.person, updatedGameMap[2,2])
        XCTAssertEqual(MapTile.empty, updatedGameMap[2,3])
        XCTAssertEqual(MapTile.wall, updatedGameMap[2,4])
    }
    
    func testMovingAPersonIntoABlockThatCanMove() {
        let gameMap = GameMap(gameArray: ["#pb #"], mapTileMover: PlayerMover())

        let updatedGameMap = gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.block, updatedGameMap[0,3])
    }
    
    func testMovingAPersonIntoABlockThatCannotMove() {
        let gameMap = GameMap(gameArray: ["#pbb "], mapTileMover: PlayerMover())
        
        let updatedGameMap = gameMap.moveMapTile(direction:Direction.right)
        XCTAssertEqual(MapTile.person, updatedGameMap[0,1])
        XCTAssertEqual(MapTile.block, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.block, updatedGameMap[0,3])
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
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
