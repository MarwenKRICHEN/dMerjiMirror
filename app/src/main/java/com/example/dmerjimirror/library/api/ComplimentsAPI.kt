package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ComplimentsAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): ComplimentsAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ComplimentsAPI::class.java)

        }
    }

    @GET("private/getcompliments")
    fun getCompliments(@Query("userid") userId: Int): Call<Component>

    @PUT("private/updatecompliments")
    fun updateCompliments(@Body compliments: Component): Call<Component>
}