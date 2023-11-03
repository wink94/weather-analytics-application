package com.qt.weatherapi.util

import com.qt.weatherapi.dto.QueryOutput
import com.qt.weatherapi.dto.WebResponse

fun List<QueryOutput>.toWebResponse(kpi:String): WebResponse {
    val totalCount = this.sumOf { it.count }

    val top5 = this.take(5).map { pair ->
        mapOf(
                "name" to pair.evenType,
                "value" to if (totalCount > 0) (pair.count * 100 / totalCount).toInt() else 0
        )
    }

    val otherCount = this.drop(5).sumOf { it.count }
    val otherPercentage = if (totalCount > 0) (otherCount * 100 / totalCount).toInt() else 0

    val otherEntry = mapOf(
            "name" to "Other ($otherCount)",
            "value" to otherPercentage
    )

    val finalList = top5 + otherEntry

    println(finalList)

    return WebResponse(type = kpi, data = finalList)
}