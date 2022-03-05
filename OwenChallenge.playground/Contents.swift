import XCTest

struct Owen{
    let output:Array<String>
    let noOfBracketsOpen:Int
    
    func plus(_ s:String) -> Owen {
        switch (s) {
        case "(": return  Owen(output:output + ((noOfBracketsOpen == 0) ? [""] : []) , noOfBracketsOpen:noOfBracketsOpen + 1)
        case ")": return Owen(output:output, noOfBracketsOpen:noOfBracketsOpen - 1)
        default:
            if noOfBracketsOpen > 0 {
                return Owen(output: output.dropLast() + ["\( (output.last ?? "") + s)"], noOfBracketsOpen: noOfBracketsOpen)
            } else {
                return Owen(output: output + [s], noOfBracketsOpen: 0)
            }
        }
    }
 }

func solution(_ data: Array<String>) -> Array<String> {
    data.reduce( Owen(output:[], noOfBracketsOpen: 0)){owen, string in owen.plus(string)}.output
}

class OwenTests: XCTestCase {
    func testWithNoData() {
        XCTAssertEqual(solution([]) , [])
    }
    func testWithNoBrackets() {
        XCTAssertEqual(solution(["a","b"]) , ["a","b"])
    }
    func testWithOneSetOfBrackets() {
        XCTAssertEqual(solution(["a","b","(","a","b",")"]) , ["a","b","ab"])
    }
    func testWithTwoSetsOfBrackets() {
        XCTAssertEqual(solution(["a","b","(","a","b",")","a","b","(","a","b",")"]) , ["a","b","ab","a","b","ab"])
    }
    func testWithBracketsNestedInBrackets() {
        XCTAssertEqual(solution(["a","(","b","(","c","d",")","e",")","f"]), ["a", "bcde", "f"])
    }
}

OwenTests.defaultTestSuite.run()
