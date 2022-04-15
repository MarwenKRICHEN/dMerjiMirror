package com.example.dmerjimirror.ui.details.todo.model

interface Items {
    fun listItemType(): Int

    companion object {
        const val COMPONENT_HEADER = 0
        const val HEADER = 1
        const val TODO = 2
        const val FEEDS = 3
    }
}