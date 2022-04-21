package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.CalendarAPI
import com.example.dmerjimirror.library.model.response.Calendar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarController {
    companion object {
        fun get(userId: Int, callback: (Calendar?, Throwable?) -> Unit) {
            val apiCall = CalendarAPI.create().getCalendar(userId)
            apiCall.enqueue(object : Callback<Calendar>{
                override fun onResponse(call: Call<Calendar>, response: Response<Calendar>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Calendar>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(calendar: Calendar, callback: (Calendar?, Throwable?) -> Unit) {
            val apiCall = CalendarAPI.create().updateCalendar(calendar)
            apiCall.enqueue(object : Callback<Calendar>{
                override fun onResponse(call: Call<Calendar>, response: Response<Calendar>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Calendar>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}