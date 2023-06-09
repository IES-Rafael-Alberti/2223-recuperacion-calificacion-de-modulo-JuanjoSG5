package dataFactory

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DataFactory {
    enum class DataSourceType {
        Embedded,
        InMemory,
        Remote,
        URLonly
    }

    fun getDataSource(dataSourceType: DataSourceType): DataSource {
        return when (dataSourceType) {
            DataSourceType.Remote -> {
                hikariConfig()
            }
            DataSourceType.InMemory ->{
                hikariConfig()
            }
            DataSourceType.URLonly ->{
                hikariConfig()
            }

            DataSourceType.Embedded -> {
                hikariConfig()
            }
        }
    }

    private fun hikariConfig(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:h2:./default"
        config.driverClassName = "org.h2.Driver"
        config.maximumPoolSize = 10
        config.isAutoCommit = true
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        return HikariDataSource(config)
    }
}