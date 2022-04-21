package com.example.dmerjimirror.listener

import com.example.dmerjimirror.library.model.response.TodoElement
import java.io.Serializable

interface TodoElementListener: Serializable {
    fun addTodo(todoElement: TodoElement)
    fun showUpdateTodo(todoElement: TodoElement, position: Int)
    fun updateTodo(todoElement: TodoElement, position: Int)
}