package com.example.dmerjimirror.utils

import com.example.dmerjimirror.library.model.Component

class ComponentUtils {
    companion object {
        fun getPositionStringFromIndex(index: Int): String {
            return when (index) {
                0 -> Component.Position.TOP_LEFT
                1 -> Component.Position.TOP_CENTER
                2 -> Component.Position.TOP_RIGHT
                3 -> Component.Position.MIDDLE_LEFT
                4 -> Component.Position.MIDDLE_CENTER
                5 -> Component.Position.MIDDLE_RIGHT

                else -> Component.Position.TOP_LEFT
            }
        }
    }
}