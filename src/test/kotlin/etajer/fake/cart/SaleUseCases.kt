package etajer.fake.cart

import etajer.api.sale.Sale
import etajer.api.sale.SoldItem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.json.Json
import javax.json.JsonObject

// See an actual usage example of Iterable & Iterator
// https://github.com/amihaiemil/self-core/blob/e9532c47e7d80fc2bbeb60fe5a8392650901e572/self-core-impl/src/main/java/com/selfxdsd/core/GitlabWebhooks.java#L153
class CartSale(private val cartId: Int) : Sale {
    override val number: String
        get() = TODO("Not yet implemented")
    override val time: LocalDateTime
        get() = TODO("Not yet implemented")

    override fun iterator(): Iterator<SoldItem> {
        // get the CartItems of this Cart
        // get the SoldItems for each CartItem
        // return all SoldItems
        TODO("Not yet implemented")
    }
}

class SoldCartItem(
    cartItemId: Long, // or use an id to fetch data from a sql db
    private val data: JsonObject, // just experimenting
): SoldItem {

    override val desc: String
        get() = data.getString(this::desc.name)
    override val qty: Int
        get() = TODO("Not yet implemented")
    override val price: BigDecimal
        get() = TODO("Not yet implemented")
    override val cost: BigDecimal
        get() = TODO("Not yet implemented")

}

class SoldCartItemTest {
    @Test
    fun `new feature test`() {
        Assertions.assertEquals(
            "test",
            SoldCartItem(
                0L,
                Json.createObjectBuilder().add(
                    SoldItem::desc.name,
                    "test"
                ).build()
            ).desc
        )
    }
}


