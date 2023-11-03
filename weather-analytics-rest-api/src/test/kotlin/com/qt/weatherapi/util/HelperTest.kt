package com.qt.weatherapi.util

import com.qt.weatherapi.dto.QueryOutput
import com.qt.weatherapi.dto.WebResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelperTest {

    @Test
    fun `toWebResponse should calculate correct percentages for top 5 and others`() {
        // Given
        val queryOutputs = listOf(
                QueryOutput("Sunny", 50),
                QueryOutput("Rainy", 30),
                QueryOutput("Cloudy", 20),
                QueryOutput("Windy", 10),
                QueryOutput("Snowy", 5),
                QueryOutput("Foggy", 3),
                QueryOutput("Stormy", 2)
        )
        val expectedWebResponse = WebResponse(
                type = "SUMMARY",
                data = listOf(
                        mapOf("name" to "Sunny", "value" to 41),
                        mapOf("name" to "Rainy", "value" to 25),
                        mapOf("name" to "Cloudy", "value" to 16),
                        mapOf("name" to "Windy", "value" to 8),
                        mapOf("name" to "Snowy", "value" to 4),
                        mapOf("name" to "Other (5)", "value" to 4) // Assuming the total count is 120
                )
        )

        // When
        val actualWebResponse = queryOutputs.toWebResponse("SUMMARY")

        // Then
        assertEquals(expectedWebResponse, actualWebResponse)
    }

    @Test
    fun `toWebResponse should handle empty list`() {
        // Given
        val queryOutputs = emptyList<QueryOutput>()
        val expectedWebResponse = WebResponse(type = "SUMMARY", data = listOf(
                mapOf("name" to "Other (0)", "value" to 0)
        ))

        // When
        val actualWebResponse = queryOutputs.toWebResponse("SUMMARY")

        // Then
        assertEquals(expectedWebResponse, actualWebResponse)
    }

    @Test
    fun `toWebResponse should handle list with less than 5 elements`() {
        // Given
        val queryOutputs = listOf(
                QueryOutput("Sunny", 70),
                QueryOutput("Rainy", 30)
        )
        val expectedWebResponse = WebResponse(
                type = "SUMMARY",
                data = listOf(
                        mapOf("name" to "Sunny", "value" to 70),
                        mapOf("name" to "Rainy", "value" to 30),
                        mapOf("name" to "Other (0)", "value" to 0)
                )
        )

        // When
        val actualWebResponse = queryOutputs.toWebResponse("SUMMARY")

        // Then
        assertEquals(expectedWebResponse, actualWebResponse)
    }
}