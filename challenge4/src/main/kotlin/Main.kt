fun main(args: Array<String>) {
    println("Hello world")
}

fun sortVouchers(vouchers: String): String {
    val listOfVouchers = vouchers.split(";")

    if (listOfVouchers.size < 2) return vouchers

    return listOfVouchers.sortedBy { it.rank() }.joinToString(";")

}

fun String.rank():Int {
    val voucherStatusGroup = if (this.contains("Activated") || this.contains("Available") ) 0 else 10000000
    val voucherDate = this.substring(0,6).toInt() * 10
    val voucherstatusValue = if (this.contains("Activated")) 0 else 1

    return voucherStatusGroup + voucherDate + voucherstatusValue
}