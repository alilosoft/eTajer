package etajer.api.sale

import java.math.BigDecimal

// See selfxdsd.api.InvoicedTask & Task for similar relation
// as SoldItem to CartItem
interface SoldItem {
    val desc: String
    val qty: Int
    val price: BigDecimal
    val cost: BigDecimal
}

interface SoldItems : Iterable<SoldItem>