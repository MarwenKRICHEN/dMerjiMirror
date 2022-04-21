package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.library.model.response.WeatherForecast
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface WeatherForecastAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): WeatherForecastAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherForecastAPI::class.java)

        }
    }

    @GET("private/getweatherforecast")
    fun getForecast(@Query("userid") userId: Int): Call<WeatherForecast>

    @PUT("private/updateweatherforecast")
    fun updateForecast(@Body forecast: WeatherForecast): Call<WeatherForecast>
}