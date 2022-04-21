package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.CalendarAPI
import com.example.dmerjimirror.library.api.ClockAPI
import com.example.dmerjimirror.library.model.response.Calendar
import com.example.dmerjimirror.library.model.response.Clock
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClockController {
    companion object {
        fun get(userId: Int, callback: (Clock?, Throwable?) -> Unit) {
            val apiCall = ClockAPI.create().getClock(userId)
            apiCall.enqueue(object : Callback<Clock> {
                override fun onResponse(call: Call<Clock>, response: Response<Clock>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Clock>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(clock: Clock, callback: (Clock?, Throwable?) -> Unit) {
            val apiCall = ClockAPI.create().updateClock(clock)
            apiCall.enqueue(object : Callback<Clock> {
                override fun onResponse(call: Call<Clock>, response: Response<Clock>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Clock>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}