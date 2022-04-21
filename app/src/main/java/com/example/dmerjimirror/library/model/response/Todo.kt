package com.example.dmerjimirror.library.model.response

class Todo(
    id: Int,
    name: String,
    position: String,
    active: Boolean,
    var periodicity: Int,
    val list: ArrayList<TodoElement>? = null
) :
    Component(id, name, position, active) {

    constructor() : this(0, "", "", false, 0)

    class Periodicity {
        companion object {
            const val DAILY = 0
            const val WEAKLY = 1
            const val MONTHLY = 2
            const val ALL = 3
        }
    }
}
