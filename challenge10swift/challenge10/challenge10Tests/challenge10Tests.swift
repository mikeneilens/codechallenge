//
//  challenge10Tests.swift
//  challenge10Tests
//
//  Created by Michael Neilens on 15/08/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import XCTest
@testable import challenge10

class diceTest: XCTestCase {


    func test_Dice_I_Between_Two_And_Twelve() {
        let dice = Dice()
        XCTAssertTrue(dice.total >= 1 && dice.total <= 12 )
    }

    func test_twenty_dice_are_not_all_the_same_value() {
        let dices = [
            Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),
            Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
        ]
        let dicesWithSameValueAsFirst = dices.filter{$0.total == dices[0].total }
        XCTAssertFalse(dicesWithSameValueAsFirst.count == dices.count)
    }
    
    func test_200_dice_contain_one_of_each_possible_value() {
       
        let dices = [
                Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
                ,Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice(),Dice()
        ]
            
        let dicesWithValue2 = dices.filter{$0.total == 2}
        XCTAssertTrue(dicesWithValue2.count > 0 )
            
        let dicesWithValue3 = dices.filter{$0.total == 3}
        XCTAssertTrue(dicesWithValue3.count > 0 )
            
        let dicesWithValue4 = dices.filter{$0.total == 4}
        XCTAssertTrue(dicesWithValue4.count > 0 )
            
        let dicesWithValue5 = dices.filter{$0.total == 5}
        XCTAssertTrue(dicesWithValue5.count > 0 )
            
        let dicesWithValue6 = dices.filter{$0.total == 6}
        XCTAssertTrue(dicesWithValue6.count > 0 )
            
        let dicesWithValue7 = dices.filter{$0.total == 7}
        XCTAssertTrue(dicesWithValue7.count > 0 )
            
        let dicesWithValue8 = dices.filter{$0.total == 8}
        XCTAssertTrue(dicesWithValue8.count > 0 )
            
        let dicesWithValue9 = dices.filter{$0.total == 9}
        XCTAssertTrue(dicesWithValue9.count > 0 )
            
        let dicesWithValue10 = dices.filter{$0.total == 10}
        XCTAssertTrue(dicesWithValue10.count > 0 )
            
        let dicesWithValue11 = dices.filter{$0.total == 11}
        XCTAssertTrue(dicesWithValue11.count > 0 )
            
        let dicesWithValue12 = dices.filter{$0.total == 12}
        XCTAssertTrue(dicesWithValue12.count > 0 )
            
        XCTAssertEqual(200,dicesWithValue2.count + dicesWithValue3.count + dicesWithValue4.count + dicesWithValue5.count +
            dicesWithValue6.count + dicesWithValue7.count + dicesWithValue8.count + dicesWithValue9.count + dicesWithValue10.count +
            dicesWithValue11.count + dicesWithValue12.count)
    }
    
    func test_description_formats_dice_value_correctly_when_a_value_of_11_is_thrown() {
        var dice = Dice()
        while (dice.total != 11) { dice = Dice()}
        XCTAssertTrue("\(dice)" == "You shook a 5 and a 6" || "\(dice)" == "You shook a 6 and a 5" )
    }
    
    
    func test_Initial_board_location_is_Go () {
        let boardLocation = BoardLocation(locations: locations)
        XCTAssertEqual(Go(), boardLocation.currentLocation)
    }
    
    
    func test_Initial_board_location_is_location_at_position_4_if_a_locationIndex_is_given_to_the_constructor() {
        let boardLocation = BoardLocation(locations: locations, locationIndex: 4)
        XCTAssertEqual(locations[4], boardLocation.currentLocation)
    }
    
    
    func test_Initial_board_has_hasPassedGo_set_correctly_depending_on_locationIndex_sent_to_the_constructor() {
        let boardLocation = BoardLocation(locations: locations, locationIndex: locations.count  - 1)
        XCTAssertEqual(false, boardLocation.hasPassedGo)
    
        let boardLocationPassedGo = BoardLocation(locations: locations, locationIndex: locations.count + 1)
        XCTAssertEqual(true, boardLocationPassedGo.hasPassedGo)
    }
    
    
    func test_Adding_dice_of_value_4_to_an_inital_boardLocation_updates_the_boardLocation_to_show_location_at_position_4_in_the_Location_array() {
        let boardLocation = BoardLocation(locations: locations)
        var dice = Dice()
        while (dice.total != 4) {dice = Dice()}
        let newBoardLocation = boardLocation.move(dice:dice)
        XCTAssertEqual(locations[4], newBoardLocation.currentLocation)
    }
    
    
    func test_Adding_dice_of_value_4_to_an_inital_boardLocation_with_index_set_to_5_updates_the_boardLocation_to_show_location_at_position_9_in_the_Location_array() {
        let boardLocation = BoardLocation(locations: locations, locationIndex: 5)
        var dice = Dice()
        while (dice.total != 4) {dice = Dice() }
        
        let newBoardLocation = boardLocation.move(dice:dice)
        XCTAssertEqual(locations[9], newBoardLocation.currentLocation)
    }
    
    
    func test_Adding_dice_of_value_4_to_an_inital_boardLocation_with_index_set_to_board_size_minus_3_updates_the_boardLocation_to_show_location_at_position_1_in_the_Location_array() {
        let boardLocation = BoardLocation(locations:locations, locationIndex:locations.count - 3 )
        var dice = Dice()
        while (dice.total != 4) {dice = Dice() }
    
        let newBoardLocation = boardLocation.move(dice: dice)
        XCTAssertEqual(locations[1], newBoardLocation.currentLocation)
        XCTAssertEqual(true, newBoardLocation.hasPassedGo)
    }
}
