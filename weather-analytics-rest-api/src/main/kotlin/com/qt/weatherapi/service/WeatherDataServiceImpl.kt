package com.qt.weatherapi.service

import com.qt.weatherapi.enums.EventType
import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.enums.Timestamp
import com.qt.weatherapi.exception.WeatherDataException
import com.qt.weatherapi.repository.WeatherDataRepository
import com.qt.weatherapi.util.DateUtil
import com.qt.weatherapi.util.Helper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class WeatherDataServiceImpl(val weatherDataRepository: WeatherDataRepository) : WeatherDataService {

    companion object {
        private val logger = LoggerFactory.getLogger(WeatherDataRepository::class.java)
    }

    override fun getKPIDataCountByTimeStampDatePeriod(
        kpi: String,
        timeDefinition: String,
        startDate: String,
        endDate: String
    ): Map<String, Any> {

        return try {

            val startDateParsed = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val endDateParsed = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            val (newStartDate, newEndDate) = when (timeDefinition) {
                Timestamp.CUSTOM.toString() -> Pair(startDateParsed, endDateParsed)
                else -> DateUtil.getDateRangeForTimestamp(timeDefinition)
            }

            val data = when (kpi) {
                EventType.SUMMARY.toString() ->
                    Helper.convertToMapList(
                        weatherDataRepository.getSummaryDataCountByDatePeriod(
                            newStartDate,
                            newEndDate
                        )
                    )

                EventType.PRECIP_TYPE.toString() ->
                    Helper.convertToMapList(
                        weatherDataRepository.getPrecipTypeDataCountByDatePeriod(
                            newStartDate,
                            newEndDate
                        )
                    )

                else -> emptyList<Any>()
            }

            return mapOf("type" to kpi,"data" to data,"unit" to "%")
        } catch (exception: Exception) {
            logger.error("Data Fetch Failure {} error {}", ExceptionEnum.KPI_DATA_FETCH_FAILURE,exception.toString())
            throw WeatherDataException(ExceptionEnum.KPI_DATA_FETCH_FAILURE)
        }
    }
}