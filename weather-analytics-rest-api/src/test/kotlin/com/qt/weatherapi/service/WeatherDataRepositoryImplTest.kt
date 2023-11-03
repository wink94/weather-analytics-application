package com.qt.weatherapi.service

import com.qt.weatherapi.dto.QueryOutput
import com.qt.weatherapi.repository.WeatherDataRepositoryImpl
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class WeatherDataRepositoryImplTest {

    @Mock
    private lateinit var entityManager: EntityManager

    @Mock
    private lateinit var query: TypedQuery<Array<Any>>

    @InjectMocks
    private lateinit var weatherDataRepositoryImpl: WeatherDataRepositoryImpl

    private val startDate: LocalDateTime = LocalDateTime.now().minusDays(1)
    private val endDate: LocalDateTime = LocalDateTime.now()

    @BeforeEach
    fun setUp() {
        `when`(entityManager.createQuery(anyString(), eq(Array<Any>::class.java))).thenReturn(query)
        `when`(query.setParameter(anyString(), any())).thenReturn(query)
    }

    @Test
    fun `getSummaryDataCountByDatePeriod returns correct data`() {
        // Given
        val expectedResults = listOf(
                QueryOutput("Sunny", 50),
                QueryOutput("Rainy", 30)
        )
        `when`(query.resultList).thenReturn(listOf(arrayOf("Sunny", 50L), arrayOf("Rainy", 30L)))

        // When
        val results = weatherDataRepositoryImpl.getSummaryDataCountByDatePeriod(startDate, endDate)

        // Then
        assertEquals(expectedResults, results)
    }

    @Test
    fun `getPrecipTypeDataCountByDatePeriod returns correct data`() {
        // Given
        val expectedResults = listOf(
                QueryOutput("Rain", 40),
                QueryOutput("Snow", 20)
        )
        `when`(query.resultList).thenReturn(listOf(arrayOf("Rain", 40L), arrayOf("Snow", 20L)))

        // When
        val results = weatherDataRepositoryImpl.getPrecipTypeDataCountByDatePeriod(startDate, endDate)

        // Then
        assertEquals(expectedResults, results)
    }

    // Helper function to match any string
    private fun anyString(): String {
        return any()
    }

    // Helper function to match any class type
    private fun <T> any(): T {
        return Mockito.any<T>()
    }
}