package com.example.dmerjimirror.library.api

import com.example.dmerjimirror.library.model.request.user.UserLogin
import com.example.dmerjimirror.library.model.request.user.UserRegister
import com.example.dmerjimirror.library.model.request.user.update.*
import com.example.dmerjimirror.library.model.response.User
import com.example.dmerjimirror.library.model.response.UserResponse
import com.example.dmerjimirror.library.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserAPI {
    companion object {
        private const val BASE_URL = Constants.BASE_URL

        fun create(): UserAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserAPI::class.java)

        }
    }

    @POST("public/login")
    fun login(@Body user: UserLogin): Call<UserResponse>

    @POST("public/register")
    fun register(@Body user: UserRegister): Call<String?>

    @PUT("private/editprofile")
    fun updateProfile(@Body profile: UserUpdateProfile): Call<User>

    @PUT("private/editprofile")
    fun updatePassword(@Body profile: UserUpdatePassword): Call<User>

    @PUT("private/editprofile")
    fun updateEmail(@Body profile: UserUpdateEmail): Call<User>

    @PUT("private/editprofile")
    fun updateTimeFormat(@Body profile: UserUpdateTimeFormat): Call<User>

    @PUT("private/editprofile")
    fun updateUnit(@Body profile: UserUpdateUnit): Call<User>
}

