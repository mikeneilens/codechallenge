//
//  ProcessSokobanMove.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

func processSokobanMove(_ listOfStrings:GameArray, _ direction:String) -> GameArray {
    let gameMap = GameMap(gameArray: listOfStrings, mapTileMover: PlayerMover())
    let directionToMove = Direction(string: direction)
    let updatedGameMap = gameMap.moveMapTile(direction:directionToMove)
    return updatedGameMap.toGameArray()
}
