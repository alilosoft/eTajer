package etajer.cashier.objects.cart

import etajer.cashier.objects.CartItem
import java.time.LocalDate
import java.time.LocalTime

interface Cart : MutableIterable<CartItem> {
    val number: Int
    val date: LocalDate
    val time: LocalTime

    fun addItem(item: CartItem)
}