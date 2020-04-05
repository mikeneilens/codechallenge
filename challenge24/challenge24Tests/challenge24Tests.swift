//
//  challenge24Tests.swift
//  challenge24Tests
//
//  Created by Michael Neilens on 04/04/2020.
//  Copyright © 2020 Michael Neilens. All rights reserved.
//

import XCTest
@testable import challenge24

class challenge24Tests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testStringRank() throws {
        XCTAssertEqual(2, "2A".rank)
        XCTAssertEqual(9, "9C".rank)
        XCTAssertEqual(10, "TS".rank)
        XCTAssertEqual(11, "JH".rank)
        XCTAssertEqual(12, "QS".rank)
        XCTAssertEqual(13, "KS".rank)
        XCTAssertEqual(14, "AS".rank)
    }
    func testStringSuit() throws {
        XCTAssertEqual("C", "AC".suit)
        XCTAssertEqual("D", "4D".suit)
        XCTAssertEqual("H", "8H".suit)
        XCTAssertEqual("S", "TS".suit)
    }
    func testGetPair() throws {
        XCTAssertNil(getPair(cards:["2C","3C","JD","5D","7H","6S"]))
        XCTAssertEqual(["2C","2D","7H","6S","5D"], getPair(cards:["2C","3C","2D","5D","7H","6S"]))
    }
    func testGetTwoPair() throws {
        XCTAssertNil(getTwoPair(cards:["2C","4C","5D","6D","3H","6S"]))
        XCTAssertEqual(["8C","8D","7H","7C","TS"], getTwoPair(cards:["8C","4C","8D","6D","7H","TS","7C"]))
        XCTAssertEqual(["TC","TS","8C","8D","7H"], getTwoPair(cards:["8C","TC","8D","6D","7H","TS","7C"]))
    }
    func testGetThree() throws {
        XCTAssertNil(getThree(cards:["2C","3C","3D","5D","7H","6S"]))
        XCTAssertEqual(["2C","2D","2S"], getThree(cards:["2C","3C","2D","5D","7H","2S"]))
    }
    func testGetStraight() throws {
        XCTAssertNil(getStraight(cards:["2C","4C","5D","6D","7H","6S"]))
        XCTAssertEqual(["8C","7H","6D","5D","4C"], getStraight(cards:["8C","4C","5D","6D","7H","TS","JC"]))
        XCTAssertEqual(["TS","9C","8C","7H","6D"], getStraight(cards:["8C","4C","5D","6D","7H","TS","9C"]))
    }
    func testGetAceLowStraight() throws {
        XCTAssertNil(getAceLowStraight(cards:["AC","KC","QD","JD","TS","6S","7S"]))
        XCTAssertEqual(["KC","QD","JD","TS","9S",], getAceLowStraight(cards:["AC","KC","QD","JD","TS","6S","9S"]))
        XCTAssertEqual(["5D","4C","3D","2H","AS"], getAceLowStraight(cards:["8C","4C","5D","3D","2H","AS","9C"]))
    }
    func testHighestCard() throws {
        XCTAssertEqual(["AC","KC","QD","JD","TS"], getHighestCard(cards:["QD","TS","AC","6S","JD","7S","KC"]))
    }
    func testCardValue() throws {
        XCTAssertEqual("100403", ["TC","4H","3C"].value)
        XCTAssertEqual("091011121314", ["9D","TC","JH","QS","KH","AS"].value)
        XCTAssertTrue(["2C","2D","8H","7S","5D"].value > ["2C","2D","8S","6H","5S"].value  )
    }
    func testBestCard() throws {
        XCTAssertEqual( Hand(cards: ["AC","KC","QD","JD","8S"], handRank: 0) , ["QD","8S","AC","6S","JD","7S","KC"].bestHand() )
        XCTAssertEqual( Hand(cards: ["AC","KC","QD","JD","TS"], handRank: 5) , ["QD","TS","AC","6S","JD","7S","KC"].bestHand() )
    }
    func testHandGreaterThan() throws {
        XCTAssertTrue( Hand(cards: ["AC","KC","QD","JD","TS"], handRank: 5) > Hand(cards: ["AC","KC","QD","JD","8S"], handRank: 0) )
        XCTAssertTrue( Hand(cards: ["KC","QD","JD","TS","8S"], handRank: 0) > Hand(cards: ["KC","QD","JD","9S","8S"], handRank: 0) )
        XCTAssertTrue( Hand(cards: ["AC","KC","QD","JD","TS"], handRank: 5) > Hand(cards: ["KC","QD","JD","TS","9H"], handRank: 5) )
    }
    
    func testRemoveDuplicateRank() throws {
        XCTAssertEqual(["3D","6C","TC"], ["TC","3D","6C"].removeDuplicateRank())
        XCTAssertEqual(["3D","6C","TC"], ["TC","3D","6C","TC","3D","6C"].removeDuplicateRank())
    }
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
