package etajer.fake.cart

import etajer.api.cart.CartItem
import etajer.api.product.SaleUnitBySku
import etajer.api.sale.SoldItems
import etajer.fake.FakeSaleUnits
import etajer.fake.FakeSku
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

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
        assertEquals(coffeeFacto.desc, cartItem.itemName)
        assertEquals(coffeeFacto.price, cartItem.itemPrice)
    }

    @Test
    fun `return null when the SKU is not found`() {
        // Arrange
        // Act
        // Assert
        assertNull(createFakeCartItem("INVALID"))
    }

    @Test
    fun `a CartItem can change the qty of sold Units`() {
        createFakeCartItem(FakeSku.FACTO)?.apply {
            assertEquals(1, soldQty)
            changeQty(5)
            assertEquals(5, soldQty)
        }
    }

    @Test
    fun `using Result as return type example`() {
        fakeCartItemBySkuFn(FakeSku.FACTO, 3).onSuccess {
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
            "name" to unit.desc,
            "price" to unit.price,
            "qty" to qty
        )
        override val itemName: String = data["name"] as String
        override val soldQty: Int get() = data["qty"] as Int
        override val itemPrice: BigDecimal = data["price"] as BigDecimal
        override fun changeQty(newQty: Int) = synchronized(this) { data["qty"] = newQty }
        override fun checkout(): SoldItems {
            TODO("Not yet implemented")
        }

        override fun toString(): String {
            return "FakeCartItem[sku:$sku, desc:$itemName, price:$itemPrice, qty:$soldQty]"
        }
    }
}