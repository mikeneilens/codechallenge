package mike

class UnitTime(val unit:String, val value:Int) {
    override fun toString(): String {
        return  if (value == 1)
                    "$value $unit"
                else
                    "$value ${unit}s"
    }
}