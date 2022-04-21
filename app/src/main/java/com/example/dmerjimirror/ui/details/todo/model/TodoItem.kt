package com.example.dmerjimirror.ui.details.todo.model

import com.example.dmerjimirror.library.model.response.TodoElement

class TodoItem(val todo: TodoElement): Items {

    override fun listItemType(): Int {
        return Items.TODO
    }
}