//
//  Direction.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

enum Direction {
    case left
    case right
    case up
    case down
    
    var move:Position {
        switch self {
        case .left: return Position(0,-1)
        case .right: return Position(0,1)
        case .up: return Position(-1,0)
        case .down: return Position(1,0)
        }
    }
    static func from(string:String) -> Direction {
        switch string {
        case "U" :return Direction.up
        case "D" :return Direction.down
        case "L" :return Direction.left
        case "R" :return Direction.right
        default: return Direction.right
        }
    }
}
