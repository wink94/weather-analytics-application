package com.qt.weatherapi.dto

data class WebResponse(val type:String, val data: List<Map<String, Any>>, val unit:String = "%")
