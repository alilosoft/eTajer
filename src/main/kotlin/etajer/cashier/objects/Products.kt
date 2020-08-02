package etajer.cashier.objects

import java.util.*

interface Products {
    fun byId(id: Int): Optional<Product>
    fun byBarCode(barCode: String): Optional<Product>
}