//
//  sokobanTests.swift
//  sokobanTests
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import XCTest
@testable import sokoban

class testsOnGameMap: XCTestCase {
    
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
    
    func testMovingAPersonToAnEmptySquareOnGameMapUsungPlayerMover() {
        let gameMap = GameMap(gameArray:["# p #"], mapTileMover: PlayerMover())
        let updatedGameMap = gameMap.moveMapTile(direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGameMap[0,2])
        XCTAssertEqual(MapTile.person, updatedGameMap[0,3])
    }
    
}

class processSokobanMoveTests:XCTestCase {
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

class testsOnPosition:XCTestCase {
    func testAddingsTogetherPosition() {
        XCTAssertEqual(Position(2,3),Position(1,1) + Position(1,2))
        XCTAssertEqual(Position(0,-1),Position(1,1) + Position(-1,-2))
        XCTAssertEqual(Position(4,6),Position(1,2) + Position(3,4))
    }
}

class testsOnPlayerMover: XCTestCase {
    func testMovingAPersonToAnEmptySquareOnGrid() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.person
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,3)])
    }
    
    func testMovingAPersonToAStorageSquareOnGrid() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.person
        grid[Position(0,3)] = MapTile.storage
        grid[Position(0,4)] = MapTile.wall
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.personOnStorage, updatedGrid[Position(0,3)])
    }
    
    func testMovingAPersonFromAStorageSquareOnGrid() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.personOnStorage
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.storage, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,3)])
    }
    
    func testMovingAPersonLeft() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.person
        grid[Position(0,3)] = MapTile.storage
        grid[Position(0,4)] = MapTile.wall
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.left)
        
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,1)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,2)])
    }
    
    func testMovingAPersonRight() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.person
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,3)])
        
    }
    
    func testMovingAPersonUp() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.storage
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        grid[Position(1,0)] = MapTile.wall
        grid[Position(1,1)] = MapTile.empty
        grid[Position(1,2)] = MapTile.person
        grid[Position(1,3)] = MapTile.empty
        grid[Position(1,4)] = MapTile.wall
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.up)
        
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(0,0)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,1)])
        XCTAssertEqual(MapTile.personOnStorage, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,3)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(0,4)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(1,0)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,1)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,2)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,3)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(1,4)])
    }
    func testMovingAPersonDown() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.empty
        grid[Position(0,2)] = MapTile.storage
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        grid[Position(1,0)] = MapTile.wall
        grid[Position(1,1)] = MapTile.empty
        grid[Position(1,2)] = MapTile.person
        grid[Position(1,3)] = MapTile.empty
        grid[Position(1,4)] = MapTile.wall
        grid[Position(2,0)] = MapTile.wall
        grid[Position(2,1)] = MapTile.empty
        grid[Position(2,2)] = MapTile.empty
        grid[Position(2,3)] = MapTile.empty
        grid[Position(2,4)] = MapTile.wall
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.down)
        
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(0,0)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,1)])
        XCTAssertEqual(MapTile.storage, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,3)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(0,4)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(1,0)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,1)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,2)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(1,3)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(1,4)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(2,0)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(2,1)])
        XCTAssertEqual(MapTile.person, updatedGrid[Position(2,2)])
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(2,3)])
        XCTAssertEqual(MapTile.wall, updatedGrid[Position(2,4)])
        
    }
    
    func testMovingAPersonIntoABlockThatCanMove() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.person
        grid[Position(0,2)] = MapTile.block
        grid[Position(0,3)] = MapTile.empty
        grid[Position(0,4)] = MapTile.wall
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.empty, updatedGrid[Position(0,1)])
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.block, updatedGrid[Position(0,3)])
    }
    
    func testMovingAPersonIntoABlockThatCannotMove() {
        var grid = Grid()
        grid[Position(0,0)] = MapTile.wall
        grid[Position(0,1)] = MapTile.person
        grid[Position(0,2)] = MapTile.block
        grid[Position(0,3)] = MapTile.block
        grid[Position(0,4)] = MapTile.empty
        grid[Position(0,5)] = MapTile.wall
        
        let updatedGrid = PlayerMover().moveMapTile(grid: grid, direction: Direction.right)
        
        XCTAssertEqual(MapTile.person, updatedGrid[Position(0,1)])
        XCTAssertEqual(MapTile.block, updatedGrid[Position(0,2)])
        XCTAssertEqual(MapTile.block, updatedGrid[Position(0,3)])
    }
}
