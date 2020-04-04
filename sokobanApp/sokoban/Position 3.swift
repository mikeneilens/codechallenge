//
//  Position.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

struct Position:Hashable {
    let row:Int
    let column:Int
    init (_ row:Int, _ column:Int) {
        self.row = row
        self.column = column
    }
    static func +(left:Position, right:Position) -> Position {
        return Position(left.row + right.row, left.column + right.column)
    }
}
