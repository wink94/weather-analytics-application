package com.qt.weatherapi.service

import java.time.LocalDateTime

interface WeatherDataService {

    fun getKPIDataCountByTimeStampDatePeriod(
        kpi: String, timeDefinition: String, startDate: String,
        endDate: String
    ): Map<String, Any>
}