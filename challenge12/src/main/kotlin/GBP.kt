class GBP(_value:Int) {
    val value:Int

    init {
        if (_value > 0 ) value = _value else value = - _value
    }

    override fun equals(other: Any?): Boolean {
        return other is GBP && other.value == value
    }

    override fun toString(): String {
        return "£$value"
    }
}