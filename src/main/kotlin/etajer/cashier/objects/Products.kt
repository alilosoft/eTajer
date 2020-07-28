package etajer.cashier.objects

import java.util.*

interface Products {
    fun find(id: Int): Optional<Product>
}