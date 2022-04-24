package com.example.dmerjimirror.library.model.response

class WeatherForecast(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    userid: Int,
    location: String,
    val numberofdays: Int,
    val colored: Boolean
) : Weather(id, name, position, active, 0, location)
