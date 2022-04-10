package com.example.dmerjimirror.library.model

class WeatherForecast(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    location: String,
    val numberOfDays: Int,
    val colored: Boolean
) : Weather(id, name, position, active, location)
