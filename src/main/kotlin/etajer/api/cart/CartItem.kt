package etajer.api.cart

import etajer.api.sale.SoldItems
import java.math.BigDecimal

interface CartItem {
    /**
     * name of the sold item as shown to user (in a receipt for example)
     */
    val itemName: String

    /**
     * number of sold units in this item
     */
    val soldQty: Int

    /**
     * change the Qty of sold units by this item
     */
    fun changeQty(newQty: Int)

    /**
     * final sale price of this cart item (after discount or any calc)
     */
    val itemPrice: BigDecimal

    /**
     * total price of this item
     */
    fun total(): BigDecimal = itemPrice.multiply(BigDecimal(soldQty))

    fun checkout(): SoldItems
}