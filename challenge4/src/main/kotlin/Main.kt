fun main(args: Array<String>) {
    println("Hello world")
}

fun sortVouchers(vouchers: String): String {
    return vouchers
        .split(";")
        .sortedBy { voucher -> voucher.rank() }
        .joinToString(";")
}

fun String.rank():Int {
    val voucherStatusGroup = if (this.contains("Activated") || this.contains("Available") ) 0 else 10000000
    val voucherDate = this.substring(0,6).toInt() * 10
    val voucherstatusValue = if (this.contains("Activated")) 0 else 1

    return voucherStatusGroup + voucherDate + voucherstatusValue
}