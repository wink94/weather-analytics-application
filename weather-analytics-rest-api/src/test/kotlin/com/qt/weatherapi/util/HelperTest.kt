package com.qt.weatherapi.util

import com.qt.weatherapi.util.Helper.convertToMapList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelperTest {

    @Test
    fun `test convertToMapList with valid data`() {
        val input = listOf(
            Pair("Partly Cloudy", 33L),
            Pair("Mostly Cloudy", 29L),
            Pair("Clear", 26L),
            Pair("Rainy", 21L),
            Pair("Overcast", 10L),
            Pair("Snowy", 5L),
            Pair("Windy", 3L)
        )

        val expected = listOf(
            mapOf("name" to "Partly Cloudy", "value" to 25),
            mapOf("name" to "Mostly Cloudy", "value" to 22),
            mapOf("name" to "Clear", "value" to 20),
            mapOf("name" to "Rainy", "value" to 16),
            mapOf("name" to "Overcast", "value" to 7),
            mapOf("name" to "Other (8)", "value" to 6)
        )

        val result = convertToMapList(input) // Assuming `convertToMapList` is a top-level function. Adjust if it's a member function.

        assertEquals(expected, result)
    }

    @Test
    fun `test convertToMapList with empty data`() {
        val input = emptyList<Pair<String, Long>>()

        val expected = listOf(
            mapOf("name" to "Other (0)", "value" to 0)
        )

        val result = convertToMapList(input)

        assertEquals(expected, result)
    }

    @Test
    fun `test convertToMapList with zero totalCount`() {
        val input = listOf(
            Pair("Partly Cloudy", 0L),
            Pair("Mostly Cloudy", 0L),
            Pair("Clear", 0L),
            Pair("Rainy", 0L),
            Pair("Overcast", 0L)
        )

        val expected = listOf(
            mapOf("name" to "Partly Cloudy", "value" to 0),
            mapOf("name" to "Mostly Cloudy", "value" to 0),
            mapOf("name" to "Clear", "value" to 0),
            mapOf("name" to "Rainy", "value" to 0),
            mapOf("name" to "Overcast", "value" to 0),
            mapOf("name" to "Other (0)", "value" to 0)
        )

        val result = convertToMapList(input)

        assertEquals(expected, result)
    }
}