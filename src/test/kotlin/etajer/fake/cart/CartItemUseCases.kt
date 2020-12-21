package etajer.fake.cart

import arrow.core.Either
import arrow.core.Left
import etajer.api.cart.CartItem
import etajer.api.product.SaleUnitBySku
import etajer.api.sale.SoldItems
import etajer.core.FailMsg
import etajer.fake.FakeSaleUnits
import etajer.fake.FakeSku
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CartItemUseCases {

    @Test
    fun `create a CartItem using a valid SKU`() {
        // Arrange
        val coffeeFacto = FakeSaleUnits.coffeeFacto
        // fake SaleUnitBySku using SAM impl.
        val fakeSaleUnitBySku = SaleUnitBySku { Either.right(coffeeFacto) }
        // Act
        createFakeCartItem(
            sku = "VALID SKU",
            saleUnitBySku = fakeSaleUnitBySku
        ).map { cartItem ->
            // Assert
            assertEquals(coffeeFacto.desc, cartItem.desc)
            assertEquals(coffeeFacto.price, cartItem.itemPrice)
        }
    }

    @Test
    fun `fail to create a CartItem with invalid SKU`() {
        // Arrange
        // Act
        // Assert
        createFakeCartItem(
            sku = "?",
            saleUnitBySku = { Left("SKU not found!") }
        ).mapLeft { failMsg ->
            assertTrue(failMsg is FailMsg)
            assertTrue(failMsg.msg().contains("SKU not found!"))
        }.map { assertTrue(it is Nothing) }
    }

    @Test
    fun `a CartItem can change the qty of sold Units`() {
        createFakeCartItem(FakeSku.FACTO).map { item ->
            assertEquals(1, item.soldQty)
            item.changeQty(5)
            assertEquals(5, item.soldQty)
        }
    }

    @Test
    fun `using Result as return type, on success`() {
        fakeCartItemBySkuFn(FakeSku.FACTO, 3).onSuccess {
            assertEquals(3, it.soldQty)
            it.changeQty(5)
            assertEquals(5, it.soldQty)
        }
    }

    @Test
    fun `using Result as return type, on failure`() {
        fakeCartItemBySkuFn("invalid", 1).onFailure {
            println(it.message)
            assertTrue(it.message?.contains("invalid") ?: false)
        }
    }
}

/**
 * Experimenting with kotlin.Result as return type
 */
typealias CartItemBySkuResult = (String, Int) -> Result<CartItem>

val fakeCartItemBySkuFn: CartItemBySkuResult = { sku, qty ->
    createFakeCartItem(sku, qty).fold(
        { fail -> Result.failure(Throwable(fail.msg())) },
        { item -> Result.success(item) }
    )
}

fun createFakeCartItem(
    sku: String,
    qty: Int = 1,
    saleUnitBySku: SaleUnitBySku = FakeSaleUnits
): Either<FailMsg, CartItem> { // TODO: think of returning Result/Either
    return saleUnitBySku.find(sku)
        .map { foundUnit ->
            object : CartItem {
                private val data = mutableMapOf<String, Any>(
                    "desc" to foundUnit.desc,
                    "price" to foundUnit.price,
                    "qty" to qty
                )
                override val desc: String = data["desc"] as String
                override val soldQty: Int get() = data["qty"] as Int
                override val itemPrice: BigDecimal = data["price"] as BigDecimal
                override fun changeQty(newQty: Int) = synchronized(this) { data["qty"] = newQty }
                override fun checkout(): SoldItems {
                    TODO("Not yet implemented")
                }

                override fun toString(): String {
                    return "FakeCartItem[sku:$sku, desc:$desc, price:$itemPrice, qty:$soldQty]"
                }
            } //as CartItem
        }
        .mapLeft { msg -> FailMsg { "Can't create Cart item, $msg" } }
}