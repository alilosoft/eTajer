package etajer.api

import etajer.api.inventory.Inventory
import etajer.api.product.Products
import etajer.api.purchase.Purchases
import etajer.api.sale.Cashiers
import etajer.api.sale.Sales

interface ETajer {
    val name: String //
    val company: Company // store info
    val users: Users // logins
    val products: Products // catalog
    val inventory: Inventory // -> inventory transactions and state
    val purchase: Purchases // -> inventory & costs
    val sales: Sales // -> income & profit
    val cashiers: Cashiers // -> income (cash)
}