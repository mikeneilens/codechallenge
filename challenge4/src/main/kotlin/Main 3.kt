fun sortVouchers(vouchers: String): String {
    return vouchers
        .split(",")
        .sortedBy { voucher -> voucher.rank() }
        .joinToString(",")
}

//rank is an 8 digit number used to rank vouchers
fun String.rank():Int {
    val isCurrentReward = (this.contains("Activated") || this.contains("Available") )

    val voucherStatusGroupRanking = if (isCurrentReward)  0 else 10000000
    val voucherDateRanking = if (isCurrentReward) this.substring(0,6).toInt() * 10 else 9999999 - this.substring(0,6).toInt() * 10
    val voucherStatusRanking = if (this.contains("Activated") || this.contains("Redeemed")) 0 else 1

    return voucherStatusGroupRanking + voucherDateRanking + voucherStatusRanking
}