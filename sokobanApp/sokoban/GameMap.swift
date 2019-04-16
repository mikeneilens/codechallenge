//
//  GameMap2.swift
//  sokoban
//
//  Created by Michael Neilens on 16/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

struct GameMap {
    private var grid = Dictionary<Position, MapTile>()

    subscript(position:Position) -> MapTile? {
        return grid[position]
    }
    
    mutating func add(string:String) {
        let lastRow = grid.keys.map{$0.row}.sorted().last ?? -1
        let stringChars = string.map{String($0)}
        for (column, stringChar) in stringChars.enumerated() {
            grid[Position(lastRow + 1,column )] = MapTile(string: stringChar)
        }
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
            .map{(position, mapTile) in return mapTile.text}
            .reduce("",+)
    }
    
    func positionOfPerson() -> Position {
        let position = grid.first{(position, mapTile) in mapTile == MapTile.person || mapTile == MapTile.personOnStorage }?.key
        return position ?? Position(0,0)
    }
    
    private func canMoveOnto(position:Position) -> Bool {
        return grid[position] == MapTile.empty || grid[position] == MapTile.storage
    }
    
    private func containsBlock(atPosition position:Position) -> Bool {
        return grid[position] == MapTile.block || grid[position] == MapTile.blockOnStorage
    }
    
    mutating func moveMapTile(direction: Direction)  {
        let positionOfPerson = self.positionOfPerson()
        let positionToMoveTo = positionOfPerson + direction.move
        
        switch (true) {
            case canMoveOnto(position: positionToMoveTo) :
                moveMapTile(fromPosition: positionOfPerson, toPostion: positionToMoveTo)
            case containsBlock(atPosition: positionToMoveTo) :
                tryAndMoveBlock(fromPosition: positionToMoveTo, direction: direction, positionOfPerson: positionOfPerson)
            default: break
        }        
    }
    
    private mutating func moveMapTile(fromPosition positionToMoveFrom: Position, toPostion positionToMoveTo: Position) {
        guard let mapTile = grid[positionToMoveFrom] else { return }
        
        let mapTileForOldPosition = (grid[positionToMoveFrom] == mapTile.notOnStorage) ? MapTile.empty : MapTile.storage
        let mapTileForNewPosition = (grid[positionToMoveTo] == MapTile.empty) ? mapTile.notOnStorage : mapTile.onStorage
        grid[positionToMoveFrom] = mapTileForOldPosition
        grid[positionToMoveTo] = mapTileForNewPosition
    }
    
    private mutating func tryAndMoveBlock(fromPosition positionOfBlock:Position, direction: Direction, positionOfPerson: Position) {
        let positionAdjacentToBlock = positionOfBlock + direction.move
        if canMoveOnto(position:positionAdjacentToBlock) {
            moveMapTile(fromPosition: positionOfBlock, toPostion: positionAdjacentToBlock)
            moveMapTile(fromPosition: positionOfPerson, toPostion: positionOfBlock)
        }
    }
    
}
