package etajer.pos.objects.cart

import etajer.pos.data.fake.FakeSKUs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartItemsUseCases {
    @Test
    fun `CartItems should create a CartItem by SKU`() {
        // Arrange
        val items = object : CartItems {
            // could/should be a real DB
            private val data = mutableMapOf<String, CartItem>()

            // could/should be passed as a param to ctor
            private val itemBySkuFn: (String) -> CartItem = { sku -> createCartItemBySku(sku) }

            override val cart: Cart
                get() = TODO("Not yet implemented")

            override fun bySku(sku: String): CartItem {
                val item = itemBySkuFn(sku)
                data[sku] = item
                return item
            }

            override fun iterator(): Iterator<CartItem> {
                return data.values.iterator()
            }
        }
        // Act
        items.bySku(FakeSKUs.FACTO)
        items.bySku(FakeSKUs.IFRI6B)
        // Assert
        assertEquals(2, items.count())
    }
}

interface CartItems : Iterable<CartItem> {
    val cart: Cart
    fun bySku(sku: String): CartItem
    //fun remove(item: CartItem) TODO: maybe
}