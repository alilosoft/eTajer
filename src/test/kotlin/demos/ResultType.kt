package demos

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ResultType {
    fun parseNumber(number: String) = number.toInt()

    //fun parseNumberResult(s: String) = runCatching { s.toInt() }
    val parseCatching: (String) -> Result<Int> = { n -> runCatching { n.toInt() } }
    val parseCatchingFn: (String) -> Result<Int> = fun(n) = runCatching { n.toInt() }

    @Test
    fun `parseNumber() throws an Exception`() {
        assertThrows<NumberFormatException> { parseNumber("") }
    }

    @Test
    fun `parseCatching() returns Result`() {
        Assertions.assertTrue(parseCatching("").isFailure)
    }

    @Test
    fun `fail fast without Result`() {
        val numbers = mutableListOf<Int>()
        assertThrows<java.lang.NumberFormatException> {
            "1,2, ,4,5, , 0"
                .split(",")
                .map { n -> numbers.add(parseNumber(n)) }
        }
        Assertions.assertEquals(2, numbers.size)
    }

    @Test
    fun `accumulate failures`() {
        val parsed = "1,2, ,4,5, ,0"
            .split(",")
            .map(parseCatching)

        val errors = parsed.filter { r -> r.isFailure }
        Assertions.assertEquals(7, parsed.size)
        Assertions.assertEquals(2, errors.size)
    }

    @Test
    fun `Result map()`() {
        val parsed = "1,2, ,0"
            .split(",")
            .map(parseCatching)

        var sum = 0;
        parsed.map { r -> r.map { n -> sum += n } }
        assertThrows<ArithmeticException> { parsed.map { r -> r.map { n -> 1 / n } } }
        Assertions.assertEquals(3, sum)
    }

    @Test
    fun `Result mapCatching()`() {
        val parsed = "1,2, ,4,5, ,0"
            .split(",")
            .map(parseCatching)
        parsed
            .map { r -> r.mapCatching { n -> 1.0 / n } }
            .map(::println)
    }
}