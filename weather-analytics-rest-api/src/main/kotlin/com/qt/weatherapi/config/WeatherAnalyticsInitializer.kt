package com.qt.weatherapi.config

import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.exception.WeatherDataException
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

@Configuration
@ConfigurationProperties("application.properties")
class WeatherAnalyticsInitializer {

    @Value("\${DEBUG:false}")
    private var debug: Boolean = false

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String

    @Value("\${host}")
    private lateinit var host: String

    @Value("\${database}")
    private lateinit var database: String

    @Value("\${PORT}")
    private lateinit var port: String

    companion object {
        private val LOGGER = LoggerFactory.getLogger(WeatherAnalyticsInitializer::class.java)
        private const val MAXIMUM_POOL_SIZE = 5
        private const val MINIMUM_POOL_SIZE = 2
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
        em.dataSource = getDataSource(host,database,username,password,port.toInt(),false)
        em.setPackagesToScan("com.qt.weatherapi.model","com.qt.weatherapi.dao") // Base package to scan for entities
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