package etajer.fake.cart

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.json.Json
import javax.json.JsonObject

interface Sale : Iterable<SoldItem> {
    val number: String
    val time: LocalDateTime
    fun seller(): String = "Cashier"
    fun total(): BigDecimal = this.sumOf { item -> item.price.multiply(item.qty.toBigDecimal()) }
    fun profit(): BigDecimal = this.total().minus(this.sumOf { item -> item.cost.multiply(item.qty.toBigDecimal()) })
    fun payments(): Payments = object : Payments {
        val payments = listOf(object : Payment {
            override fun value(): BigDecimal = this@Sale.total()
            override val mode: String = "Cash"
        })
        override fun iterator(): Iterator<Payment> = payments.iterator()
    }
}

// See an actual usage example of Iterable & Iterator
// https://github.com/amihaiemil/self-core/blob/e9532c47e7d80fc2bbeb60fe5a8392650901e572/self-core-impl/src/main/java/com/selfxdsd/core/GitlabWebhooks.java#L153
class CartSale(private val cartId: Int) : Sale {
    override val number: String
        get() = TODO("Not yet implemented")
    override val time: LocalDateTime
        get() = TODO("Not yet implemented")

    override fun iterator(): Iterator<SoldItem> {
        TODO("Not yet implemented")
    }
}

// See selfxdsd.api.InvoicedTask & Task for similar relation
// as SoldItem with CartItem
interface SoldItem {
    val desc: String
    val qty: Int
    val price: BigDecimal
    val cost: BigDecimal
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

interface SoldItems : Iterable<SoldItem>

interface Payment {
    fun value(): BigDecimal
    val mode: String
}

interface Payments : Iterable<Payment> {
    fun total(): BigDecimal = this.sumOf { p -> p.value() }
}


