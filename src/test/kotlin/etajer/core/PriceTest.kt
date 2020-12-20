package etajer.core

import etajer.api.Price
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class PriceTest {
    @Test
    fun `understanding BigDecimal scale & precision`() {
        listOf(
            BigDecimal.ZERO,
            BigDecimal.TEN,
            BigDecimal(15.25),
            BigDecimal.valueOf(1525, 2),
            BigDecimal.valueOf(1525),
        ).forEach { p ->
            println("val: $p, scale: ${p.scale()}, precis: ${p.precision()}, unscaled: ${p.unscaledValue()}")
        }
    }

    @Test
    fun `create Price from cents`() {
        assertEquals(
            BigDecimal.valueOf(1250, 2),
            Price.Cents(1250).value
        )
        assertEquals(
            BigDecimal.valueOf(12.50).setScale(2),
            Price.Cents(1250).value
        )
        assertEquals(
            BigDecimal("12.50"),
            Price.Cents(1250).value
        )
        assertEquals(
            BigDecimal("12.50"),
            Price.Fixed(1250).value
        )
    }

    @Test
    fun `create Price from double val`() {
        listOf(
            Price.Fixed(1.252345),
            Price.Fixed(1.264567, RoundingMode.FLOOR),
        ).forEach { p ->
            println(p.value)
            assertEquals(BigDecimal.valueOf(1.26), p.value)
        }
    }
}