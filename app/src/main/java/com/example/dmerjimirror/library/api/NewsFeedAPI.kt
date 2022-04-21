package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.Weather
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface NewsFeedAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): NewsFeedAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NewsFeedAPI::class.java)

        }
    }

    @GET("private/getnewsfeed")
    fun getNewsFeed(@Query("userid") userId: Int): Call<NewsFeed>

    @PUT("private/updatenewsfeed")
    fun updateNewsFeed(@Body newsFeed: NewsFeed): Call<NewsFeed>
}