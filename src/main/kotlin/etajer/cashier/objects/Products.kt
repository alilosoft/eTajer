package etajer.cashier.objects

import java.util.*

interface Products {
    fun get(id: Int): Optional<Product>
}