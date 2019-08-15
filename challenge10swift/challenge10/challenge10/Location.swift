//
//  Location.swift
//  challenge10
//
//  Created by Michael Neilens on 15/08/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

protocol Location {}

protocol Purchaseable:Location {
    var purchasePrice:GBP { get }
}

protocol Buildable:Purchaseable {
    var miniStore:DevelopmentType.BuildCostAndRent { get }
    var supermarket:DevelopmentType.BuildCostAndRent { get }
    var megaStore:DevelopmentType.BuildCostAndRent { get }
}

struct FreeParking:Location {}

struct Go:Location {
    let fee = GBP(100)
}

struct FactoryOrWarehouse:Purchaseable{
    let name:String
    let purchasePrice:GBP = GBP(100)
    let rent = GBP(20)
}

struct RetailSite:Buildable {
    let group:Group
    let name:String
    let purchasePrice:GBP
    let undeveloped:DevelopmentType.RentOnly
    let miniStore:DevelopmentType.BuildCostAndRent
    let supermarket:DevelopmentType.BuildCostAndRent
    let megaStore:DevelopmentType.BuildCostAndRent
}
enum Group {
    case Red
    case Green
    case Orange
}
    
struct DevelopmentType {
    struct RentOnly {let rent:GBP}
    struct BuildCostAndRent{let buildCost:GBP; let rent:GBP}
}
