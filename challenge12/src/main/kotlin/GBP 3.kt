class GBP(_value:Int) {
    val value:Int

    init {
        if (_value > 0 ) value = _value else value = - _value
    }

    operator fun plus(other:GBP):GBP{
        return GBP(this.value + other.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is GBP && other.value == value
    }

    override fun toString(): String {
        return "£$value"
    }
}