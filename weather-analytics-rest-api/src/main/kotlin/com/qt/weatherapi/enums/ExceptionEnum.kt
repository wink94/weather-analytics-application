package com.qt.weatherapi.enums

enum class ExceptionEnum(val error: String,val errorCode: Int) {

    DATABASE_CONNECTION_FAILURE("Database Connection Failure",123),
    KPI_DATA_FETCH_FAILURE("KPI Data Fetch Failure",124);
}