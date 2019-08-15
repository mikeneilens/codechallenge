//
//  BoardLocation.swift
//  challenge10
//
//  Created by Michael Neilens on 15/08/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

struct BoardLocation {
    
    let currentLocation:Location
    
    let hasPassedGo:Bool
    
    init() {
        self.currentLocation = go
        self.hasPassedGo = false
    }
    
    init(location:Location, _hasPassedGo:Bool = false) {
        self.currentLocation = location
        self.hasPassedGo = _hasPassedGo
    }
    
    func move(using dice:Dice) -> BoardLocation {
        var movesLeft = dice.total
        var location = currentLocation
        var passingGo = false

        while movesLeft > 0 {
            location = location.ahead(by: 1)
            if location == go { passingGo = true }
            movesLeft -= 1
        }
        
        return BoardLocation(location:location, _hasPassedGo: passingGo)
    }
}
