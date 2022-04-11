//
//  Models.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//

import Foundation

struct UserDetails{
    let osVersion:OperatingSystemVersion?
}

struct Card{
    let id:String
    let cardType:String
    let name:String
    let filtering:Filtering
}

struct Filtering{
    let groupId:String?
    let filters:Array<Filter>
    
    func containsControlGroup() -> Bool {filters.contains{$0 is ControlGroup}}
}

protocol Filter {
    var shouldInclude: (OperatingSystemVersion?) -> Bool {get}
}

struct ControlGroup: Filter {
    let shouldInclude:(OperatingSystemVersion?) -> Bool  = { _ in true }
}

struct OsVersionEquals: Filter {
    let shouldInclude:(OperatingSystemVersion?) -> Bool
    
    init (cardOSVersion: OperatingSystemVersion?) {
        shouldInclude = { userOsVersion in
            guard let cardOsVersion = cardOSVersion, let userOsVersion = userOsVersion else {return false}
            return cardOsVersion == userOsVersion }
    }
}

struct OsVersionGreaterThan: Filter {
    let shouldInclude:(OperatingSystemVersion?) -> Bool
    
    init (cardOSVersion: OperatingSystemVersion?) {
        shouldInclude = { userOsVersion in
            guard let cardOsVersion = cardOSVersion, let userOsVersion = userOsVersion else {return false}
            return userOsVersion > cardOsVersion}
    }
}

