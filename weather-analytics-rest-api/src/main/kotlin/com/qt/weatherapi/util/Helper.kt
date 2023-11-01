package com.qt.weatherapi.util

object Helper {

    fun convertToMapList(results: List<Pair<String, Long>>): List<Map<String, Any>> {

        val totalCount = results.sumOf { it.second }

        val top5 = results.take(5).map { pair ->
            mapOf(
                "name" to pair.first,
                "value" to if (totalCount > 0) (pair.second * 100 / totalCount).toInt() else 0
            )
        }

        val otherCount = results.drop(5).sumOf { it.second }
        val otherPercentage = if (totalCount > 0) (otherCount * 100 / totalCount).toInt() else 0

        val otherEntry = mapOf(
            "name" to "Other ($otherCount)",
            "value" to otherPercentage
        )

        val finalList = top5 + otherEntry

        println(finalList)

        return finalList
    }
}