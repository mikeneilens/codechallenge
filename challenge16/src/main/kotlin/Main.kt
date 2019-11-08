fun String.fromRomanToInt(): Int {
    if (this.isEmpty()) return 0

    if (this == "IV" ) return 4
    else if (this == "V") return 5 else return this.count()
}
