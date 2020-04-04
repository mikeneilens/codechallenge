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
        XCTAssertEqual(["8C","8D","7C","7H","TS"], getTwoPair(cards:["8C","4C","8D","6D","7H","TS","7C"]))
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
