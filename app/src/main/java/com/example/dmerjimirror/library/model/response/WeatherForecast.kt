package com.example.dmerjimirror.library.model.response

class WeatherForecast(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    location: String,
    val numberofdays: Int,
    val colored: Boolean
) : Weather(id, name, position, active, location)
