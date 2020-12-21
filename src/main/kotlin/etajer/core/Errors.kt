package etajer.core

fun interface FailMsg {
    fun msg(): String
}

data class ErrorInfo(val msg: String): FailMsg {
    override fun msg(): String = msg
}
