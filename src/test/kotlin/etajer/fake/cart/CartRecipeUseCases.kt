package etajer.fake.cart

import etajer.api.cart.Cart
import etajer.fake.FakeSKUs
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

interface CartRecipe {
    fun print()
}

fun consoleRecipe(cart: Cart) = object : CartRecipe {
    override fun print() {
        val lineFormat = "%-25s | %-5s| %-3s| %-5s"

        val recipe = """Recipe N°: ${cart.number}, Date: ${cart.date}, Time: ${"%tT".format(cart.time)} 
                    |${lineFormat.format("Product", "Price", "Qty", "Total")}
                    |${"-".repeat(45)}
                    |${
            cart.fold("", { acc, item -> "$acc${lineFormat.format(item.itemName, item.itemPrice, item.soldQty, item.total())}\n" })
        }
        |${"-".repeat(45)}
        |${"%45s".format("Total: ${cart.total()}\n")}""".trimMargin()
        println(recipe)
    }
}

class CartRecipeUseCases {
    @Test
    fun `print CartRecipe to console`() {
        // Arrange
        var printSpy = false;
        val consoleRecipe = object : CartRecipe {
            // a real impl could/should get the Cart by it's ctor.
            private val cart = createFakeCart()
            override fun print() {
                println(cart)
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
        val cart = createFakeCart().apply {
            addItem(createFakeCartItem(FakeSKUs.IFRI1B, 3)!!)
            addItem(createFakeCartItem(FakeSKUs.IFRI6B, 1)!!)
            addItem(createFakeCartItem(FakeSKUs.FACTO, 2)!!)
        }
        val receipt = consoleRecipe(cart)
        // Act
        receipt.print()
        // Assert
    }
}