package model

data class Card(val id:String, val cardType:String, val name:String, val filtering:Filtering)

data class Filtering(val groupId:String?, val filters:List<Filter>)

interface Filter {
    val shouldInclude: (OperatingSystemVersion?) -> Boolean
}

object ControlGroup:Filter {
    override val shouldInclude = {_: OperatingSystemVersion? -> true}
}

class OsVersionEquals(cardOsVersion: OperatingSystemVersion):Filter {
    override val shouldInclude = {userOsVersion: OperatingSystemVersion? -> cardOsVersion.equals(userOsVersion) }
}

class OsVersionGreaterThan(cardOsVersion: OperatingSystemVersion):Filter {
    override val shouldInclude = {userOsVersion: OperatingSystemVersion? -> userOsVersion?.greaterThan(cardOsVersion) ?: false}
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