package com.qt.weatherapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("v1/weather/health")
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        val healthStatus = mapOf("status" to "UP")
        return ResponseEntity.ok(healthStatus)
    }
}