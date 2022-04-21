package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.response.Calendar
import com.example.dmerjimirror.library.model.response.Clock
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CalendarAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): CalendarAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CalendarAPI::class.java)

        }
    }

    @GET("private/getcalendar")
    fun getCalendar(@Query("userid") userId: Int): Call<Calendar>

    @PUT("private/updatecalendar")
    fun updateCalendar(@Body calendar: Calendar): Call<Calendar>
}