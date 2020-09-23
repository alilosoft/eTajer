package etajer.cashier.jdbc

import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.using
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class DerbyDbTests {
    @Test
    fun `should be using HikariCP datasource`() {
        // Arrange
        // Act
        val ds = DerbyDb.dataSource
        // Assert
        assertTrue(ds is HikariDataSource)
    }

    @Test
    fun `using() should close the connection but not the datasource`() {
        // Arrange
        val ds = DerbyDb.dataSource
        // Act
        val conn = using(session(ds)) { s -> s.connection }
        // Assert
        assertTrue(conn.isClosed)
        assertFalse(ds.connection.isClosed)
    }
}