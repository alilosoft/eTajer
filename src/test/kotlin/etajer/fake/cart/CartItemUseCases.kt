package etajer.fake.cart

import etajer.fake.FakeSKUs
import etajer.fake.FakeSaleUnits
import etajer.api.product.SaleUnit
import etajer.api.cart.CartItem
import etajer.api.sale.SoldItems
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartItemUseCases {

    @Test
    fun `create a CartItem using a valid SKU`() {
        // Arrange
        // prepare a fake impl of SaleUnitBySku which returns always a valid fake SaleUnit impl!
        val coffeeFacto = FakeSaleUnits.coffeeFacto
        val fakeSaleUnitBySku = SaleUnitBySku { coffeeFacto }
        // Act
        val cartItem = createFakeCartItem(
            sku = "VALID SKU",
            saleUnitBySku = fakeSaleUnitBySku
        )!!
        // Assert
        assertEquals(coffeeFacto.name, cartItem.itemName)
        assertEquals(coffeeFacto.price, cartItem.itemPrice)
    }

    @Test
    fun `throws an exception when the SKU is not found`() {
        // Arrange
        // Act
        val ex = assertThrows<Throwable> { createFakeCartItem("INVALID") }
        // Assert
        println(ex.message)
    }

    @Test
    fun `a CartItem can change the qty of sold Units`() {
        createFakeCartItem(FakeSKUs.FACTO)?.apply {
            assertEquals(1, soldQty)
            changeQty(5)
            assertEquals(5, soldQty)
        }
    }

    @Test
    fun `using Result as return type example`() {
        fakeCartItemBySkuFn(FakeSKUs.FACTO, 3).onSuccess {
            assertEquals(3, it.soldQty)
            it.changeQty(5)
            assertEquals(5, it.soldQty)
        }
    }
}

typealias CartItemBySkuFn = (String, Int) -> Result<CartItem>

val fakeCartItemBySkuFn: CartItemBySkuFn = { sku, qty ->
    val item = createFakeCartItem(sku, qty)
    if (item != null) Result.success(item)
    else Result.failure(Throwable("Can't create CartItem with this SKU: $sku"))
}

// This function could be part of a Cart API -> TODO!
fun createFakeCartItem(
    sku: String,
    qty: Int = 1,
    saleUnitBySku: SaleUnitBySku = FakeSaleUnits
): CartItem? { // TODO: think of returning Result/Either
    val unit = saleUnitBySku.find(sku) ?: return null
    return object : CartItem {
        private val data = mutableMapOf<String, Any>(
            "name" to unit.name,
            "price" to unit.price,
            "qty" to qty
        )
        override val itemName: String = data["name"] as String
        override val soldQty: Int // TODO: use a function instead of property with getter
            get() = data["qty"] as Int
        override val itemPrice: Double = data["price"] as Double
        override fun changeQty(newQty: Int) = synchronized(this) { data["qty"] = newQty }
        override fun checkout(): SoldItems {
            TODO("Not yet implemented")
        }
        override fun toString(): String {
            return "FakeCartItem[sku:$sku, desc:$itemName, price:$itemPrice, qty:$soldQty]"
        }
    }
}

/**
 * Clients that needs to find a SaleUnit using its SKU need to provide an Impl of this interface
 */
fun interface SaleUnitBySku {
    fun find(sku: String): SaleUnit?
}

