package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.Todo
import com.example.dmerjimirror.library.model.response.TodoElement
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TodoListAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): TodoListAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(TodoListAPI::class.java)

        }
    }

    @GET("private/gettodolist")
    fun getTodo(@Query("userid") userId: Int): Call<Todo>

    @PUT("private/getnewsfeed")
    fun updateTodo(@Body todo: Todo): Call<Todo>

    @POST("private/addtodoelement")
    fun addTodoElement(@Body todoElement: TodoElement): Call<TodoElement>

    @PUT("private/updatetodoelement")
    fun updateTodoElement(@Body todoElement: TodoElement): Call<TodoElement>

}