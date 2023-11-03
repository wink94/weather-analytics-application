package com.qt.weatherapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.qt.weatherapi.dto.QueryOutput
import com.qt.weatherapi.dto.WeatherDataRequest
import com.qt.weatherapi.service.WeatherDataService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
@WebMvcTest(WeatherAnalyticsController::class)
class WeatherAnalyticsControllerTest {

    @MockBean
    private lateinit var weatherDataService: WeatherDataService

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @Test
    fun `getWeatherHistory returns data successfully`() {
        // Given
        val eventType = "SUMMARY"
        val timestamp = "CUSTOM"
        val startDate = LocalDateTime.now().minusDays(1).toString()
        val endDate = LocalDateTime.now().toString()
        val weatherDataRequest = WeatherDataRequest(eventType, timestamp, startDate, endDate)
        val webResponse = listOf<QueryOutput>()

        `when`(weatherDataService.getKPIDataCountByTimeStampDatePeriod(eventType, timestamp, startDate, endDate))
                .thenReturn(webResponse)

        // When & Then
        mockMvc.perform(
                get("/v1/weather/kpi")
                        .param("eventType", eventType)
                        .param("timestamp", timestamp)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk)
    }
}