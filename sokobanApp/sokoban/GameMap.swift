//
//  GameMap2.swift
//  sokoban
//
//  Created by Michael Neilens on 19/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

typealias Grid  = Dictionary<Position, MapTile>

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
                newGrid[Position(row, column)] = MapTileCreator.create(string: stringChar)
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
