//
//  PuzzleIsSolved.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

func puzzleIsSolved(_ listOfStrings:GameArray) -> Bool {
    return listOfStrings.map{!($0.contains("b"))}.reduce(true){ acc,value in value && acc}
}
