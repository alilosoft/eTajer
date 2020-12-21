package demos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * FP Decorator pattern
 * https://www.youtube.com/watch?v=FsAPt_9Bf3U
 */
typealias GreetFn = () -> String

// GreetFn decorator
fun greetName(name: String, origin: GreetFn): GreetFn {
    val decoratedGreet = origin()
    // return a function of type GreetFn
    return { "$decoratedGreet $name" }
}

// print greeting decorator
fun printGreeting(origin: GreetFn): GreetFn {
    val greeting = origin()
    // the print statement should be called in the lambda for lazy evaluation
    // println("printGreeting() decorator says: $greeting")
    return {
        println("printGreeting() decorator says: $greeting")
        greeting
    }
}

class FuncDecoratorDemo {
    /**
     * OOP Decorator pattern
     */
    interface Greeter {
        fun greet(): String
    }

    // Greeter decorator
    class NameGreeter(private val name: String, private val origin: Greeter) : Greeter {
        override fun greet(): String {
            val decoratedGreet = origin.greet()
            return "$decoratedGreet $name"
        }
    }

    // Greeter decorator
    class GreetPrinter(private val origin: Greeter) : Greeter {
        override fun greet(): String {
            val greet = origin.greet()
            println("GreetPrinter decorator says: $greet")
            return greet
        }
    }

    @Test
    fun `default impl of Greeter`() {
        // Arrange
        val hi: Greeter = object : Greeter {
            override fun greet() = "Hi"
        }
        // Act
        val greet = hi.greet()
        // Assert
        assertEquals("Hi", greet)
    }

    @Test
    fun `greet a name with Greeter decorator`() {
        // Arrange
        // original Greeter
        val hello: Greeter = object : Greeter {
            override fun greet() = "Hello"
        }
        // Decorated Greeter
        val helloAlilo: Greeter = NameGreeter("al!lo", hello)
        // Act
        val greet = helloAlilo.greet()
        // Assert
        assertEquals("Hello al!lo", greet)
    }

    @Test
    fun `print a greeting with Greeter decorator`() {
        // Arrange
        val hello: Greeter = object : Greeter {
            override fun greet() = "Hello"
        }
        val helloAlilo: Greeter = NameGreeter("al!lo", hello)
        val printHello: Greeter = GreetPrinter(hello)
        val printHelloName: Greeter = GreetPrinter(helloAlilo)
        // Act
        val helloGreeting = printHello.greet()
        val helloNameGreeting = printHelloName.greet()
        // Assert
        assertEquals("Hello", helloGreeting)
        assertEquals("Hello al!lo", helloNameGreeting)
    }

    @Test
    fun `default GreetFn impl`() {
        // Arrange
        val hi: GreetFn = { "Hi" }
        // Act
        val greet = hi()
        // Assert
        assertEquals("Hi", greet)
    }

    @Test
    fun `greet a name with GreetFn decorator`() {
        // Arrange
        val sayHello: GreetFn = { "Hello" }
        // decorated GreetFn
        val sayHelloAlilo: GreetFn = greetName("al!lo", sayHello)
        // Act
        val greet = sayHelloAlilo()
        // Assert
        assertEquals("Hello al!lo", greet)
    }

    @Test
    fun `print greeting with GreetFn decorator`() {
        // Arrange
        val helloFn: GreetFn = { "Hello" }
        // decorated GreetFn
        val helloAliloFn: GreetFn = greetName("al!lo", helloFn)
        // Act
        val printHello = printGreeting(helloFn) // this call should not execute the print statement
        val printHelloAlilo = printGreeting(helloAliloFn)
        // Assert
        assertEquals("Hello", printHello())
        assertEquals("Hello al!lo", printHelloAlilo())
    }
}