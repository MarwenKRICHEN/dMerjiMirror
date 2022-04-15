package com.example.dmerjimirror.library.model

class NewsFeed(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    val showDescription: Boolean
) : Component(id, name, position, active) {
    constructor(): this(0, "", "", false, true)
}
