package etajer.core.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

// inspired by: EO &
// https://amihaiemil.com/2017/11/04/but-how-do-you-work-without-a-model.html
class PriceTests {

    private fun getFakePrice() = object : Price {
        var seed = 100
        override val value: Double
            get() {
                println("Calculating price/Fetching from DB")
                return Random(seed++).nextDouble() * 100
            }
    }

    @Test
    fun `a Price should have a value`() {
        // Arrange
        val fakePrice = getFakePrice()
        // Act
        val p1 = fakePrice.value
        val p2 = fakePrice.value
        println("p1=$p1, p2=$p2")
        // Assert
        assertNotEquals(p1, p2)
    }

    @Test
    fun `price value could be cached for performance`() {
        // Arrange
        val cachedPrice = CachedPrice(getFakePrice())
        // Act
        val p1 = cachedPrice.value
        val p2 = cachedPrice.value
        println("p1=$p1, p2=$p2")
        // Assert
        assertEquals(p1, p2)
        cachedPrice.currency()
    }
}

interface Price {
    val value: Double
    fun currency() = "DZD"
}

class CachedPrice(private val decorated: Price) : Price {
    override val value: Double by lazy { decorated.value }
}