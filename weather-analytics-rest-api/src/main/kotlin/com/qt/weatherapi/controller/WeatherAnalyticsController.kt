package com.qt.weatherapi.controller

import com.qt.weatherapi.dto.WeatherDataRequest
import com.qt.weatherapi.dto.WebResponse
import com.qt.weatherapi.service.WeatherDataService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class WeatherAnalyticsController(val weatherDataService: WeatherDataService) {
    @GetMapping(
        value = ["v1/weather/kpi"],
        produces = ["application/json"]
    )
    fun getWeatherHistory(@Valid @ModelAttribute request: WeatherDataRequest): WebResponse<Map<String,Any>> {
        val data = weatherDataService.getKPIDataCountByTimeStampDatePeriod(
            request.eventType,
            request.timestamp,
            request.startDate,
            request.endDate
        )
        println(data)
        return WebResponse(
            code = 200,
            status = "Ok",
            data = data
        )
    }
}