//
//  GameMap.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

typealias GameMap = Dictionary<Position, MapTile>

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
        let mapTile = self[positionToMoveFrom]?.notOnStorage ?? MapTile.empty
        let mapTileForOldPosition = (self[positionToMoveFrom] == mapTile) ? MapTile.empty : MapTile.storage
        let mapTileForNewPosition = (self[positionToMoveTo] == MapTile.empty) ? mapTile : mapTile.onStorage
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
