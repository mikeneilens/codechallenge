//
//  GameArray.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

typealias GameArray = Array<String>

extension GameArray {
    func toGameMap() -> GameMap {
        var gameMap = GameMap()
        for string in self {
            gameMap.add(string: string)
        }
        return gameMap
    }
}
