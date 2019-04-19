//
//  PlayerMover.swift
//  sokoban
//
//  Created by Michael Neilens on 19/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

protocol MapTileMover {
    func moveMapTile(grid:Grid, direction: Direction) -> Grid
}

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
