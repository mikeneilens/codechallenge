import XCTest

struct Owen{
    let output:Array<String>
    let shouldConcat:Bool
    
    func plus(_ s:String) -> Owen {
        switch (s) {
        case "(": return Owen(output:output + [""] , shouldConcat:true)
        case ")": return Owen(output:output , shouldConcat:false)
        default:
            if shouldConcat {
                return Owen(output: output.dropLast() + ["\( (output.last ?? "") + s)"], shouldConcat:true)
            } else {
                return Owen(output: output + [s], shouldConcat:false)
            }
        }
    }
}

func solution(_ data: Array<String>) -> Array<String> {
    data.reduce( Owen(output:[], shouldConcat:false)){owen, string in owen.plus(string)}.output
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
}

OwenTests.defaultTestSuite.run()
