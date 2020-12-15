package etajer.api.cart

import etajer.api.sale.Sale
import java.time.LocalDate
import java.time.LocalTime

interface Cart : Iterable<CartItem> {
    val number: Int
    val date: LocalDate
    val time: LocalTime

    /** add an item to the Cart by its SKU */
    fun addBySku(sku: String, qty: Int = 1): CartItem?

    // TODO: return a boolean for add and remove
    fun addItem(item: CartItem) // TODO: remove
    fun removeItem(item: CartItem)
    fun total() = sumByDouble { it.total() }
    fun checkout(): Sale
}