package com.qt.weatherapi.model

import com.qt.weatherapi.enums.PrecipType
import jakarta.persistence.*

@Entity
@Table(name = "weather_summary")
data class WeatherSummary(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "weather_summary_id")
        val weatherSummaryId: Int,

        @Column(name = "summary")
        val summary: String?,


        @Column(name = "precip_type")
        val precipType: String?,

        @Column(name = "daily_summary")
        val dailySummary: String?
)
