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
        let person = MapTile.from(string:"p")
        let wall = MapTile.from(string:"#")
        let block = MapTile.from(string:"b")
        let storage = MapTile.from(string:"*")
        let personOnStorage = MapTile.from(string:"P")
        let blockOnStorage = MapTile.from(string:"B")
        let empty = MapTile.from(string:" ")
        let empty2 = MapTile.from(string:"some bad data")
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
        gameMap.moveMapTile(toPostion:positionToMoveTo, fromPosition:positionToMoveFrom, mapTile:MapTile.person)
        
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
        gameMap.moveMapTile(toPostion:positionToMoveTo, fromPosition:positionToMoveFrom, mapTile:MapTile.person)
        
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
        gameMap.moveMapTile(toPostion:positionToMoveTo, fromPosition:positionToMoveFrom, mapTile:MapTile.person)
        
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
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
