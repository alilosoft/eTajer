package etajer.pos.objects.cart

import etajer.pos.data.fake.FakeSKUs
import etajer.pos.objects.CartItem
import etajer.pos.objects.createCartItemBySku
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

interface Cart : Iterable<CartItem> {
    val number: Int
    val date: LocalDate
    val time: LocalTime

    // TODO: return a boolean for add and remove
    fun addItem(item: CartItem)
    fun removeItem(item: CartItem)
}

val fakeCart = object : Cart {
    override val number: Int = 1
    override val date: LocalDate = LocalDate.now()
    override val time: LocalTime = LocalTime.now()

    private val items = mutableListOf<CartItem>()
    override fun addItem(item: CartItem) {
        items.add(item)
    }

    override fun removeItem(item: CartItem) {
        items.remove(item)
    }

    override fun iterator(): Iterator<CartItem> = items.iterator()

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
        fakeCart.addItem(facto)
        println(fakeCart)
        // Assert
        assertTrue(fakeCart.iterator().hasNext())
    }

    @Test
    fun `remove an (existing) item from th Cart`() {
        // Arrange
        val facto = createCartItemBySku(FakeSKUs.FACTO, 2)
        // Act
        fakeCart.addItem(facto)
        assertTrue(fakeCart.iterator().hasNext())
        fakeCart.removeItem(facto)
        // Assert
        assertFalse(fakeCart.iterator().hasNext())
    }
}