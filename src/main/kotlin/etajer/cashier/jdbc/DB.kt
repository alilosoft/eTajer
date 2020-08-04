package etajer.cashier.jdbc

import com.vladsch.kotlin.jdbc.HikariCP

object DerbyDB {
    // TODO: load config from file
    private const val url = "jdbc:derby://localhost:1527/dine2020"
    val dataSource = HikariCP.default(url, "alilo", "alilo")
}