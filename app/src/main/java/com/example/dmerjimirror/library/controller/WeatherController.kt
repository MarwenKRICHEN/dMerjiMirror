package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.ClockAPI
import com.example.dmerjimirror.library.api.WeatherAPI
import com.example.dmerjimirror.library.model.response.Clock
import com.example.dmerjimirror.library.model.response.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherController {
    companion object {
        fun get(userId: Int, callback: (Weather?, Throwable?) -> Unit) {
            val apiCall = WeatherAPI.create().getWeather(userId)
            apiCall.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(weather: Weather, callback: (Weather?, Throwable?) -> Unit) {
            val apiCall = WeatherAPI.create().updateWeather(weather)
            apiCall.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}