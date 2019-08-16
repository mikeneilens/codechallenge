
struct GBP:Equatable {
    let value:Int
    init (_ value:Int) {
        if value >= 0 {self.value = value}
        else {self.value = -value}
    }
}
