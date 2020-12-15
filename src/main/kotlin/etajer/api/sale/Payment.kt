package etajer.api.sale

import java.math.BigDecimal

interface Payment {
    fun value(): BigDecimal
    val mode: String
}

interface Payments : Iterable<Payment> {
    fun total(): BigDecimal = this.sumOf { p -> p.value() }
}