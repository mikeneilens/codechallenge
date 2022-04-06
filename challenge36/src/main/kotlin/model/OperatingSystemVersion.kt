package model

data class OperatingSystemVersion(val majorVersion:Int, val minorVersion:Int, val patchVersion:Int) {
    override fun equals(other: Any?) = other is OperatingSystemVersion
            && other.majorVersion == this.majorVersion
            && other.minorVersion == this.minorVersion
            && other.patchVersion == this.patchVersion

    fun greaterThan(other: Any?) = other is OperatingSystemVersion &&
            (this.majorVersion > other.majorVersion
                    || this.majorVersion == other.majorVersion && this.minorVersion > other.minorVersion
                    || this.majorVersion == other.majorVersion && this.minorVersion == other.minorVersion && this.patchVersion > other.patchVersion
                    )

    fun lessThan(other: Any?) = other is OperatingSystemVersion
            && !equals(other) && !greaterThan(other)

    fun majorEquals(other: Any?) = other is OperatingSystemVersion
            && other.majorVersion == this.majorVersion

    fun majorGreaterThan(other: Any?) = other is OperatingSystemVersion
            && this.majorVersion > other.majorVersion

    fun majorlessThan(other: Any?) = other is OperatingSystemVersion
            && !majorEquals(other) && !majorGreaterThan(other)

    fun minorEquals(other: Any?) = other is OperatingSystemVersion
            && this.majorVersion == other.majorVersion
            && this.minorVersion == other.minorVersion

    fun minorGreaterThan(other: Any?) = other is OperatingSystemVersion &&
            (this.majorVersion > other.majorVersion
                    || this.majorVersion == other.majorVersion && this.minorVersion > other.minorVersion)

    fun minorlessThan(other: Any?) = other is OperatingSystemVersion
            && !minorEquals(other) && !minorGreaterThan(other)

}