package com.qt.weatherapi.Validation

import com.qt.weatherapi.dto.WeatherDataRequest
import com.qt.weatherapi.enums.ExceptionEnum
import com.qt.weatherapi.exception.WeatherDataException
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CustomDateValidator : ConstraintValidator<ValidCustomTimestamp, WeatherDataRequest> {
    override fun isValid(
            request: WeatherDataRequest?,
            context: ConstraintValidatorContext
    ): Boolean {
        if (request == null) {
            return true
        }

        return if (request.timestamp == "CUSTOM") {

            if (request.startDate == null || request.endDate == null)
                throw WeatherDataException(ExceptionEnum.VALIDATION_ERROR)
            else
                request.startDate.isNotBlank() && request.endDate.isNotBlank() && isValidTimestamp(request.startDate) && isValidTimestamp(request.endDate)
        } else {
            true
        }
    }

    private fun isValidTimestamp(timestamp: String): Boolean {

        return timestamp.matches(Regex("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]"))
    }
}