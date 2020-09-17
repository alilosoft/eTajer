package etajer.cashier.objects

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import etajer.cashier.data.fake.FakeSaleUnits
import org.junit.jupiter.api.Test

class CartItemTests {
    @Test
    fun `create CartItem with SKU`() {
        // Arrange
        // Act
        // Assert

    }
}

fun createCartItem(sku: String,
                   qty: Int,
                   saleUnitBySku: SaleUnitBySku = FakeSaleUnits): CartItem {

    return when (val found = saleUnitBySku.find(sku)) {
        is Some ->
            object : CartItem {
                val barCode: String = sku // TODO: remove if not needed!
                override val itemName: String = found.value.name // + (sale unit qty)
                override val itemPrice: Double = found.value.price
                override val soldQty: Int = qty

            }
        is None ->
            throw IllegalStateException("Item with barcode $barCode not found!")
    }
}

fun interface SaleUnitBySku {
    fun find(sku: String): Optional<SaleUnit>
}

interface CartItem {
    val itemName: String // Water Ifri
    val soldQty: Int //
    val itemPrice: Double // 180.00
    fun total() = soldQty * itemPrice
}