package etajer.api

import java.math.BigDecimal
import java.math.RoundingMode

interface Price {
    val value: BigDecimal

    data class Fixed constructor(override val value: BigDecimal) : Price {
        constructor(
            doubleValue: Double,
            rounding: RoundingMode = RoundingMode.CEILING
        ) : this(
            BigDecimal.valueOf(doubleValue).setScale(2, rounding)
        )
        constructor(priceInCents: Long) : this(
            BigDecimal.valueOf(priceInCents, 2)
        )
    }

    class Cents(private val cents: Long) : Price {
        override val value: BigDecimal = BigDecimal.valueOf(cents, 2)
    }
}