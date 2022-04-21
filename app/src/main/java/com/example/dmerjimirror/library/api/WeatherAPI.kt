package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.request.user.UserLogin
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WeatherAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): WeatherAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherAPI::class.java)

        }
    }

    @GET("private/getweather")
    fun getWeather(@Query("userid") userId: Int): Call<Weather>

    @PUT("private/updateweather")
    fun updateWeather(@Body weather: Weather): Call<Weather>
}