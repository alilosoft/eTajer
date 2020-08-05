package etajer.cashier.jdbc

import com.vladsch.kotlin.jdbc.HikariCP
import javax.sql.DataSource

interface DB {
    val dataSource: DataSource
}

object DerbyDb : DB {
    // TODO: load config from file
    private const val url = "jdbc:derby://localhost:1527/dine2020"
    override val dataSource : DataSource = HikariCP.default(url, "alilo", "alilo")
}