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

fun createCartItem(barCode: String,
                   qty: Int,
                   findSaleUnitBySku: (String) -> Optional<SaleUnit> = FakeSaleUnits::bySku): CartItem {

    return when (val found = findSaleUnitBySku(barCode)) {
        is Some ->
            object : CartItem {
                val barCode: String = barCode // TODO: remove if not needed!
                override val itemName: String = found.value.name // + (sale unit qty)
                override val itemPrice: Double = found.value.price
                override val soldQty: Int = qty

            }
        is None ->
            throw IllegalStateException("Item with barcode $barCode not found!")
    }
}

interface CartItem {
    val itemName: String // Water Ifri
    val soldQty: Int //
    val itemPrice: Double // 180.00
    fun total() = soldQty * itemPrice
}