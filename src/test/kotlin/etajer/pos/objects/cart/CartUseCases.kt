package etajer.pos.objects.cart

import etajer.pos.data.fake.FakeSKUs
import etajer.pos.data.fake.FakeSaleUnits
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

class CartUseCases {
    @Test
    fun `add an item to a Cart (fake) impl`() {
        // Arrange
        val fakeCart = createFakeCart()
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
        val fakeCart = createFakeCart()
        val facto = createCartItemBySku(FakeSKUs.FACTO, 2)
        // Act
        fakeCart.addItem(facto)
        assertTrue(fakeCart.iterator().hasNext())
        fakeCart.removeItem(facto)
        // Assert
        assertFalse(fakeCart.iterator().hasNext())
    }

    @Test
    fun `add an item to the Cart with invalid SKU`() {
        // Arrange
        val cart = createFakeCart()
        val sku = "invalid sku"
        // Act
        val ex = assertThrows<IllegalArgumentException> { cart.addBySku(sku) }
        // Assert
        assertTrue(ex.message?.contains(sku) ?: false)
    }

    @Test
    fun `add an item to the Cart with valid SKU`() {
        // Arrange
        val cart = createFakeCart()
        // Act
        val item = cart.addBySku(FakeSKUs.FACTO)
        // Assert
        assertTrue(cart.contains(item))
    }
}

interface Cart : Iterable<CartItem> {
    val number: Int
    val date: LocalDate
    val time: LocalTime

    // TODO: return a boolean for add and remove
    /** add an item to the Cart by its SKU */
    fun addBySku(sku: String): CartItem
    fun addItem(item: CartItem)
    fun removeItem(item: CartItem)
    fun total() = sumByDouble { it.total() }
}


fun createFakeCart(number: Int = -1,
                   date: LocalDate = LocalDate.now(),
                   time: LocalTime = LocalTime.now()) =
        object : Cart {
            override val number: Int = number
            override val date: LocalDate = date
            override val time: LocalTime = time

            // encapsulated data/db where the Cart items could be stored
            private val items = mutableListOf<CartItem>()

            // TODO: chose between the following impls.
            // encapsulated dep. should be passed to a real impl. via the ctor.
            private val unitBySku: SaleUnitBySku = FakeSaleUnits // SAM object

            // encapsulated dep. should be passed to real impl. via ctor.
            private val createItemBySku: CartItemBySkuFn = fakeCartItemBySkuFn // functional

            // create, return & store the item in the Cart items data
            override fun addBySku(sku: String): CartItem = createItemBySku(sku).apply(items::add)

            override fun addItem(item: CartItem) {
                items.add(item)
            }

            override fun removeItem(item: CartItem) {
                items.remove(item)
            }

            override fun iterator(): Iterator<CartItem> = items.iterator()

            override fun toString(): String {
                return """Cart (FakeImpl) N°: $number, Date: $date, Time: $time"""
            }
        }