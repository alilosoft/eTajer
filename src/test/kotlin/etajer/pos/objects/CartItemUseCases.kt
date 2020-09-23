package etajer.pos.objects

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
import etajer.pos.data.fake.FakeSaleUnits
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
        val cartItem = createCartItemBySku("VALID SKU", saleUnitBySku = fakeSaleUnitBySku)
        // Assert
        assertEquals(coffeeFacto.name, cartItem.itemName)
        assertEquals(coffeeFacto.price, cartItem.itemPrice)
    }

    @Test
    fun `when the SKU is not found, then throws an exception`() {
        // Arrange
        val invalidSku = "INVALID"
        // Act
        val ex = assertThrows<Exception> { createCartItemBySku(invalidSku) }
        // Assert
        assertTrue(ex is IllegalArgumentException)
        println(ex.message)
    }

}

// This function could be part of a Cart API -> TODO!
fun createCartItemBySku(sku: String,
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
 * Clients that needs to create a CartItem using a SKU need to provide an Impl of this interface
 */
fun interface SaleUnitBySku {
    fun find(sku: String): Optional<SaleUnit>
}

interface CartItem {
    val itemName: String // as shown to user in receipt
    val soldQty: Int // number of units sold of an item
    val itemPrice: Double // final sale price (after discount if any)
    fun total() = soldQty * itemPrice
}