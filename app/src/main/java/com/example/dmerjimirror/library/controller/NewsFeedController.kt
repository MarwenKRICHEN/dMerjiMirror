package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.NewsFeedAPI
import com.example.dmerjimirror.library.api.WeatherForecastAPI
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.WeatherForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFeedController {
    companion object {
        fun get(userId: Int, callback: (NewsFeed?, Throwable?) -> Unit) {
            val apiCall = NewsFeedAPI.create().getNewsFeed(userId)
            apiCall.enqueue(object : Callback<NewsFeed> {
                override fun onResponse(call: Call<NewsFeed>, response: Response<NewsFeed>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<NewsFeed>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(newsFeed: NewsFeed, callback: (NewsFeed?, Throwable?) -> Unit) {
            val apiCall = NewsFeedAPI.create().updateNewsFeed(newsFeed)
            apiCall.enqueue(object : Callback<NewsFeed> {
                override fun onResponse(call: Call<NewsFeed>, response: Response<NewsFeed>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<NewsFeed>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}