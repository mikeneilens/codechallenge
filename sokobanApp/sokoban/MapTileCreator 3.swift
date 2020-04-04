//
//  MapTileCreator.swift
//  sokoban
//
//  Created by Michael Neilens on 19/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

class MapTileCreator{
    static let emptyFloor = MapTileCreator.create(using: Empty.identifier)
    static let storage = MapTileCreator.create(using: Storage.identifier)
    static let wall = MapTileCreator.create(using: Wall.identifier)
    
    static func create(using string:String) -> MapTile {
        switch string {
        case Empty.identifier: return Empty()
        case Wall.identifier: return Wall()
        case Storage.identifier: return Storage()
        case Person.identifier: return Person(emptyFloor)
        case Person.onStorageIdentifier: return Person(storage)
        case Block.identifier: return Block(emptyFloor)
        case Block.onStorageIdentifier: return Block(storage)
        default: return Empty()
        }
    }
}
