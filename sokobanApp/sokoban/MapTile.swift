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
    func place(onMapTile mapTile:MapTile) -> MapTile {
        return MapTile(mapTile)
    }
    var toString:String {
        switch self {
        case is Person: return (self.mapTileUnderneath is Storage) ? K.personOnStorage : K.person
        case is Block: return (self.mapTileUnderneath is Storage) ? K.blockOnStorage : K.block
        case is Wall: return K.wall
        case is Storage: return K.storage
        case is Empty: return K.empty
        default : return K.empty
        }
    }
}

class Empty:MapTile {
}
class Storage:MapTile {
}
class Person:MapTile {
    override func place(onMapTile mapTile:MapTile) -> Person {
        return Person(mapTile)
    }
}
class Block:MapTile {
    override func place(onMapTile mapTile:MapTile) -> Block {
        return Block(mapTile)
    }
}
class Wall:MapTile {
}

class MapTileBuilder{
    static let emptyFloor = MapTileBuilder.create(string:K.empty)
    static let storage = MapTileBuilder.create(string:K.storage)
    static let wall = MapTileBuilder.create(string:K.wall)

    static func create(string:String) -> MapTile {
        switch string {
        case K.empty: return Empty()
        case K.wall: return Wall()
        case K.storage: return Storage()
        case K.person: return Person(emptyFloor)
        case K.personOnStorage: return Person(storage)
        case K.block: return Block(emptyFloor)
        case K.blockOnStorage: return Block(storage)
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
