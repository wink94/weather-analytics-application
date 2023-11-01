package com.qt.weatherapi.exception

import com.qt.weatherapi.controller.ErrorController
import com.qt.weatherapi.dto.WebResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): WebResponse<String> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        logger.error(ex.toString())
        return WebResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            status = "BAD_REQUEST",
            data = "Bad Request"
        )
    }
}
