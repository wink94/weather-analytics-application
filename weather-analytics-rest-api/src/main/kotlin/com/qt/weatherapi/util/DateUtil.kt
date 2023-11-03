package com.qt.weatherapi.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object DateUtil {

    fun getDateRangeForTimestamp(timestamp: String,startDate:String?,endDate:String?): Pair<LocalDateTime, LocalDateTime> {
        return when (timestamp) {
            "DAY" -> {
                val startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)
                val endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
                Pair(startOfDay, endOfDay)
            }

            "MONTH" -> {
                val startOfMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT)
                val endOfMonth =
                    LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX)
                Pair(startOfMonth, endOfMonth)
            }

            "YEAR" -> {
                val startOfYear = LocalDateTime.of(LocalDate.now().withDayOfYear(1), LocalTime.MIDNIGHT)
                val endOfYear = LocalDateTime.of(
                    LocalDate.now().withMonth(12).with(TemporalAdjusters.lastDayOfMonth()),
                    LocalTime.MAX
                )
                Pair(startOfYear, endOfYear)
            }
            "CUSTOM" -> {
                val startDateParsed = LocalDateTime.parse(startDate!!, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val endDateParsed = LocalDateTime.parse(endDate!!, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                Pair(startDateParsed, endDateParsed)
            }
            else -> throw IllegalArgumentException("Invalid timestamp value: $timestamp")
        }
    }
}
