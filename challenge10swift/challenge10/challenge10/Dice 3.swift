
struct Dice:CustomStringConvertible {
    
    private let dice1 = Int.random(in: 1...6)
    private let dice2 = Int.random(in: 1...6)
    
    var total:Int {return dice1 + dice2}
    
    var description: String {return "You shook a \(dice1) and a \(dice2)"}
}
