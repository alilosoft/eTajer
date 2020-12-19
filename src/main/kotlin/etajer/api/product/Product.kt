package etajer.api.product

import java.math.BigDecimal

interface Product {
    val name: String
    val purchasePrice: BigDecimal
    val salePrice: BigDecimal
    val qty: Int
    val saleUnits: SaleUnits
}