import XCTest
func numberIsEvenAndLessThanSomething(_ something:Int, _ aNumber:Int) ->Bool  {
    return aNumber % 2 == 0 && aNumber < something
}

func curry (_ param1:Int,_ f:@escaping (Int,Int)->Bool) -> (Int)->Bool {
    func g(param2:Int) -> Bool { return f(param1, param2)}
    return g
}

infix operator => :TernaryPrecedence //for currying. Not sure if the precedence is ideal but it seems to work processing right to left!

func => <P,Q,Output>(_ param1:P,_ f:@escaping (P,Q)->Output) -> (Q)->Output {
    func g(param2:Q) -> Output { return f(param1, param2)}
    return g
}

func => <P,Output>( _ param1:P, _ f:(P)->Output) -> Output {
    return f(param1)
}

func => <P,Q,R, Output>(_ param1:P, _ f:@escaping (P,Q,R)->Output) -> (Q,R)->Output {
    func g(param2:Q, param3:R) -> Output { return f(param1, param2, param3)}
    return g
}

func isEvenAndLessThan(_ something:Int) -> (Int) -> Bool { return 5 => numberIsEvenAndLessThanSomething }

class Challenge7Tests: XCTestCase {
    func test_numberIsEvenAndLessThanSomething() {
        XCTAssertEqual(true, numberIsEvenAndLessThanSomething(5,2))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,3))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,6))
    }

    func test_the_curry_function_that_takes_a_function_expecting_two_integers_and_converts_to_function_expecting_one_parameter() {
        let numberIsEvenAndLessThan5 = curry(5, numberIsEvenAndLessThanSomething)
        XCTAssertEqual(true, numberIsEvenAndLessThan5(2))
        XCTAssertEqual(false, numberIsEvenAndLessThan5(3))
        XCTAssertEqual(false, numberIsEvenAndLessThan5(6))
    }

    func test_the_generic_curried_function_to_make_sure_it_makes_isEvenAndLessThan_that_is_correct() {
        XCTAssertEqual(true, (5 => numberIsEvenAndLessThanSomething)(2))
        XCTAssertEqual(false, (5 => numberIsEvenAndLessThanSomething)(3))
        XCTAssertEqual(false, (5 => numberIsEvenAndLessThanSomething)(6))
    }
    
    func test_isEvenAndLessThan() {
        let numberIsEvenAndLessThan5 = 5 => isEvenAndLessThan
        XCTAssertEqual(true, 2 => numberIsEvenAndLessThan5 )
        XCTAssertEqual(false, 3 => numberIsEvenAndLessThan5 )
        XCTAssertEqual(false, 6 => numberIsEvenAndLessThan5 )
    }
    
    func test_currying_3_parameter_function_to_2_paramater_function2() {
        func scoreBoard(_ text:String, _ playerName:String, _ score:Int) -> String {
            return "\(text) \(playerName) \(score)"
        }
        XCTAssertEqual("Your score: Mike 5", scoreBoard("Your score:", "Mike", 5))
        //curried version of the test
        XCTAssertEqual("Your score: Mike 5", ("Your score:" => scoreBoard)("Mike",5) )
        //test chaining currying together
        XCTAssertEqual("Your score: Mike 5", 5 => "Mike" => "Your score:" => scoreBoard )
    }

}

Challenge7Tests.defaultTestSuite.run()


