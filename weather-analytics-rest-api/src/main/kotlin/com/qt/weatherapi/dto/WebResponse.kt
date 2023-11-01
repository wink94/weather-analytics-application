package com.qt.weatherapi.dto

data class WebResponse<T>(

        val code: Int,

        val status: String,

        val data: T
)