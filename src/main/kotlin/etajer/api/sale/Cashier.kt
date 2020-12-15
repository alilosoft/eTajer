package etajer.api.sale

interface Cashier {
    fun name(): String
}

interface Cashiers : Iterable<Cashier>