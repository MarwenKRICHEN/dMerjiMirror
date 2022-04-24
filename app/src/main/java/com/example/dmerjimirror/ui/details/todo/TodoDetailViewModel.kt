package com.example.dmerjimirror.ui.details.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmerjimirror.library.controller.TodoListController
import com.example.dmerjimirror.library.controller.WeatherController
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import com.example.dmerjimirror.ui.details.DetailViewModel
import java.util.*
import kotlin.collections.ArrayList

class TodoDetailViewModel: DetailViewModel() {

    private val _component = MutableLiveData<Todo?>().apply {
        this.value = null
    }

    val component: LiveData<Todo?> = _component

    fun refresh(userId: Int) {
        setIsRefreshing(true)
        TodoListController.get(userId) { todo, throwable ->
            setIsRefreshing(false)
            _component.value = todo
        }
    }

}