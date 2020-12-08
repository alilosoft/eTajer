package etajer.api

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