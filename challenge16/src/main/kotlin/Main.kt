fun String.fromRomanToInt(): Int {
    if (this.isEmpty()) return 0

    return this.count()
}
