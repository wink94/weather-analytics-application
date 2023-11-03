package com.qt.weatherapi.model

import com.qt.weatherapi.enums.PrecipType
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "weather_data")
data class WeatherData(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "weather_data_id")
        val weatherDataId: Int,

        @Column(name = "observed_at")
        val observedAt: LocalDateTime,

        @Column(name = "summary")
        val summary: String?,

        @Enumerated(EnumType.STRING)
        @Column(name = "precip_type")
        val precipType: PrecipType?,

        @Column(name = "temperature_celsius")
        val temperatureCelsius: Int,

        @Column(name = "apparent_temperature_celsius")
        val apparentTemperatureCelsius: Int,

        @Column(name = "humidity")
        val humidity: Int,

        @Column(name = "wind_speed_km_h")
        val windSpeedKmH: Int,

        @Column(name = "wind_bearing_degrees")
        val windBearingDegrees: Int,

        @Column(name = "visibility_km")
        val visibilityKm: Int,

        @Column(name = "cloud_cover")
        val cloudCover: Boolean,

        @Column(name = "pressure_millibar")
        val pressureMillibar: Int,

        @Column(name = "daily_summary")
        val dailySummary: String?,

        @ManyToOne
        @JoinColumn(name = "weather_summary_id")
        val weatherSummary: WeatherSummary
)