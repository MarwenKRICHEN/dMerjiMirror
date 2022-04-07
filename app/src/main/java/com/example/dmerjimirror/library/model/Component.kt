package com.example.dmerjimirror.library.model

open class Component(val id: Int, val name: String, val position: String, val active: Boolean) {
    constructor() : this(0, "", "", true)

    class Position {
        companion object {
            const val TOP_LEFT = "TOP_LEFT"
            const val TOP_CENTER = "TOP_CENTER"
            const val TOP_RIGHT = "TOP_RIGHT"
            const val MIDDLE_LEFT = "MIDDLE_LEFT"
            const val MIDDLE_CENTER = "MIDDLE_CENTER"
            const val MIDDLE_RIGHT = "MIDDLE_RIGHT"
            const val BOTTOM_BAR = "BOTTOM_BAR"
        }
    }
}