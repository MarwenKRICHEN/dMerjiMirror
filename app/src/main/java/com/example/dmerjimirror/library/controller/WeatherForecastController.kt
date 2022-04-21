package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.WeatherAPI
import com.example.dmerjimirror.library.api.WeatherForecastAPI
import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.library.model.response.WeatherForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherForecastController {
    companion object {
        fun get(userId: Int, callback: (WeatherForecast?, Throwable?) -> Unit) {
            val apiCall = WeatherForecastAPI.create().getForecast(userId)
            apiCall.enqueue(object : Callback<WeatherForecast> {
                override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(forecast: WeatherForecast, callback: (WeatherForecast?, Throwable?) -> Unit) {
            val apiCall = WeatherForecastAPI.create().updateForecast(forecast)
            apiCall.enqueue(object : Callback<WeatherForecast> {
                override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    callback(null, t)
                }

            })
        }
    }
}