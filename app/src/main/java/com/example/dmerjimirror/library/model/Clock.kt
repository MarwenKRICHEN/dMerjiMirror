package com.example.dmerjimirror.library.model

class Clock(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    val timeZone: Int,
    val isDigital: Boolean
) : Component(id, name, position, active)