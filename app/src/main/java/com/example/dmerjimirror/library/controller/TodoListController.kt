package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.NewsFeedAPI
import com.example.dmerjimirror.library.api.TodoListAPI
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoListController {
    companion object {
        fun get(userId: Int, callback: (Todo?, Throwable?) -> Unit) {
            val apiCall = TodoListAPI.create().getTodo(userId)
            apiCall.enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(todo: Todo, callback: (Todo?, Throwable?) -> Unit) {
            val apiCall = TodoListAPI.create().updateTodo(todo)
            apiCall.enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun addTodoElement(todoElement: TodoElement, callback: (TodoElement?, Throwable?) -> Unit) {
            val apiCall = TodoListAPI.create().addTodoElement(todoElement)
            apiCall.enqueue(object : Callback<TodoElement> {
                override fun onResponse(call: Call<TodoElement>, response: Response<TodoElement>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<TodoElement>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun updateTodoElement(todoElement: TodoElement, callback: (TodoElement?, Throwable?) -> Unit) {
            val apiCall = TodoListAPI.create().updateTodoElement(todoElement)
            apiCall.enqueue(object : Callback<TodoElement> {
                override fun onResponse(call: Call<TodoElement>, response: Response<TodoElement>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<TodoElement>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}