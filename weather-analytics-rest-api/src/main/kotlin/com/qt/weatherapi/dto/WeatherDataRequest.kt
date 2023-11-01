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
    @field:Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]")
    val startDate: String,

    @field:NotNull
    @field:Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]")
    val endDate: String
)
