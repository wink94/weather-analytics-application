package com.qt.weatherapi.exception

import org.slf4j.LoggerFactory
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
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity.BodyBuilder {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        logger.error(ex.toString())
        logger.error(errors.toString())
        return ResponseEntity.badRequest( )
    }
}
