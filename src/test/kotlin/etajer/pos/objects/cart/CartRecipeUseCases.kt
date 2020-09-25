package etajer.pos.objects.cart

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

interface CartRecipe {
    fun print()
}

class CartRecipeUseCases {
    @Test
    fun `print CartRecipe to console`() {
        // Arrange
        var printSpy = false;
        val consoleRecipe = object : CartRecipe {
            // a real impl could/should get the Cart by it's ctor.
            private val cart = fakeCart
            override fun print() {
                println(fakeCart)
                printSpy = true
            }
        }
        // Act
        consoleRecipe.print()
        // Assert
        assertTrue(printSpy)
    }
}