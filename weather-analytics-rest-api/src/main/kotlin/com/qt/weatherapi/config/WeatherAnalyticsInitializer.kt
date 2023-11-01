package com.qt.weatherapi.config

import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.exception.WeatherDataException
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

@Configuration
class WeatherAnalyticsInitializer {

    @Value("\${DEBUG:false}")
    private var debug: Boolean = false

    companion object {
        private val entityManagerPackages = arrayOf("com.qt.weatherapi.dao", "com.qt.weatherapi.model")
        private val LOGGER = LoggerFactory.getLogger(WeatherAnalyticsInitializer::class.java)
        private const val MAXIMUM_POOL_SIZE = 20
        private const val MINIMUM_POOL_SIZE = 10
        private val MAX_LIFE_TIME = TimeUnit.MINUTES.toMillis(30)
        private val MINIMUM_IDLE_TIME = TimeUnit.MINUTES.toMillis(5)
    }

    fun getDataSource(host: String, database: String, user: String, password: String, databasePort: Int, readOnly: Boolean): DataSource {
        return try {
            val url = "jdbc:mysql://$host:$databasePort/$database?useSSL=false"
            val hikariDataSource = DataSourceBuilder
                    .create()
                    .type(HikariDataSource::class.java)
                    .username(user)
                    .password(password)
                    .url(url)
                    .build()

            hikariDataSource.maxLifetime = MAX_LIFE_TIME
            hikariDataSource.maximumPoolSize = MAXIMUM_POOL_SIZE
            hikariDataSource.minimumIdle = MINIMUM_POOL_SIZE
            hikariDataSource.idleTimeout = MINIMUM_IDLE_TIME
            hikariDataSource.isReadOnly = readOnly

            LOGGER.info("Successfully created datasource with url {}", url)
            if (debug) {
                ProxyDataSourceBuilder.create(hikariDataSource).name("Debug-Logger").asJson().countQuery().logQueryToSysOut().build()
            } else {
                if (!isDBConnectionValid(hikariDataSource)) {
                    LOGGER.error("error-code:{} error: Connection to the database failed", ExceptionEnum.DATABASE_CONNECTION_FAILURE.errorCode)
                    throw WeatherDataException(ExceptionEnum.DATABASE_CONNECTION_FAILURE)
                }
                hikariDataSource
            }
        } catch (e: Exception) {
            LOGGER.error("error-code:{} error: Connection to the Database failed", ExceptionEnum.DATABASE_CONNECTION_FAILURE.errorCode, e)
            throw WeatherDataException(ExceptionEnum.DATABASE_CONNECTION_FAILURE, e)
        }
    }
    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = getDataSource("localhost","weather_analytics_data_db","root","root",3306,false) // Define your data source here
        em.setPackagesToScan("com.qt.weatherapi.model") // Base package to scan for entities
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }

    @Bean
    fun transactionManager(emf: EntityManagerFactory): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = emf
        return transactionManager
    }

    private fun isDBConnectionValid(hikariDataSource: HikariDataSource): Boolean {
        return try {
            hikariDataSource.connection.use { connection ->
                connection.isValid(0)
            }
        } catch (e: Exception) {
            false
        }
    }
}