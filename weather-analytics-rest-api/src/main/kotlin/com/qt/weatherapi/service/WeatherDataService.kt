package com.qt.weatherapi.service

import com.qt.weatherapi.dto.QueryOutput

interface WeatherDataService {

    fun getKPIDataCountByTimeStampDatePeriod(
        kpi: String, timeDefinition: String, startDate: String?,
        endDate: String?
    ): List<QueryOutput>
}