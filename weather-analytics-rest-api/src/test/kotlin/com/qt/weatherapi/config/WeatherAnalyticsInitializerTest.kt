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
import java.sql.Connection

@ExtendWith(MockitoExtension::class)
class WeatherAnalyticsInitializerTest {

    @InjectMocks
    lateinit var weatherAnalyticsInitializer: WeatherAnalyticsInitializer

    @Mock
    lateinit var hikariDataSource: HikariDataSource

    @Mock
    lateinit var connection: Connection

    @BeforeEach
    fun setUp() {
        Mockito.`when`(hikariDataSource.connection).thenReturn(connection)
    }

    @Test
    fun `test getDataSource with valid connection`() {
        Mockito.`when`(connection.isValid(0)).thenReturn(true)

        val dataSource = weatherAnalyticsInitializer.getDataSource("localhost", "weather_analytics_data_db", "root", "root", 3306, false)
        assertTrue(dataSource is HikariDataSource)
    }

    @Test
    fun `test getDataSource with invalid connection`() {
        Mockito.`when`(connection.isValid(0)).thenReturn(false)

        assertThrows(WeatherDataException::class.java) {
            weatherAnalyticsInitializer.getDataSource("localhost", "weather_analytics_data_db", "root", "root", 3306, false)
        }
    }

}