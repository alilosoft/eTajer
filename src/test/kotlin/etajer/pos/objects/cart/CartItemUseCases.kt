package etajer.pos.objects.cart

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
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

}

typealias CartItemBySkuFn = (String) -> CartItem

val fakeCartItemBySkuFn: CartItemBySkuFn = { sku -> createFakeCartItem(sku) }

// This function could be part of a Cart API -> TODO!
fun createFakeCartItem(sku: String,
                       qty: Int = 1,
                       saleUnitBySku: SaleUnitBySku = FakeSaleUnits): CartItem {

    return when (val found = saleUnitBySku.find(sku)) {
        is Some ->
            object : CartItem {
                override val itemName: String = found.value.name
                override val itemPrice: Double = found.value.price
                override val soldQty: Int = qty
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
     * final sale price of this cart item (after discount or any calc)
     */
    val itemPrice: Double

    /**
     * total price of this item
     */
    fun total() = soldQty * itemPrice
}