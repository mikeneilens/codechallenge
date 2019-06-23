import XCTest
func numberIsEvenAndLessThanSomething(_ something:Int, _ aNumber:Int) ->Bool  {
    return aNumber % 2 == 0 && aNumber < something
}

infix operator <= :AdditionPrecedence//for currying. Not sure if the precedence is ideal but it seems to work!

func <= <P,Q,Output>(_ f:@escaping (P,Q)->Output, _ param1:P) -> (Q)->Output {
     func g(param2:Q) -> Output { return f(param1, param2)}
    return g
}

func <= <P,Output>(_ f:(P)->Output, _ param1:P) -> Output {
    return f(param1)
}

func <= <P,Q,R, Output>(_ f:@escaping (P,Q,R)->Output, _ param1:P) -> (Q,R)->Output {
    func g(param2:Q, param3:R) -> Output { return f(param1, param2, param3)}
    return g
}


func isEvenAndLessThan(_ something:Int) -> (Int) -> Bool { return numberIsEvenAndLessThanSomething <= 5 }

class Challenge7Tests: XCTestCase {
    func test_numberIsEvenAndLessThanSomething() {
        XCTAssertEqual(true, numberIsEvenAndLessThanSomething(5,2))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,3))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,6))
    }
    
    func test_the_curried_function_to_make_sure_it_makes_isEvenAndLessThan_that_is_correct() {
        XCTAssertEqual(true, numberIsEvenAndLessThanSomething <= 5 <= 2)
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething <= 5 <= 3)
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething <= 5 <= 6)
    }
    
    func test_isEvenAndLessThan() {
        let numberIsEvenAndLessThan5 = isEvenAndLessThan(5)
        XCTAssertEqual(true, numberIsEvenAndLessThan5 <= 2)
        XCTAssertEqual(false, numberIsEvenAndLessThan5 <= 3)
        XCTAssertEqual(false, numberIsEvenAndLessThan5 <= 6)
    }
    
    func test_currying_3_parameter_function_to_2_paramater_function() {
        func scoreBoard(_ text:String, _ playerName:String, _ score:Int) -> String {
            return "\(text) \(playerName) \(score)"
        }
        XCTAssertEqual("Your score: Mike 5", scoreBoard("Your score:", "Mike", 5))
        //curried version of the test
        XCTAssertEqual("Your score: Mike 5", scoreBoard <= "Your score:" <= "Mike" <= 5)
    }
}

Challenge7Tests.defaultTestSuite.run()


