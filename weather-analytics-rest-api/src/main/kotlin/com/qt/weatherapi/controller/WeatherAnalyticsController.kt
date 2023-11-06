package com.qt.weatherapi.controller

import com.qt.weatherapi.dto.WeatherDataRequest
import com.qt.weatherapi.dto.WebResponse
import com.qt.weatherapi.service.WeatherDataService
import com.qt.weatherapi.util.toWebResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class WeatherAnalyticsController(private val weatherDataService: WeatherDataService) {
    @GetMapping(
        value = ["v1/weather/kpi"],
        produces = ["application/json"]
    )
    fun getWeatherHistory(@Valid @ModelAttribute request: WeatherDataRequest): ResponseEntity<WebResponse> {
        val data = weatherDataService.getKPIDataCountByTimeStampDatePeriod(
            kpi=request.eventType,
            timeDefinition = request.timestamp,
            startDate = request.startDate,
            endDate = request.endDate
        )
        return ResponseEntity.ok(data.toWebResponse(request.eventType))
    }
}