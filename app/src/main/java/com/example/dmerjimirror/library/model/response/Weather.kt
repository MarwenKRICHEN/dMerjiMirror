package com.example.dmerjimirror.library.model.response

open class Weather(id: Int, name: String, position: String, active: Boolean, val location: String): Component(id, name, position, active) {
    constructor() : this(0, "Weather", Position.TOP_CENTER, false, "")
}
