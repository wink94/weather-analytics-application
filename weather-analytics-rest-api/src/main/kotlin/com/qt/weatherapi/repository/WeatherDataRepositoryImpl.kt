package com.qt.weatherapi.repository

import com.qt.weatherapi.dto.QueryOutput
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
@Repository
class WeatherDataRepositoryImpl:WeatherDataRepository {

    @PersistenceContext
    @Qualifier("WeatherAnalyticsInitializer")
    lateinit var entityManager: EntityManager
    override fun getSummaryDataCountByDatePeriod(startDate: LocalDateTime, endDate: LocalDateTime): List<QueryOutput> {
        val query = """
            SELECT ws.summary, COUNT(wd.weatherDataId) as weather_count
            FROM WeatherData wd
            JOIN wd.weatherSummary ws
            WHERE wd.observedAt BETWEEN :startDate AND :endDate
            GROUP BY ws.summary
            ORDER BY weather_count DESC
        """

        return entityManager.createQuery(query, Array<Any>::class.java)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .resultList
                .map { QueryOutput(it[0] as String,it[1] as Long) }
    }

    override fun getPrecipTypeDataCountByDatePeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<QueryOutput> {
        val query = """
            SELECT ws.precipType, COUNT(wd.weatherDataId) as weather_count
            FROM WeatherData wd
            JOIN wd.weatherSummary ws
            WHERE wd.observedAt BETWEEN :startDate AND :endDate
            GROUP BY ws.precipType
            ORDER BY weather_count DESC
        """

        return entityManager.createQuery(query, Array<Any>::class.java)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .resultList
                .map { QueryOutput(it[0] as String,it[1] as Long) }
    }

}