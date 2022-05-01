package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.ComplimentsAPI
import com.example.dmerjimirror.library.api.NewsFeedAPI
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.NewsFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplimentsController {
    companion object {
        fun get(userId: Int, callback: (Component?, Throwable?) -> Unit) {
            val apiCall = ComplimentsAPI.create().getCompliments(userId)
            apiCall.enqueue(object : Callback<Component> {
                override fun onResponse(call: Call<Component>, response: Response<Component>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Component>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(compliments: Component, callback: (Component?, Throwable?) -> Unit) {
            val apiCall = ComplimentsAPI.create().updateCompliments(compliments)
            apiCall.enqueue(object : Callback<Component> {
                override fun onResponse(call: Call<Component>, response: Response<Component>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Component>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}