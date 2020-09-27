package etajer.pos.objects.cart

import etajer.pos.data.fake.FakeSKUs
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

    @Test
    fun `CartRecipe decide how to print a Cart`() {
        // Arrange
        fakeCart.addItem(createCartItemBySku(FakeSKUs.IFRI1B, 3))
        fakeCart.addItem(createCartItemBySku(FakeSKUs.IFRI6B, 1))
        fakeCart.addItem(createCartItemBySku(FakeSKUs.FACTO, 2))
        val formattedRecipe = object : CartRecipe {
            private val cart = fakeCart
            override fun print() {
                val lineFormat = "%-25s | %-5s| %-3s| %-5s"

                val recipe = """Recipe N°: ${cart.number}, Date: ${cart.date}, Time: ${"%tT".format(cart.time)} 
                    |${lineFormat.format("Product", "Price", "Qty", "Total")}
                    |${"-".repeat(45)}
                    |${
                    cart.fold("", { acc, item -> "$acc${lineFormat.format(item.itemName, item.itemPrice, item.soldQty, item.total())}\n" })
                }""".trimMargin()
                println(recipe)
            }
        }
        // Act
        formattedRecipe.print()
        // Assert
    }
}