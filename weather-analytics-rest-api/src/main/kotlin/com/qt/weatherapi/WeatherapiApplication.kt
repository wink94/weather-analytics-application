package com.qt.weatherapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class WeatherapiApplication

fun main(args: Array<String>) {
	runApplication<WeatherapiApplication>(*args)
}
