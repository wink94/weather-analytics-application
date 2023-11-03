package com.qt.weatherapi.repository

import com.qt.weatherapi.dto.QueryOutput
import java.time.LocalDateTime

interface WeatherDataRepository {

    fun getSummaryDataCountByDatePeriod(startDate: LocalDateTime, endDate: LocalDateTime): List<QueryOutput>

    fun getPrecipTypeDataCountByDatePeriod(startDate: LocalDateTime, endDate: LocalDateTime): List<QueryOutput>
}