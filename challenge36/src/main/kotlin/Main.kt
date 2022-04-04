

data class UserDetails(val osVersion:OperatingSystemVersion?)

data class CardData(val id:String, val cardType:String, val name:String)

data class Filtering(val groupId:String, val filters:List<Filter>)

interface Filter {
    fun shouldInclude(osVersion:OperatingSystemVersion):Boolean
}

object controlGroup:Filter {
    override fun shouldInclude(osVersion: OperatingSystemVersion) = true
}
class osVersionEquals(val osVersion:OperatingSystemVersion):Filter {
    override fun shouldInclude(osVersion: OperatingSystemVersion) = this.osVersion.equals(osVersion)
}
class osVersionGreaterThan(val osVersion:OperatingSystemVersion):Filter {
    override fun shouldInclude(osVersion: OperatingSystemVersion) = osVersion.greaterThan(this.osVersion)
}
/*
osVersionMajorEquals
osVersionMinorEquals

osVersionMajorGreaterThan
osVersionMinorGreaterThan
osVersionLessThan
osVersionMajorLessThan
osVersionMinorLessThan
*/