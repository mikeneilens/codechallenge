
fun <Key, Value> memoize(function:(Key)->Value, cache:MutableMap<Key, Value> = mutableMapOf()): (Key)->Value {
    return fun(p:Key):Value {
        return if (cache[p] != null ) cache.getValue(p)
        else {
            val result = function(p)
            cache[p] = result
            result
        }
    }
}

