package com.example.dmerjimirror.library.model.response

class NewsFeed(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    val showdescription: Boolean,
    val list: ArrayList<Feed>
) : Component(id, name, position, active) {
    constructor() : this(0, "", "", false, true, arrayListOf())
}
