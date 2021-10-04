import XCTest

class Cache<Key, Value> where Key : Hashable{
    private var dictionary = Dictionary<Key, Value>()
    subscript(_ key:Key) -> Value? {
        get { dictionary[key]}
        set { dictionary[key] = newValue}
    }
}

func  memoize<Key:Hashable, Value> (_ function: @escaping (Key) -> Value, using cache: Cache<Key,Value> = Cache<Key,Value>()  ) -> (Key) -> Value {
    
    return { p in
        if let result = cache[p] {
            return result
        } else {
            let result = function(p)
            cache[p] = result
            return result
        }
    }
}

class MemoizeTests: XCTestCase {

    let numberToString:(Int)->String = { n in String(n)}
    
    func testWithDefaultCache() {
        let functionWithMemory = memoize(numberToString)
        XCTAssertEqual("56", functionWithMemory(56))
        XCTAssertEqual("61", functionWithMemory(61))
    }
    func testUsingTestCache() {
        let testCache = Cache<Int, String>()
        let functionWithMemory = memoize(numberToString, using:testCache)
        XCTAssertEqual(nil, testCache[56])
        XCTAssertEqual("56", functionWithMemory(56))
        XCTAssertEqual("56", testCache[56])
    }
    func testUsingTestCacheWhenCachedValueIsChanged(){
        let testCache = Cache<Int, String>()
        let functionWithMemory = memoize(numberToString, using:testCache)
        testCache[56] = "The wrong answer!"
        XCTAssertEqual("The wrong answer!", functionWithMemory(56))
    }
    func testThatResultsAreAlwaysCached() {
        var x = 2
        func addNtoX(_ n:Int) -> Int {n + x}
        XCTAssertEqual(5, addNtoX(3) )
        x = 4
        XCTAssertEqual(7, addNtoX(3) )
        XCTAssertEqual(8, addNtoX(4) )
        //repeat test with memoized function
        let functionWithMemory = memoize(addNtoX)
        x = 2
        XCTAssertEqual(5, functionWithMemory(3))
        x = 4
        XCTAssertEqual(5, functionWithMemory(3))
        XCTAssertEqual(8, functionWithMemory(4))
    }

}
MemoizeTests.defaultTestSuite.run()



