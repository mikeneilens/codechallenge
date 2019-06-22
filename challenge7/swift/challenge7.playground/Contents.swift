import XCTest
var str = "Hello, playground"
func numberIsEvenAndLessThanSomething(_ something:Int, _ aNumber:Int) ->Bool  {
    return aNumber % 2 == 0 && aNumber < something
}

func curried<P,Q,Output>(_ f:@escaping (P,Q)->Output, _ something:P) -> (Q)->Output {
     func g(aNumber:Q) -> Output { return f(something, aNumber)}
    return g
}

class Challenge7Tests: XCTestCase {
    func test_numberIsEvenAndLessThanSomething() {
        XCTAssertEqual(true, numberIsEvenAndLessThanSomething(5,2))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,3))
        XCTAssertEqual(false, numberIsEvenAndLessThanSomething(5,6))
    }
    
    func test_the_curried_function_to_make_sure_it_makes_isEvenAndLessThan_that_is_correct() {
        let numberIsEvenAndLessThan5 = curried(numberIsEvenAndLessThanSomething,5)
        XCTAssertEqual(true, numberIsEvenAndLessThan5(2))
        XCTAssertEqual(false, numberIsEvenAndLessThan5(3))
        XCTAssertEqual(false, numberIsEvenAndLessThan5(6))
    }
}

Challenge7Tests.defaultTestSuite.run()

