package model

data class Card(val id:String, val cardType:String, val name:String, val filtering:Filtering)

data class Filtering(val groupId:String?, val filters:List<Filter>)

interface Filter {
    val shouldInclude: (OperatingSystemVersion?) -> Boolean
}

object ControlGroup:Filter {
    override val shouldInclude = {_: OperatingSystemVersion? -> true}
}

class OsVersionEquals( _osVersion: OperatingSystemVersion):Filter {
    override val shouldInclude = {osVersion: OperatingSystemVersion? -> _osVersion.equals(osVersion) }
}

class OsVersionGreaterThan(_osVersion: OperatingSystemVersion):Filter {
    override val shouldInclude = {osVersion: OperatingSystemVersion? -> osVersion?.greaterThan(_osVersion) ?: false}
}
/* filters to add
osVersionMajorEquals
osVersionMinorEquals
osVersionMajorGreaterThan
osVersionMinorGreaterThan
osVersionLessThan
osVersionMajorLessThan
osVersionMinorLessThan
*/