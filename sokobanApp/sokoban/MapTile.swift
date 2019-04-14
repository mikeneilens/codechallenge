//
//  MapTile.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation
import UIKit
enum MapTile:String {
    case person = "p"
    case wall = "#"
    case block = "b"
    case storage = "*"
    case personOnStorage = "P"
    case blockOnStorage = "B"
    case empty = " "
    
    static func from(string:String) -> MapTile {
        return MapTile(rawValue:string) ?? MapTile.empty
    }
    var onStorage:MapTile {
        switch self {
        case .person: return .personOnStorage
        case .block: return .blockOnStorage
        default: return self
        }
    }
}
extension MapTile {
    var image:String {
        switch self {
        case .person,.personOnStorage: return "Person"
        case .block, .blockOnStorage: return "Block"
        case .wall: return "Wall"
        case .storage: return "Storage"
        case .empty: return ""
        }
    }
    var backgroundColor:UIColor {
        switch self {
        case .personOnStorage: return UIColor.red
        case .blockOnStorage: return UIColor.green
        default: return UIColor.clear
        }
    }

}
