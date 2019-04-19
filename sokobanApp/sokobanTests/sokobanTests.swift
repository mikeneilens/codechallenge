//
//  sokonanTests.swift
//  sokobanTests
//
//  Created by Michael Neilens on 19/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation
import XCTest
@testable import sokoban

class testsOnGameMap: XCTestCase {
    
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
