package com.example.dmerjimirror.library.controller

import com.example.dmerjimirror.library.api.NewsFeedAPI
import com.example.dmerjimirror.library.api.UserAPI
import com.example.dmerjimirror.library.model.request.user.UserLogin
import com.example.dmerjimirror.library.model.request.user.UserRegister
import com.example.dmerjimirror.library.model.request.user.update.*
import com.example.dmerjimirror.library.model.response.NewsFeed
import com.example.dmerjimirror.library.model.response.User
import com.example.dmerjimirror.library.model.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {
    companion object {
        fun login(user: UserLogin, callback: (UserResponse?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().login(user)
            apiCall.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun register(user: UserRegister, callback: (String?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().register(user)
            apiCall.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun update(apiCall: Call<User>, callback: (User?, Throwable?) -> Unit) {
            apiCall.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun updateProfile(profile: UserUpdateProfile, callback: (User?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().updateProfile(profile)
            update(apiCall, callback)
        }

        fun updatePassword(profile: UserUpdatePassword, callback: (User?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().updatePassword(profile)
            update(apiCall, callback)
        }

        fun updateEmail(profile: UserUpdateEmail, callback: (User?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().updateEmail(profile)
            update(apiCall, callback)
        }

        fun updateTimeFormat(profile: UserUpdateTimeFormat, callback: (User?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().updateTimeFormat(profile)
            update(apiCall, callback)
        }

        fun updateUnit(profile: UserUpdateUnit, callback: (User?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().updateUnit(profile)
            update(apiCall, callback)
        }

    }
}