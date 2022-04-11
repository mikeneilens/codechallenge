//
//  OperatingSystemVersion+Validation.swift
//  Filtering
//
//  Created by PJ on 01/04/2022.
//

import Foundation

enum ValidationFunction {
    case equals
    case greater
    case lessThan
}

extension OperatingSystemVersion: Equatable {
    public static func == (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion) -> Bool {
        lhs.majorVersion == rhs.majorVersion &&
        lhs.minorVersion == rhs.minorVersion &&
        lhs.patchVersion == rhs.patchVersion
    }
    
    public static func > (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion) -> Bool {
        lhs.majorVersion > rhs.majorVersion ||
        lhs.majorVersion >= rhs.majorVersion && lhs.minorVersion > rhs.minorVersion ||
        lhs.majorVersion >= rhs.majorVersion && lhs.minorVersion >= rhs.minorVersion && lhs.patchVersion > rhs.patchVersion
    }
    
    public static func >= (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion) -> Bool {
        lhs.majorVersion > rhs.majorVersion ||
        lhs.majorVersion >= rhs.majorVersion && lhs.minorVersion >= rhs.minorVersion ||
        lhs.majorVersion >= rhs.majorVersion && lhs.minorVersion >= rhs.minorVersion && lhs.patchVersion >= rhs.patchVersion
    }
    
    public static func < (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion) -> Bool {
        lhs.majorVersion < rhs.majorVersion ||
        lhs.majorVersion <= rhs.majorVersion && lhs.minorVersion < rhs.minorVersion ||
        lhs.majorVersion <= rhs.majorVersion && lhs.minorVersion <= rhs.minorVersion && lhs.patchVersion < rhs.patchVersion
    }
    
    public static func <= (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion) -> Bool {
        lhs.majorVersion < rhs.majorVersion ||
        lhs.majorVersion <= rhs.majorVersion && lhs.minorVersion <= rhs.minorVersion ||
        lhs.majorVersion <= rhs.majorVersion && lhs.minorVersion <= rhs.minorVersion && lhs.patchVersion <= rhs.patchVersion
    }
    
    public static func > (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion?) -> Bool {
        guard let rhs = rhs else { return false }
        return lhs > rhs
    }
    
    public static func >= (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion?) -> Bool {
        guard let rhs = rhs else { return false }
        return lhs >= rhs
    }
    
    public static func < (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion?) -> Bool {
        guard let rhs = rhs else { return false }
        return lhs < rhs
    }
    
    public static func <= (lhs: OperatingSystemVersion, rhs: OperatingSystemVersion?) -> Bool {
        guard let rhs = rhs else { return false }
        return lhs <= rhs
    }
    
    func validateMajor(value: OperatingSystemVersion?, type: ValidationFunction) -> Bool {
        guard let value = value else { return false }
        switch type {
        case .equals:
            return majorVersion == value.majorVersion
        case .greater:
            return majorVersion > value.majorVersion
        case .lessThan:
            return majorVersion < value.majorVersion
        }
    }
    
    func validateMinor(value: OperatingSystemVersion?, type: ValidationFunction) -> Bool {
        guard let value = value else { return false }
        switch type {
        case .equals:
            return majorVersion == value.majorVersion && minorVersion == value.minorVersion
        case .greater:
            return majorVersion > value.majorVersion && minorVersion > value.minorVersion
        case .lessThan:
            return majorVersion < value.majorVersion && minorVersion < value.minorVersion
        }
    }
    
    init?(value: String?) {
        guard let value = value else { return nil }
        let components = value.split(separator: ".").map { Int(String($0)) }
        guard components.count == 3 else { return nil }
        self.init(majorVersion: components[0] ?? 0, minorVersion: components[1] ?? 0, patchVersion: components[2] ?? 0)
    }
}


