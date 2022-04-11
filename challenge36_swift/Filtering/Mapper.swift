//
//  Mapper.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//
//  Converts the DTO into a Card
//
import Foundation

func createCard(card:Dto.Card) throws -> Card {
    let id = card.id
    let cardType = card.cardType
    let name = card.name
    if let filtering = card.filtering {
        let groupId = filtering.groupId
        let filters = try filtering.filters?.map(toFilter) ?? []
        let filtering = Filtering(groupId: groupId, filters: filters)
        return Card(id: id, cardType: cardType, name: name, filtering: filtering)
    } else {
        return Card(id: id, cardType: cardType, name: name, filtering: Filtering(groupId: nil, filters: []))
    }
}

enum ParseError: Error {
    case IllegalOsVersion(message: String)
    case IllegalFilter(message: String)
}

func toFilter(dtoFilter:Dto.Filter) throws -> Filter {
    switch(dtoFilter.filter) {
    case "controlGroup": return ControlGroup()
    case "osVersionEquals": return OsVersionEquals(cardOSVersion: try operatingSystemValue(dtoFilter: dtoFilter))
    case "osVersionGreaterThan": return OsVersionGreaterThan(cardOSVersion: try operatingSystemValue(dtoFilter: dtoFilter))
    default: throw ParseError.IllegalFilter(message: "filter \(dtoFilter) has an invalid name.")
    }
}

func operatingSystemValue(dtoFilter: Dto.Filter ) throws -> OperatingSystemVersion {
    guard let value = dtoFilter.value else {throw ParseError.IllegalFilter(message: "filter \(dtoFilter.filter) had no value for operating system")}
    return try value.toOperatingSystemVersion()
}

extension String {
    func toOperatingSystemVersion() throws -> OperatingSystemVersion {
        let parts = self.split(separator: ".")
        if parts.count != 3 {
            throw ParseError.IllegalOsVersion(message: "Operating systems \(self) does not contain three integers seperated by periods.")
        }
        guard let major = Int(parts[0]) else {throw ParseError.IllegalOsVersion(message: "Operating systems \(self) does not contain three integers seperated by periods.")}
        guard let minor = Int(parts[1]) else {throw ParseError.IllegalOsVersion(message: "Operating systems \(self) does not contain three integers seperated by periods.")}
        guard let patch = Int(parts[2]) else {throw ParseError.IllegalOsVersion(message: "Operating systems \(self) does not contain three integers seperated by periods.")}
        return OperatingSystemVersion(majorVersion: major, minorVersion: minor, patchVersion: patch)
    }
}
