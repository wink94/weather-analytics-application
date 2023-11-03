package com.qt.weatherapi.Validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CustomDateValidator::class])
annotation class ValidCustomTimestamp (
    val message: String = "Invalid date range for the given timestamp",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])
{}