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
}
MemoizeTests.defaultTestSuite.run()



