package etajer.cashier.objects

import java.util.*

interface Products {
    /**
     * Assuming that a Product should exist with the given `id`, this method will return an instance of Product.
     * Otherwise any implementation should throw an IllegalStateException
     */
    fun byId(id: Int): Optional<Product>
    fun byBarCode(barCode: String): Optional<Product>
}