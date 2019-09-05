sealed class Balance {
    val value:Int get() = when (this) {
        is Credit ->  this.gbp.value
        is Debt -> - this.gbp.value
    }

    operator fun plus(other:Balance):Balance {
        val totalValue = this.value + other.value
        return if (totalValue >= 0) Credit(GBP(totalValue)) else Debt(GBP(totalValue))
    }

    override fun equals(other: Any?): Boolean {
        return other is Balance && ((this is Credit && other is Credit) || (this is Debt && other is Debt)) && (this.value == other.value)
    }
}
data class Credit(val gbp:GBP):Balance()
data class Debt(val gbp:GBP):Balance()
