package com.example.dmerjimirror.library.model

open class Component(val id: Int, val name: String, var position: String, var active: Boolean) {
    constructor() : this(0, "", "", true)

    fun getImageName(): String {
        var newName = name
        newName = name.replace(" ", "_")
        return newName.lowercase()
    }

    class Position {
        companion object {
            const val TOP_LEFT = "top_left"
            const val TOP_CENTER = "top_center"
            const val TOP_RIGHT = "top_right"
            const val MIDDLE_LEFT = "middle_left"
            const val MIDDLE_CENTER = "middle_center"
            const val MIDDLE_RIGHT = "middle_right"
        }
    }

    fun moveBackward() {
        this.position = getPositionStringFromIndex(getIndexFromPositionString(this.position) - 1)
    }

    fun moveForward() {
        this.position = getPositionStringFromIndex(getIndexFromPositionString(this.position) + 1)
    }

    companion object {

        fun move(components: ArrayList<Component>?, from: Int, to: Int) {
            if (components != null) {
                if (from > to) {
                    for (i in to until from) {
                        components[i].moveForward()
                    }
                    components[from].position = getPositionStringFromIndex(to)
                } else {
                    for (i in from + 1 .. to) {
                        components[i].moveBackward()
                    }
                    components[from].position = getPositionStringFromIndex(to)
                }
                components.sortBy { getIndexFromPositionString(it.position) }
            }

        }

        fun getPositionStringFromIndex(index: Int): String {
            return when (index) {
                0 -> Position.TOP_LEFT
                1 -> Position.TOP_CENTER
                2 -> Position.TOP_RIGHT
                3 -> Position.MIDDLE_LEFT
                4 -> Position.MIDDLE_CENTER
                5 -> Position.MIDDLE_RIGHT

                else -> Component.Position.TOP_LEFT
            }
        }

        fun getIndexFromPositionString(position: String): Int {
            return when (position.lowercase()) {
                Position.TOP_LEFT -> 0
                Position.TOP_CENTER -> 1
                Position.TOP_RIGHT -> 2
                Position.MIDDLE_LEFT -> 3
                Position.MIDDLE_CENTER -> 4
                Position.MIDDLE_RIGHT -> 5

                else -> 0
            }
        }

    }
}