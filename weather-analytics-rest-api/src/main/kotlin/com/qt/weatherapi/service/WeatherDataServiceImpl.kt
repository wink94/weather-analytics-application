package com.qt.weatherapi.service

import com.qt.weatherapi.dto.QueryOutput
import com.qt.weatherapi.enums.EventType
import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.exception.WeatherDataException
import com.qt.weatherapi.repository.WeatherDataRepository
import com.qt.weatherapi.util.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WeatherDataServiceImpl(val weatherDataRepository: WeatherDataRepository) : WeatherDataService {


    private val logger = LoggerFactory.getLogger(WeatherDataRepository::class.java)


    override fun getKPIDataCountByTimeStampDatePeriod(
            kpi: String,
            timeDefinition: String,
            startDate: String?,
            endDate: String?
    ): List<QueryOutput> {

        try {

            val (newStartDate, newEndDate) = DateUtil.getDateRangeForTimestamp(timeDefinition, startDate, endDate)

            return when (kpi) {
                EventType.SUMMARY.toString() ->

                    weatherDataRepository.getSummaryDataCountByDatePeriod(
                            newStartDate,
                            newEndDate
                    )


                EventType.PRECIP_TYPE.toString() ->

                    weatherDataRepository.getPrecipTypeDataCountByDatePeriod(
                            newStartDate,
                            newEndDate
                    )


                else -> emptyList()
            }


        } catch (exception: Exception) {
            logger.error("Data Fetch Failure {} error {}", ExceptionEnum.KPI_DATA_FETCH_FAILURE, exception.toString())
            throw WeatherDataException(ExceptionEnum.KPI_DATA_FETCH_FAILURE)
        }
    }
}