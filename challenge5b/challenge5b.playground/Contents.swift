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

//Start of challenge 5b =======================================================================
extension Array {
    func mapIndexed<T>(_ transform:(Int, Element) -> T) -> Array<T> {
        var newArray = Array<T>()
        for (index, element) in self.enumerated() {
            newArray.append(transform(index, element))
        }
        return newArray
    }
    func replaceElementAt(index:Int, with element:Element) -> Array<Element> {
        let a:Array<Element> = self.mapIndexed{ $0 == index ? element : $1}
        return a.reduce(Array<Element>()){$0 + [$1]}
    }
}

extension String {
    func replaceElementAt(index:Int, with char:Character) -> String {
        return String(Array(self).replaceElementAt(index: index, with: char))
    }
}

extension Grid {
    func replaceToken(row:Row, column:Col, token:Token) -> Grid {
        return self.replaceElementAt(index: row, with: self[row].replaceElementAt(index: column, with: Character(token)))
    }
    
    func lowestEmptyRow(forColumn column:Col) -> Int {
        guard let gridRow =  self.last else {return -1}
        if gridRow[column] == "." {
            return self.count - 1
        }
        else {
            return Array(self.dropLast()).lowestEmptyRow(forColumn:column)
        }
    }

}

func addToken(grid:Grid) -> Grid {

    let (lastToken, lastTokenRow, lastTokenCol) = lastTokenPlayed(grid: grid)

    let token = lastToken == "r" ? "Y" : "R"
    let columnToDropInto = 0
    let row = grid.lowestEmptyRow(forColumn: columnToDropInto)

    if lastToken.isEmpty {
        return grid
            .replaceToken(row: row, column: columnToDropInto, token: token)
    } else {
        return grid
            .replaceToken(row: row, column: columnToDropInto, token: token)
            .replaceToken(row: lastTokenRow, column:lastTokenCol , token: lastToken)
    }
}

//===================================================================================================================
class Challenge5bTests: XCTestCase {
    func test_replaceElementAt() {
        XCTAssertEqual("Xbcde", "abcde".replaceElementAt(index: 0, with: "X"))
        XCTAssertEqual("aXcde", "abcde".replaceElementAt(index: 1, with: "X"))
        XCTAssertEqual("abXde", "abcde".replaceElementAt(index: 2, with: "X"))
        XCTAssertEqual("abcXe", "abcde".replaceElementAt(index: 3, with: "X"))
        XCTAssertEqual("abcdX", "abcde".replaceElementAt(index: 4, with: "X"))
        XCTAssertEqual("abcde", "abcde".replaceElementAt(index: 5, with: "X"))
    }
    
    func test_addTokenToAnEmptyGrid() {
        let grid = [".......",
                    ".......",
                    ".......",
                    ".......",
                    ".......",
                    "......."]
        let validResults = [[".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "R......"],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             ".R....."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "..R...."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "...R..."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "....R.."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             ".....R."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "......R"]]
        XCTAssertTrue(validResults.contains(addToken(grid: grid)))
    }
    func test_addTokenToAGridContainingOneToken() {
        let grid = [".......",
                    ".......",
                    ".......",
                    ".......",
                    ".......",
                    "...R..."]
        let validResults = [[".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "Y..r..."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             ".Y.r..."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "..Yr..."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "...rY.."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "...r.Y."],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             ".......",
                             "...r..Y"],
                            [".......",
                             ".......",
                             ".......",
                             ".......",
                             "...Y...",
                             "...r..."]]
        XCTAssertTrue(validResults.contains(addToken(grid: grid)))
    }

    func test_findLowestEmptyRow() {
        let grid = [".......",
                    ".......",
                    ".......",
                    "..R....",
                    "yrryrr.",
                    "rryryyr"]
        XCTAssertEqual(3, grid.lowestEmptyRow(forColumn: 0))
        XCTAssertEqual(2, grid.lowestEmptyRow(forColumn: 2))
        XCTAssertEqual(4, grid.lowestEmptyRow(forColumn: 6))
    }
    func test_addTokenToAGridContainingManyToken() {
        let grid = [".......",
                    ".......",
                    ".......",
                    "..R....",
                    "yrryrr.",
                    "rryryyr"]
        let validResults = [[".......",
                             ".......",
                             ".......",
                             "Y.r....",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             ".......",
                             ".Yr....",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             "..Y....",
                             "..r....",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             ".......",
                             "..rY...",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             ".......",
                             "..r.Y..",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             ".......",
                             "..r..Y.",
                             "yrryrr.",
                             "rryryyr"],
                            [".......",
                             ".......",
                             ".......",
                             "..r....",
                             "yrryrrY",
                             "rryryyr"]
        ]
        XCTAssertTrue(validResults.contains(addToken(grid: grid)))
    }

}
Challenge5bTests.defaultTestSuite.run()


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

//Challenge5Tests.defaultTestSuite.run()
