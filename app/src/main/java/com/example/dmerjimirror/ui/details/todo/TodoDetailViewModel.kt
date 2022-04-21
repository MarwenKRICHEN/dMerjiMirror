package com.example.dmerjimirror.ui.details.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import java.util.*
import kotlin.collections.ArrayList

class TodoDetailViewModel: ViewModel() {

    private val myTodo = Todo(0, "Todo List", Component.Position.TOP_CENTER, true, Todo.Periodicity.MONTHLY)

    private val myTodoList = arrayListOf(
        TodoElement(0, "Pet the dog", Date(), false, 12),
        TodoElement(0, "Pet the dog1", Date(), true, 12),
        TodoElement(0, "Pet the dog2", Date(), true, 12),
        TodoElement(0, "Pet the dog3", Date(), false, 12),
    )

    private val _component = MutableLiveData<Todo>().apply {
        this.value = myTodo
    }

    val component: LiveData<Todo> = _component

    private val _todoList = MutableLiveData<ArrayList<TodoElement>>().apply {
        this.value = myTodoList
    }

    val todoList: LiveData<ArrayList<TodoElement>> = _todoList


}