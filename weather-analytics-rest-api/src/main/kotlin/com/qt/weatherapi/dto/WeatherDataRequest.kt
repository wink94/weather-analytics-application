package com.qt.weatherapi.dto


import com.qt.weatherapi.Validation.ValidCustomTimestamp
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
@ValidCustomTimestamp
data class WeatherDataRequest(
    @field:NotNull
    @field:Pattern(regexp = "SUMMARY|PRECIP_TYPE")
    val eventType: String,

    @field:NotNull
    @field:Pattern(regexp = "CUSTOM|DAY|MONTH|YEAR")
    val timestamp: String,

    val startDate: String?,

    val endDate: String?
)
