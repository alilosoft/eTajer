package etajer.pos.objects.cart

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
import etajer.pos.data.fake.FakeSKUs
import etajer.pos.data.fake.FakeSaleUnits
import etajer.pos.objects.SaleUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartItemUseCases {

    @Test
    fun `when the SKU exist, then create a CartItem`() {
        // Arrange
        // prepare a fake impl of SaleUnitBySku which returns always a valid fake SaleUnit impl!
        val coffeeFacto = FakeSaleUnits.coffeeFacto
        val fakeSaleUnitBySku = SaleUnitBySku { coffeeFacto.toOptional() }
        // Act
        val cartItem = createFakeCartItem("VALID SKU", saleUnitBySku = fakeSaleUnitBySku)
        // Assert
        assertEquals(coffeeFacto.name, cartItem.itemName)
        assertEquals(coffeeFacto.price, cartItem.itemPrice)
    }

    @Test
    fun `when the SKU is not found, then throws an exception`() {
        // Arrange
        val invalidSku = "INVALID"
        // Act
        val ex = assertThrows<Exception> { createFakeCartItem(invalidSku) }
        // Assert
        assertTrue(ex is IllegalArgumentException)
        println(ex.message)
    }

    @Test
    fun `a CartItem could change the qty of sold Units`() {
        // Arrange
        val item = createFakeCartItem(sku = FakeSKUs.FACTO, qty = 2)
        assertEquals(2, item.soldQty)
        // Act
        item.changeQty(5)
        // Assert
        assertEquals(5, item.soldQty)
    }

}

typealias CartItemBySkuFn = (String, Int) -> CartItem

val fakeCartItemBySkuFn: CartItemBySkuFn = { sku, qty -> createFakeCartItem(sku, qty) }

// This function could be part of a Cart API -> TODO!
fun createFakeCartItem(sku: String,
                       qty: Int = 1,
                       saleUnitBySku: SaleUnitBySku = FakeSaleUnits): CartItem {

    return when (val saleUnit = saleUnitBySku.find(sku)) {
        is Some ->
            object : CartItem {
                private val data = mutableMapOf<String, Any>(
                        "name" to saleUnit.value.name,
                        "price" to saleUnit.value.price,
                        "qty" to qty)

                override val itemName: String = data["name"] as String
                override val soldQty: Int // TODO: use a function instead of property with getter
                    get() = data["qty"] as Int
                override val itemPrice: Double = data["price"] as Double

                override fun changeQty(newQty: Int) = synchronized(this) { data["qty"] = newQty }
            }
        is None ->
            // TODO: throw custom exception or return Optional
            throw IllegalArgumentException("SKU $sku not found!")
    }
}

/**
 * Clients that needs to find a SaleUnit using its SKU need to provide an Impl of this interface
 */
fun interface SaleUnitBySku {
    fun find(sku: String): Optional<SaleUnit>
}

interface CartItem {
    /**
     * name of the sold item as shown to user (in a receipt for example)
     */
    val itemName: String

    /**
     * number of sold units in this item
     */
    val soldQty: Int

    /**
     * change the Qty of sold units by this item
     */
    fun changeQty(newQty: Int)

    /**
     * final sale price of this cart item (after discount or any calc)
     */
    val itemPrice: Double

    /**
     * total price of this item
     */
    fun total() = soldQty * itemPrice
}