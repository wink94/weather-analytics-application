package com.qt.weatherapi.controller

import com.qt.weatherapi.dto.WebResponse
import com.qt.weatherapi.exception.NotFoundException
import com.qt.weatherapi.exception.UnauthorizedException
import com.qt.weatherapi.repository.WeatherDataRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {

    companion object {
        private val logger = LoggerFactory.getLogger(ErrorController::class.java)
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun notFound(notfoundException: NotFoundException): WebResponse<String> {
        logger.error("NOT FOUND")
        return WebResponse(
            code = 404,
            status = "NOT FOUND",
            data = "Not Found"
        )
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(unauthorizedException: UnauthorizedException): WebResponse<String> {
        logger.error("UNAUTHORIZED")
        return WebResponse(
            code = 401,
            status = "UNAUTHORIZED",
            data = "Please put your X-Api-Key"
        )
    }

}