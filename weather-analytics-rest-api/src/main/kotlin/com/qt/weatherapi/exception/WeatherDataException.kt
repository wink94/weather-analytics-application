package com.qt.weatherapi.exception

import com.qt.weatherapi.enums.ExceptionEnum

class WeatherDataException: RuntimeException {

    val exceptionEnum: ExceptionEnum

    constructor(exceptionEnum: ExceptionEnum, cause: Throwable) : super(exceptionEnum.error, cause) {
        this.exceptionEnum = exceptionEnum
    }

    constructor(exceptionEnum: ExceptionEnum) : super(exceptionEnum.error) {
        this.exceptionEnum = exceptionEnum
    }
}

