package com.qt.weatherapi.dto


import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class WeatherDataRequest(
    @field:NotNull
    @field:Pattern(regexp = "SUMMARY|PRECIP_TYPE")
    val eventType: String,

    @field:NotNull
    @field:Pattern(regexp = "CUSTOM|DAY|MONTH|YEAR")
    val timestamp: String,

    @field:NotNull
    val startDate: String,

    @field:NotNull
    val endDate: String
)
