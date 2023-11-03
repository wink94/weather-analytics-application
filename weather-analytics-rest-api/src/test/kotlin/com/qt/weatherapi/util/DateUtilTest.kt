package com.qt.weatherapi.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

class DateUtilTest {

    @Test
    fun `test getDateRangeForTimestamp for DAY`() {
        val expected = Pair(
            LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT),
            LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        )

        val result = DateUtil.getDateRangeForTimestamp("DAY",null,null)

        assertEquals(expected, result)
    }

    @Test
    fun `test getDateRangeForTimestamp for MONTH`() {
        val expected = Pair(
            LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT),
            LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX)
        )

        val result = DateUtil.getDateRangeForTimestamp("MONTH",null,null)

        assertEquals(expected, result)
    }

    @Test
    fun `test getDateRangeForTimestamp for YEAR`() {
        val expected = Pair(
            LocalDateTime.of(LocalDate.now().withDayOfYear(1), LocalTime.MIDNIGHT),
            LocalDateTime.of(
                LocalDate.now().withMonth(12).with(TemporalAdjusters.lastDayOfMonth()),
                LocalTime.MAX
            )
        )

        val result = DateUtil.getDateRangeForTimestamp("YEAR",null,null)

        assertEquals(expected, result)
    }

    @Test
    fun `test getDateRangeForTimestamp with invalid timestamp`() {
        assertThrows(IllegalArgumentException::class.java) {
            DateUtil.getDateRangeForTimestamp("INVALID",null,null)
        }
    }

}