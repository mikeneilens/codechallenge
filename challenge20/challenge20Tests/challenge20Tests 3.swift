//
//  challenge20Tests.swift
//  challenge20Tests
//
//  Created by Michael Neilens on 10/02/2020.
//  Copyright © 2020 Michael Neilens. All rights reserved.
//

import XCTest
@testable import challenge20

class challenge20Tests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testMakingHttpRequest() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        let request = Request(refId:"", command:"",repeats:1)
        
        let expect = expectation(description: "SomeService does stuff and runs the callback closure")

        let completion = {(data:String) in
            print("Data: \(data)")
            XCTAssertTrue(!data.isEmpty, "the http request worked")
            expect.fulfill()
        }

        makeHttpRequest( forRequest:request,completion:completion)
        
        waitForExpectations(timeout: 1) { error in
          if let error = error {
            XCTFail("waitForExpectationsWithTimeout errored: \(error)")
          }
        }
    }
    
    func testGetRefidWithEmptyString() {
        let data = ""
        XCTAssertEqual("", getRefId(response:data))
    }
    func testGetRefidWithNoPlaces() {
        let data = "something"
        XCTAssertEqual("something", getRefId(response:data))
    }
    func testGetRefidWithSeveralPlaces() {
        let data = "something,O,OL,ORL"
        XCTAssertEqual("something", getRefId(response:data))
    }
    
    func testGetPlacesWithEmptyString() {
        let data = ""
        XCTAssertEqual([], getPlaces(response:data))
    }
    func testGetPlacesWithNoPlaces() {
        let data = "something"
        XCTAssertEqual([], getPlaces(response:data))
    }
    func testGetPlacesWithSeveralPlaces() {
        let data = "something,O,OL,ORL"
        XCTAssertEqual(["O","OL","ORL"], getPlaces(response:data))
    }
    
    func testGetTurnWhenLeft() {
        let place = "OL"
        XCTAssertEqual(["L"], getTurns(place:place))
    }
    func testGetTurnWhenRight() {
        let place = "OR"
        XCTAssertEqual(["R"], getTurns(place:place))
    }
    func testGetTurnWhenLeftAndRight() {
        let place = "OLR"
        XCTAssertEqual(["L","R"], getTurns(place:place))
    }
    func testGetTurnWhenNone() {
        let place = "O"
        XCTAssertEqual([], getTurns(place:place))
    }

    func testGetMovesWhenNoPlaces() {
        let places = Array<Place>()
        XCTAssertEqual(Array<Move>(), getMoves(places: places, refId: "abcd"))
    }
    func testGetMovesWhenOnePlaceWithNoTurns() {
        let places = ["O"]
        XCTAssertEqual(Array<Move>(), getMoves(places: places, refId: "abcd"))
    }
    func testGetMovesWhenOnePlaceWithOneTurns() {
        let places = ["OL"]
        let move = Move(request:Request(refId:"abcd", command:"M", repeats:1), turns:["L"])
        XCTAssertEqual([move], getMoves(places: places, refId: "abcd"))
    }
    func testGetMovesWhenManyPlacesWithManyTurns() {
        let places = ["OL","O","OR","O","OLR"]
        let move1 = Move(request:Request(refId:"abcd", command:"M", repeats:1), turns:["L"])
        let move2 = Move(request:Request(refId:"abcd", command:"M", repeats:3), turns:["R"])
        let move3 = Move(request:Request(refId:"abcd", command:"M", repeats:5), turns:["L","R"])
        XCTAssertEqual([move1,move2,move3], getMoves(places: places, refId: "abcd"))
    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
