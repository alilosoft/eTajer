package etajer.cashier.objects.cart

import etajer.cashier.data.fake.FakeSKUs
import etajer.cashier.objects.CartItem
import etajer.cashier.objects.createCartItemBySku
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

interface Cart : MutableIterable<CartItem> {
    val number: Int
    val date: LocalDate
    val time: LocalTime

    fun addItem(item: CartItem)
}

val fakeSimpleCart = object : Cart {
    override val number: Int = 1
    override val date: LocalDate = LocalDate.now()
    override val time: LocalTime = LocalTime.now()

    private val items = mutableListOf<CartItem>()
    override fun addItem(item: CartItem) {
        items.add(item)
    }

    override fun iterator(): MutableIterator<CartItem> = items.iterator()

    override fun toString(): String {

        return """Cart (FakeImpl) N°: $number, Date: $date, Time: $time
            |Items: 
            |${items.map { "${it.itemName} Price: ${it.itemPrice} Qty: ${it.soldQty}" }}
        """.trimMargin()
    }
}

class CartUseCases {
    @Test
    fun `add an item to a Cart (fake) impl`() {
        // Arrange
        val facto = createCartItemBySku(FakeSKUs.FACTO, 2)
        // Act
        fakeSimpleCart.addItem(facto)
        println(fakeSimpleCart)
        // Assert
        assertEquals(1, fakeSimpleCart.toList().size)
    }
}