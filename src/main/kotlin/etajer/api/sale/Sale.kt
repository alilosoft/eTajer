package etajer.api.sale

import java.math.BigDecimal
import java.time.LocalDateTime

// TODO: remove default impl
interface Sale : Iterable<SoldItem> {
    val number: String
    val time: LocalDateTime
    fun seller(): String = "Cashier"
    fun total(): BigDecimal = this.sumOf { item -> item.price.multiply(item.qty.toBigDecimal()) }
    fun profit(): BigDecimal = this.total().minus(this.sumOf { item -> item.cost.multiply(item.qty.toBigDecimal()) })
    fun payments(): Payments = object : Payments {
        val payments = listOf(object : Payment {
            override fun value(): BigDecimal = this@Sale.total()
            override val mode: String = "Cash"
        })

        override fun iterator(): Iterator<Payment> = payments.iterator()
    }
}

interface Sales : Iterable<Sale>