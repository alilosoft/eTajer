package func_decorator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * FP Decorator pattern
 * https://www.youtube.com/watch?v=FsAPt_9Bf3U
 */
typealias DisplayInfoFn = (String) -> String

// DisplayInfoFn
val printName = fun(name: String): String {
    val info = "Name is: $name"
    println("printName(): $info")
    return info
}

// DisplayInfoFn decorator
fun printCapitalizedName(origin: DisplayInfoFn): DisplayInfoFn {
    // return a function of type DisplayInfoFn
    return { name ->
        val info = origin(name.capitalize())
        println("capitalized(): $info")
        info
    }
}

class FuncDecoratorDemo1 {

    @Test
    fun `new feature test`() {
        // Arrange
        val name = "al!lo fellahi"
        // Act
        val info = printName("al!lo fellahi")
        // Assert
        assertEquals("Name is: $name", info)
    }

    @Test
    fun `capitalized decorator`() {
        // Arrange
        val name = "al!lo fellahi"
        // Act
        val capitalizeNameFn = printCapitalizedName(printName)
        val info = capitalizeNameFn(name)
        // Assert
        assertEquals("Name is: ${name.capitalize()}", info)
    }

}