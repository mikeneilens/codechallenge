import XCTest

//This is a helper to find an element in a string using a subscript. e.g. "hello"[1] will give "e"
extension String {
    subscript (i: Int) -> String {
        if i < 0 || i >= self.count {return ""}
        return String(self[index(startIndex, offsetBy: i)])
    }
}

typealias Grid  = Array<String>
typealias Row = Int
typealias Col = Int
typealias Token = String
let tokenMap = ["r":"Red","y":"Yellow"]

func lastTokenPlayed(grid:Grid) -> (Token, Row, Col) {
    for (row, string) in grid.enumerated() {
        for col in 0..<string.count {
            if string[col] >= "A" && string[col] <= "Z" {
                return (string[col].lowercased(), row, col)
            }
        }
    }
    return ("",-1,-1)
}

func getGridStatus(grid:Grid) -> String {
    let (token, tokenRow, tokenCol) = lastTokenPlayed(grid: grid)
    let fourInARow = token + token + token + token
    let tokenText = tokenMap[token] ?? ""

    switch true {
        case token.isEmpty:
            return "Red plays next"
        case horizontalLine(grid: grid, tokenRow: tokenRow).contains(fourInARow):
            return "\(tokenText) wins"
        case verticalLine(grid: grid, tokenCol: tokenCol).contains(fourInARow):
            return "\(tokenText) wins"
        case rightDiagonalLine(grid: grid, tokenRow: tokenRow, tokenCol: tokenCol).contains(fourInARow):
            return "\(tokenText) wins"
        case leftDiagonalLine(grid: grid, tokenRow: tokenRow, tokenCol: tokenCol).contains(fourInARow):
            return "\(tokenText) wins"
        case !grid[0].contains("."):
            return "Draw"
        case token == "r":
            return "Yellow plays next"
        case token == "y":
            return "Red plays next"
        default:
            return "Red plays next"
    }
}

typealias columnCalculator = (Row) -> Col //For diagonal lines you need a function that uses the row to determine the column

func createLine(grid:Grid, columnCalc:columnCalculator) -> String {
    var result = ""
    for row in 0..<grid.count {
        let col = columnCalc(row)
        result += grid[row][col].lowercased()
    }
    return result
}

func horizontalLine(grid:Grid, tokenRow:Int) -> String {
    return grid[tokenRow].lowercased()
}

func verticalLine(grid:Grid, tokenCol:Int ) -> String {
    let verticalColumnCalculator:columnCalculator = { _ in return tokenCol}
    return createLine(grid: grid, columnCalc: verticalColumnCalculator)
}

func rightDiagonalLine(grid:Grid, tokenRow:Int, tokenCol:Int ) -> String {
    let rightDiagColumnCalculator = {row in return row + (tokenCol - tokenRow)}
    return createLine(grid: grid, columnCalc: rightDiagColumnCalculator)
}

func leftDiagonalLine(grid:Grid, tokenRow:Int, tokenCol:Int ) -> String {
    let leftDiagColumnCalculator = {row in return (tokenCol + tokenRow) - row}
    return createLine(grid: grid, columnCalc: leftDiagColumnCalculator)
}

//===================================================================================================================
class Challenge5Tests: XCTestCase {
    
    func test_findLastRedTokenPlayed() {
        let grid = [".......",
                    ".......",
                    ".R.....",
                    ".y.....",
                    ".r.....",
                    ".y....."]
        let (token, row, col) = lastTokenPlayed(grid: grid)
        XCTAssertEqual("r", token)
        XCTAssertEqual(2, row)
        XCTAssertEqual(1, col)
    }

    func test_findLastYellowTokenPlayed() {
        let grid = [".......",
                    ".......",
                    ".r.....",
                    ".y.....",
                    ".r.....",
                    ".y..Y.."]
        let (token, row, col) = lastTokenPlayed(grid: grid)
        XCTAssertEqual("y", token)
        XCTAssertEqual(5, row)
        XCTAssertEqual(4, col)
    }

    func test_tryAndFindLastTokenPlayedWhenThereIsntOne() {
        let grid = [".......",
                    ".......",
                    ".r.....",
                    ".y.....",
                    ".r.....",
                    ".y..y.."]
        let (token, row, col) = lastTokenPlayed(grid: grid)
        XCTAssertTrue(token.isEmpty)
        XCTAssertEqual(-1, row)
        XCTAssertEqual(-1, col)
    }
    
    func test_createHorizontalLine() {
        let grid = [".......",
                    ".......",
                    ".......",
                    "12345Y7",
                    ".......",
                    "......."]
        XCTAssertEqual("12345y7", horizontalLine(grid: grid, tokenRow: 3))
    }

    func test_RightDiagonalLine() {
        let grid = [".......",
                    ".......",
                    "1......",
                    ".R.....",
                    "..3....",
                    "...4..."]
        XCTAssertEqual("1r34", rightDiagonalLine(grid: grid, tokenRow: 3, tokenCol: 1))
    }

    func test_LeftDiagonalLine() {
        let grid = [".......",
                    "......1",
                    ".....2.",
                    "....3..",
                    "...Y...",
                    "..5...."]
        XCTAssertEqual("123y5", leftDiagonalLine(grid: grid, tokenRow: 4, tokenCol: 3))
    }

    func test_createVerticalLine() {
        let grid = [".1.....",
                    ".2.....",
                    ".R.....",
                    ".3.....",
                    ".4.....",
                    ".5....."]
        XCTAssertEqual("12r345", verticalLine(grid: grid, tokenCol: 1))
    }

    
    func test_redWinsHorizontal() {
        let grid = [".......",
                    ".......",
                    ".rRrr..",
                    ".yyry..",
                    ".rryr..",
                    ".yyry.."]
        XCTAssertEqual("Red wins", getGridStatus(grid: grid))
    }
    
    func test_redWinsVertical() {
        let grid = [".......",
                    ".......",
                    ".rRyr..",
                    ".yrry..",
                    ".rryr..",
                    ".yrry.."]
        XCTAssertEqual("Red wins", getGridStatus(grid: grid))
    }

    func test_redWinsRightDiagonal() {
        let grid = [".......",
                    ".......",
                    ".r.....",
                    ".yR....",
                    ".ryr...",
                    ".ryyr.."]
        XCTAssertEqual("Red wins", getGridStatus(grid: grid))
    }

    func test_redWinsLeftDiagonal() {
        let grid = [".......",
                    "...r...",
                    "..ry...",
                    ".Ryy...",
                    "ryry...",
                    "yryr..."]
        XCTAssertEqual("Red wins", getGridStatus(grid: grid))
    }

    func test_yelloWinsLeftDiagonal() {
        let grid = [".......",
                    "..y....",
                    "..ry...",
                    "..rrY..",
                    "..ryry.",
                    "..yrry."]
        XCTAssertEqual("Yellow wins", getGridStatus(grid: grid))
    }

    func test_draw() {
        let grid = ["ryrYryr",
                    "yryryrr",
                    "ryryryy",
                    "ryrrryr",
                    "rrryryy",
                    "yryryyy"]
        print(grid)
        XCTAssertEqual("Draw", getGridStatus(grid: grid))
    }

    func test_notDrawIfAPlayerHasWon() {
        let grid = ["ryyYyyr",
                    "yryryrr",
                    "ryryryy",
                    "ryrrryr",
                    "rrryryy",
                    "yryryyy"]
        print(grid)
        XCTAssertEqual("Yellow wins", getGridStatus(grid: grid))
    }
    

    func test_redPlaysNext() {
        let grid = [".......",
                    ".......",
                    ".......",
                    ".......",
                    ".......",
                    "...rY.."]
        XCTAssertEqual("Red plays next", getGridStatus(grid: grid))
    }

    func test_yellowPlaysNext() {
        let grid = [".......",
                    ".......",
                    ".......",
                    ".......",
                    "...R...",
                    "...ry.."]
        XCTAssertEqual("Yellow plays next", getGridStatus(grid: grid))
    }

    func test_emptyGrid() {
        let grid = [".......",
                    ".......",
                    ".......",
                    ".......",
                    ".......",
                    "......."]
        XCTAssertEqual("Red plays next", getGridStatus(grid: grid))
    }

    
}

Challenge5Tests.defaultTestSuite.run()
