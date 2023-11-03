package com.qt.weatherapi.controller

import com.qt.weatherapi.exception.NotFoundException
import com.qt.weatherapi.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {


    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun notFound(notfoundException: NotFoundException): ResponseEntity.HeadersBuilder<*> {
        logger.error("NOT FOUND")
        return ResponseEntity.notFound()
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(unauthorizedException: UnauthorizedException): ResponseEntity<String> {
        logger.error("UNAUTHORIZED")
        return ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED)
    }

}