package com.qt.weatherapi.config

import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.exception.WeatherDataException
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Value
import java.sql.Connection

@ExtendWith(MockitoExtension::class)
class WeatherAnalyticsInitializerTest {

    @InjectMocks
    lateinit var weatherAnalyticsInitializer: WeatherAnalyticsInitializer

    @Mock
    lateinit var hikariDataSource: HikariDataSource

    @Mock
    lateinit var connection: Connection

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

//    @Test
//    fun testGetDataSourceWithValidConnection() {
//
//        val dataSource = weatherAnalyticsInitializer.getDataSource(
//                host,
//            database,
//            username,
//            password,
//                port.toInt(),
//            false
//        )
//        assertTrue(dataSource is HikariDataSource)
//
//    }

    @Test
    fun testGetDataSourceWithInValidConnection() {

        assertThrows(WeatherDataException::class.java) {
            weatherAnalyticsInitializer.getDataSource("localhost11", "weather_analytics_data_db", "root", "root", 3306, false)
        }
    }

}