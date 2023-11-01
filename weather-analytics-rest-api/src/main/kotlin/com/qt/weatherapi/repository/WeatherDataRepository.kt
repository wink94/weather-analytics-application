package com.qt.weatherapi.repository

import java.time.LocalDateTime

interface WeatherDataRepository {

    fun getSummaryDataCountByDatePeriod(startDate: LocalDateTime, endDate: LocalDateTime): List<Pair<String, Long>>

    fun getPrecipTypeDataCountByDatePeriod(startDate: LocalDateTime, endDate: LocalDateTime): List<Pair<String, Long>>
}