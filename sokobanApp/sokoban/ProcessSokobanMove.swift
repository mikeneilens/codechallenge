//
//  ProcessSokobanMove.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

func processSokobanMove(_ listOfStrings:GameArray, _ direction:String) -> GameArray {
    var gameMap = listOfStrings.toGameMap()
    let directionToMove = Direction.from(string: direction)
    gameMap.moveMapTile(direction:directionToMove)
    return gameMap.toGameArray()
}
