package com.example.dmerjimirror.library.model.response

class NewsFeed(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    userid: Int,
    val showdescription: Boolean,
    val list: ArrayList<Feed>
) : Component(id, name, position, active, userid) {
    constructor() : this(0, "", "", false, 0, true, arrayListOf())
}
