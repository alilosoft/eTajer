package demos

import java.util.function.Consumer

// https://jorgecastillo.dev/kotlin-sam-conversions

// Since Kotlin 1.4+, we can declare a Kotlin functional interface,
//(notice the fun keyword before interface declaration)
fun interface Producer<A> {
    fun produce(): A
}

// Kotlin SAM impl with lambda. Was not possible before 1.4
val helloGen = Producer { "Hello" }

// Kotlin SAM impl with anonymous objects.
val kotlinGen = object : Producer<String> {
    override fun produce(): String = "Kotlin 1.4"
}

fun log(msgProducer: Producer<String>, logger: Consumer<String>) = logger.accept(msgProducer.produce())

fun main() {
    log(
            msgProducer = helloGen,
            // Java SAM impl
            logger = object : Consumer<String> {
                override fun accept(msg: String) {
                    print(msg)
                }
            })
    log(
            msgProducer = kotlinGen,
            // SAM Type ↓ was mandatory before 1.4
            logger = Consumer { msg -> println(" $msg") })

    // passing lambdas as SAM impl without explicit type
    log({ "Kotlin is Awesome" }) { msg -> println(msg) }

}