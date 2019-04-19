//
//  MapTile2.swift
//  sokoban
//
//  Created by Michael Neilens on 19/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation
import UIKit

class MapTile:Equatable {
    let mapTileUnderneath:MapTile?
    
    init() {self.mapTileUnderneath = nil}
    init( _ onMapTile:MapTile? = nil) { self.mapTileUnderneath = onMapTile}
    
    static func == (lhs: MapTile, rhs: MapTile) -> Bool {
        return type(of:lhs) === type(of:rhs)
    }
    //override this if it is needed
    func place(onMapTile mapTile:MapTile) -> MapTile {
        return MapTile(mapTile)
    }
    //override this
    var toString:String { return Empty.identifier }
}

class Empty:MapTile {
    static let identifier = " "
    override var toString: String { return Empty.identifier}
}
class Storage:MapTile {
    static let identifier = "*"
    override var toString: String { return Storage.identifier}
}
class Person:MapTile {
    static let identifier = "p"
    static let onStorageIdentifier = "P"
    override var toString: String {return (self.mapTileUnderneath is Storage) ? Person.onStorageIdentifier : Person.identifier}
    
    override func place(onMapTile mapTile:MapTile) -> Person {
        return Person(mapTile)
    }
}
class Block:MapTile {
    static let identifier = "b"
    static let onStorageIdentifier = "B"
    override var toString: String {return (self.mapTileUnderneath is Storage) ? Block.onStorageIdentifier : Block.identifier}
    
    override func place(onMapTile mapTile:MapTile) -> Block {
        return Block(mapTile)
    }
}
class Wall:MapTile {
    static let identifier = "#"
    override var toString: String { return Wall.identifier}
}

class MapTileCreator{
    static let emptyFloor = MapTileCreator.create(string:Empty.identifier)
    static let storage = MapTileCreator.create(string:Storage.identifier)
    static let wall = MapTileCreator.create(string:Wall.identifier)

    static func create(string:String) -> MapTile {
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

extension MapTile {
    var image:String {
        switch self {
        case is Person: return "Person"
        case is Block: return "Block"
        case is Wall: return "Wall"
        case is Storage: return "Storage"
        case is Empty: return ""
        default : return ""
        }
    }
    var backgroundColor:UIColor {
        switch self {
        case is Person: return (self.mapTileUnderneath is Storage) ? UIColor.red : UIColor.clear
        case is Block: return (self.mapTileUnderneath is Storage) ? UIColor.green : UIColor.clear
        default: return UIColor.clear
        }
    }
}
