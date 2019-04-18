//
//  MapTileMover.swift
//  sokoban
//
//  Created by Michael Neilens on 17/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

protocol MapTileMover {
    func moveMapTile(grid:Grid, direction: Direction) -> Grid
}

struct PlayerMover:MapTileMover {
    
    func moveMapTile(grid:Grid, direction: Direction) -> Grid {
        let positionOfPerson = self.positionOfPerson(grid:grid)
        let positionAdjacentToPlayer = positionOfPerson + direction.move
        
        let updatedGrid = tryAndMoveTile(fromPosition: positionOfPerson, direction: direction, onGrid: grid)
        
        switch (true) {
            case updatedGrid != grid: return updatedGrid
            case containsBlock(atPosition: positionAdjacentToPlayer, grid:grid):
                let updatedGrid = tryAndMoveBlockAndPerson(positionOfPerson: positionOfPerson, direction: direction, onGrid:grid)
                return updatedGrid
            default: return grid
        }
    }

    private func positionOfPerson(grid:Grid) -> Position {
        let position = grid.first{(position, mapTile) in mapTile == MapTile.person || mapTile == MapTile.personOnStorage }?.key
        return position ?? Position(0,0)
    }
    
    private func canMoveOnto(position:Position, grid:Grid) -> Bool {
        return grid[position] == MapTile.empty || grid[position] == MapTile.storage
    }
    
    private func containsBlock(atPosition position:Position, grid:Grid) -> Bool {
        return grid[position] == MapTile.block || grid[position] == MapTile.blockOnStorage
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
        if canMoveOnto(position:adjacentPosition, grid:grid) {
            return moveMapTile(from: fromPosition, to: adjacentPosition, onGrid: grid)
        } else {
            return grid
        }
    }
    
    private func moveMapTile(from positionToMoveFrom: Position, to positionToMoveTo: Position, onGrid grid:Grid) -> Grid {
        guard let mapTile = grid[positionToMoveFrom] else { return grid }
        
        let mapTileForOldPosition = (grid[positionToMoveFrom] == mapTile.notOnStorage) ? MapTile.empty : MapTile.storage
        let mapTileForNewPosition = (grid[positionToMoveTo] == MapTile.empty) ? mapTile.notOnStorage : mapTile.onStorage
        
        var resultingGrid = grid
        resultingGrid[positionToMoveFrom] = mapTileForOldPosition
        resultingGrid[positionToMoveTo] = mapTileForNewPosition
        return resultingGrid
    }
}
