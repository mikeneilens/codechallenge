
//added a couple of functions to array to make code shorter to write
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
//This is a helper to find an element in a string using a subscript. e.g. "hello"[1] will give "e"
extension String {
    subscript (i: Int) -> String {
        if i < 0 || i >= self.count {return ""}
        return String(self[index(startIndex, offsetBy: i)])
    }
    func replaceElementAt(index:Int, with char:Character) -> String {
        return String(Array(self).replaceElementAt(index: index, with: char))
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

extension Grid {
    func drop(_ token:Token, intoColumn column:Col) -> Grid? {
        guard let lowestRow = self.lowestEmptyRow(forColumn: column) else {return nil}
        let cleanGrid = self.joined(separator: ":").lowercased().split(separator: ":").map{String($0)}
        return cleanGrid.replace(row: lowestRow, column: column, using:token.uppercased())
    }
    
    func lowestEmptyRow(forColumn column:Col) -> Int? {
        guard let gridRow =  self.last else {return nil}
        if gridRow[column] == "." {
            return self.count - 1
        } else {
            return Array(self.dropLast()).lowestEmptyRow(forColumn:column)
        }
    }
    
    func replace(row:Row, column:Col, using token:Token) -> Grid {
        return self.replaceElementAt(index: row, with: self[row].replaceElementAt(index: column, with: Character(token)))
    }
    
    func determineOutcomeOfAllMoves(using token:Token) -> Array<(Grid, Bool)> {
        let columns = [0,1,2,3,4,5,6]
        return columns.compactMap{ self.determineIfMoveWins(using:token, inColumn: $0)}
    }
    
    func determineIfMoveWins(using token:Token,inColumn column:Col) -> (Grid, Bool)? {
        guard let gridAfterMove = self.drop(token, intoColumn: column) else {return nil}
        let tokenText = tokenMap[token] ?? ""
        return  (gridAfterMove,  getGridStatus(grid: gridAfterMove) == "\(tokenText) wins")
    }
}

func addToken(grid existingGrid:Grid) -> Grid {
    
    let (lastToken, _, _) = lastTokenPlayed(grid: existingGrid)
    let tokenToPlay = lastToken == "r" ? "y" : "r"
    
    let outcomeOfAllMoves = existingGrid.determineOutcomeOfAllMoves(using:tokenToPlay)
    let winningMoves = outcomeOfAllMoves.filter{(resultingGrid,isWinner) in isWinner == true}
    
    if winningMoves.count > 0  {
        return winningMoves[0].0
    }
    
    let subsequentToken = tokenToPlay == "r" ? "y" : "r"
    
    //outcomeOfSubsequentMoves contains [(grid, [(subsequentGrid, isWinner)])]
    let outcomeOfSubsequentMoves = outcomeOfAllMoves.map{ (grid,_) in (grid, grid.determineOutcomeOfAllMoves(using: subsequentToken))}
    let movesThatDontAllowOpponentToWin = outcomeOfSubsequentMoves.filter{  (grid, subsequentGrids) in  !subsequentGrids.contains(where: {(_,isWinner) in isWinner}) }
    
    if movesThatDontAllowOpponentToWin.count > 0 {
        let moveIndex = Int.random(in: 0..<movesThatDontAllowOpponentToWin.count)
        return movesThatDontAllowOpponentToWin[moveIndex].0
    }
    
    if outcomeOfAllMoves.count > 0 {
        return outcomeOfAllMoves[0].0
    } else {
        return existingGrid
    }
}


