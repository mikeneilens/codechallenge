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
    init(string:String) {
        switch string {
        case "U" : self = Direction.up
        case "D" : self = Direction.down
        case "L" : self = Direction.left
        case "R" : self = Direction.right
        default:  self = Direction.right
        }
    }
}
