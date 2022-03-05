import XCTest

protocol BracketRules {
    func ProcessOpeningBracket(owen:Owen) -> Owen
    func ProcessClosingBracket(owen:Owen) -> Owen
}

struct Owen{
    let output:Array<String>
    let noOfBracketsOpen:Int
    let bracketRules:BracketRules
    
    func plus(_ s:String) -> Owen {
        switch (s) {
        case "(": return bracketRules.ProcessOpeningBracket(owen: self)
        case ")": return bracketRules.ProcessClosingBracket(owen: self)
        default:
            if noOfBracketsOpen > 0 {
                return Owen(output: output.concatonateLastStringWith(s), noOfBracketsOpen: noOfBracketsOpen, bracketRules: bracketRules)
            } else {
                return Owen(output: output + [s], noOfBracketsOpen: 0, bracketRules: bracketRules)
            }
        }
    }
 }

extension Array where Element==String {
    func concatonateLastStringWith(_ s:String) -> Array<String> { dropLast() + [(last ?? "") + s]}
}

struct DaveBracketRules:BracketRules {
    func ProcessOpeningBracket(owen: Owen) -> Owen {
        Owen(output:owen.output + ((owen.noOfBracketsOpen == 0) ? [""] : []) , noOfBracketsOpen:owen.noOfBracketsOpen + 1, bracketRules: owen.bracketRules)
    }
    func ProcessClosingBracket(owen: Owen) -> Owen {
        Owen(output:owen.output, noOfBracketsOpen:owen.noOfBracketsOpen - 1,bracketRules: owen.bracketRules)
    }
}

struct PJBracketRules:BracketRules {
    func ProcessOpeningBracket(owen: Owen) -> Owen {
        Owen(output:owen.output + [""], noOfBracketsOpen:owen.noOfBracketsOpen + 1, bracketRules: owen.bracketRules)
    }
    func ProcessClosingBracket(owen: Owen) -> Owen {
        Owen(output:owen.output + ((owen.noOfBracketsOpen > 1) ? [""] : []) , noOfBracketsOpen:owen.noOfBracketsOpen - 1, bracketRules: owen.bracketRules)
    }
}

func solution(_ data: Array<String>, _ bracketRules:BracketRules) -> Array<String> {
    data.reduce( Owen(output:[], noOfBracketsOpen: 0, bracketRules: bracketRules)){owen, string in owen.plus(string)}.output
}

class OwenTests: XCTestCase {
    func testWithNoData() {
        XCTAssertEqual(solution([], DaveBracketRules()) , [])
        XCTAssertEqual(solution([], PJBracketRules()) , [])
    }
    func testWithNoBrackets() {
        XCTAssertEqual(solution(["a","b"], DaveBracketRules()) , ["a","b"])
        XCTAssertEqual(solution(["a","b"], PJBracketRules()) , ["a","b"])
    }
    func testWithOneSetOfBrackets() {
        XCTAssertEqual(solution(["a","b","(","a","b",")"], DaveBracketRules()) , ["a","b","ab"])
        XCTAssertEqual(solution(["a","b","(","a","b",")"], PJBracketRules()) , ["a","b","ab"])
    }
    func testWithTwoSetsOfBrackets() {
        XCTAssertEqual(solution(["a","b","(","a","b",")","a","b","(","a","b",")"], DaveBracketRules()) , ["a","b","ab","a","b","ab"])
        XCTAssertEqual(solution(["a","b","(","a","b",")","a","b","(","a","b",")"], PJBracketRules()) , ["a","b","ab","a","b","ab"])
    }
    func testWithBracketsNestedInBracketsUsingDaveRules() {
        XCTAssertEqual(solution(["a","(","b","(","c","d",")","e",")","f"], DaveBracketRules()), ["a", "bcde", "f"])
    }
    func testWithBracketsNestedInBracketsUsingPJRules() {
        XCTAssertEqual(solution(["a","(","b","(","c","d",")","e",")","f"], PJBracketRules()), ["a", "b", "cd", "e", "f"])
    }
}

OwenTests.defaultTestSuite.run()
