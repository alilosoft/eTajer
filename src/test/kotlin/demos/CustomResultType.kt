package demos

import org.junit.jupiter.api.Test

// https://discuss.kotlinlang.org/t/any-good-way-or-contract-to-handle-value-or-error-on-kotlin-result/12504
sealed class Either<out T, out R> {
    data class Success<out T>(val value: T) : Either<T, Nothing>()
    data class Failure<out R>(val error: R) : Either<Nothing, R>()

    fun <V> onSuccess(transform: (value: T) -> V): Either<V, R> {
        return when (this) {
            is Either.Success -> Either.Success(transform(value))
            is Either.Failure -> Either.Failure(error)
        }
    }

    fun <V> onFailure(handler: (error: R) -> V): Either<T, V> {
        return when (this) {
            is Either.Success -> Either.Success(value)
            is Either.Failure -> Either.Failure(handler(error))
        }
    }

}
typealias Success<T> = Either.Success<T>
typealias Failure<R> = Either.Failure<R>


class CustomResultType {

    fun parseInt(s: String): Either<Int, String> {
        return try {
            Success(s.trim().toInt())
        } catch (ex: NumberFormatException) {
            Failure("Can't parse '$s' as Int")
        }
    }

    @Test
    fun `new feature test`() {
        val r = parseInt("123")
        when (r) {
            is Success -> println(r.value + 1)
            is Failure -> println(r.error)
        }

    }

    @Test
    fun `list`() {
        "1,2,3, ,1 2,4,5"
            .split(",")
            .map(::parseInt)
            .map { r -> r.onSuccess { n -> println(n * 10); n * 10 } }
            .map { r -> r.onFailure { s -> println(s) } }
    }


}