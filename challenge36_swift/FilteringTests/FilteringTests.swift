//
//  FilteringTests.swift
//  FilteringTests
//
//  Created by PJ on 01/04/2022.
//

import XCTest
@testable import Filtering

class FilteringTests: XCTestCase {
    
    func testParsingACardIntoCardDto() throws {
        let json = """
           {
              "id": "card3",
              "cardType": "departments",
              "name": "Old departments list",
              "filtering": {
                 "groupId": "departmentGroup",
                 "filters": [
                    {
                       "filter": "osVersionEquals",
                       "value": "13.0.0",
                       "range":{"min":"12.0.0","max":"14.0.0" }
                    }
                 ]
              }
           }
        """
        
        guard let data = json.data(using: .utf8) else {
            struct FailedToConvertJsonToData: Error {}
            throw FailedToConvertJsonToData()
        }
        let card = try JSONDecoder().decode(Dto.Card.self, from: data)
        XCTAssertEqual("card3", card.id)
        XCTAssertEqual("departments", card.cardType)
        XCTAssertEqual("Old departments list", card.name)
        XCTAssertEqual("departmentGroup", card.filtering?.groupId)
        XCTAssertEqual(1, card.filtering?.filters?.count)
        XCTAssertEqual("osVersionEquals", card.filtering?.filters?[0].filter)
        XCTAssertEqual("13.0.0", card.filtering?.filters?[0].value)
        XCTAssertEqual("12.0.0", card.filtering?.filters?[0].range?.min)
        XCTAssertEqual("14.0.0", card.filtering?.filters?[0].range?.max)
    }
    
    func testFilterOsVersionEquals() throws {
        let cardFilter = OsVersionEquals(cardOSVersion: OperatingSystemVersion(majorVersion: 1, minorVersion: 2, patchVersion: 3))
        let userOS = OperatingSystemVersion(majorVersion: 1, minorVersion: 2, patchVersion: 3)
        XCTAssertTrue(cardFilter.shouldInclude(userOS))
    }
    
    func testFilterOsVersionGreaterThan() throws {
        let cardFilter = OsVersionGreaterThan(cardOSVersion: OperatingSystemVersion(majorVersion: 1, minorVersion: 2, patchVersion: 3))
        let userOS = OperatingSystemVersion(majorVersion: 1, minorVersion: 2, patchVersion: 4)
        XCTAssertTrue(cardFilter.shouldInclude(userOS))
    }
    
    func testConvertingDtoCardToCard() throws {
        let dtoFiltering = Dto.Filtering(groupId: "groupId1", filters: [Dto.Filter(filter: "osVersionEquals", value: "1.2.3", range: nil)])
        let dtoCard = Dto.Card(id: "id1", cardType: "cardtype1", name: "name1", filtering: dtoFiltering)
        let card = try createCard(card: dtoCard)
        XCTAssertEqual("id1", card.id)
        XCTAssertEqual("cardtype1", card.cardType)
        XCTAssertEqual("name1", card.name)
        XCTAssertEqual("groupId1", card.filtering.groupId)
        XCTAssertTrue(card.filtering.filters[0] is OsVersionEquals)
        let osVersion = OperatingSystemVersion(majorVersion: 1, minorVersion: 2, patchVersion: 3)
        XCTAssertTrue(card.filtering.filters[0].shouldInclude(osVersion))
    }
    
    func test_whenUserHasNilOperatingSystem() throws {
        guard let input = try readInputFile("input.json") else {
            struct InvalidInputFile: Error {}
            throw InvalidInputFile()
        }
        let cards = try parseJson(input).map(createCard)
        let userDetails = UserDetails(osVersion: nil)
        
        let result = CardsChecker().filterUserDetails(userDetails: userDetails, cards: cards)
        XCTAssertEqual(["Carousel", "Banner", "New departments list default"], result.map { $0.name })
    }
    
    func test_whenUserOperatingSystemIs15_0_0() throws {
        guard let input = try readInputFile("input.json") else {
            struct InvalidInputFile: Error {}
            throw InvalidInputFile()
        }
        let cards = try parseJson(input).map(createCard)
        let userDetails = UserDetails(osVersion: OperatingSystemVersion(majorVersion: 15, minorVersion: 0, patchVersion: 0))
        
        let result = CardsChecker().filterUserDetails(userDetails: userDetails, cards: cards)
        XCTAssertEqual(["Carousel", "Banner", "Recommendations1", "New departments list 15", "Banner2"], result.map { $0.name })
    }
 
    func test_whenUserOperatingSystemIs14_0_1() throws {
        guard let input = try readInputFile("input.json") else {
            struct InvalidInputFile: Error {}
            throw InvalidInputFile()
        }
        let cards = try parseJson(input).map(createCard)
        let userDetails = UserDetails(osVersion: OperatingSystemVersion(majorVersion: 14, minorVersion: 0, patchVersion: 1))
        
        let result = CardsChecker().filterUserDetails(userDetails: userDetails, cards: cards)
        XCTAssertEqual(["Carousel", "Banner", "New departments list default", "Recommendations2"], result.map { $0.name })
    }
 
    func test_whenUserOperatingSystemIs13_0_0() throws {
        guard let input = try readInputFile("input.json") else {
            struct InvalidInputFile: Error {}
            throw InvalidInputFile()
        }
        let cards = try parseJson(input).map(createCard)
        let userDetails = UserDetails(osVersion: OperatingSystemVersion(majorVersion: 13, minorVersion: 0, patchVersion: 0))
        
        let result = CardsChecker().filterUserDetails(userDetails: userDetails, cards: cards)
        XCTAssertEqual(["Carousel", "Banner", "Old departments list"], result.map { $0.name })
    }
 
}

extension FilteringTests {
    func readInputFile(_ filename: String) throws -> String? {
        let bundle = Bundle(for: Self.self)
        guard let url = bundle.url(forResource: filename, withExtension: nil) else {
            struct MissingFile: Error {}
            throw MissingFile()
        }
        
        return try String(contentsOf: url)
    }
}
