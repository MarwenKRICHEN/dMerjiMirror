package com.example.dmerjimirror.ui.details.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.Todo
import com.example.dmerjimirror.library.model.TodoElement
import java.util.*
import kotlin.collections.ArrayList

class TodoDetailViewModel: ViewModel() {

    private val myTodo = Todo(0, "Todo List", Component.Position.TOP_CENTER, true, Todo.Periodicity.DAILY)

    private val myTodoList = arrayListOf(
        TodoElement(0, "Pet the dog", Date(), false),
        TodoElement(0, "Pet the dog1", Date(), true),
        TodoElement(0, "Pet the dog2", Date(), true),
        TodoElement(0, "Pet the dog3", Date(), false),
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