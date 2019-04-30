fun main(args: Array<String>) {
    println("Hello world")
}

fun sortVouchers(vouchers: String): String {
    return vouchers
        .split(";")
        .sortedBy { voucher -> voucher.rank() }
        .joinToString(";")
}

//rank is an 8 digit number used to rank vouchers
fun String.rank():Int {
    val isCurrentReward = (this.contains("Activated") || this.contains("Available") )

    val voucherStatusGroup = if (isCurrentReward)  0 else 10000000
    val voucherDate = if (isCurrentReward) this.substring(0,6).toInt() * 10 else 9999999 - this.substring(0,6).toInt() * 10
    val voucherstatusValue = if (this.contains("Activated") || this.contains("Expired")) 0 else 1

    return voucherStatusGroup + voucherDate + voucherstatusValue
}