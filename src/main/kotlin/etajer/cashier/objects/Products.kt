package etajer.cashier.objects

import com.gojuno.koptional.Optional


interface Products {
    fun add(name: String, price: Double): Product
    /**
     * Assuming that a Product should exist with the given `id`, this method will return an instance of Product.
     * Otherwise any implementation should throw an IllegalStateException
     */
    fun byId(id: Int): Optional<Product>
    fun byBarCode(barCode: String): Optional<Product>
}