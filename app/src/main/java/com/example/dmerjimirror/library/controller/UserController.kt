package com.example.dmerjimirror.library.controller

import android.R.attr.description
import com.example.dmerjimirror.library.api.UserAPI
import com.example.dmerjimirror.library.model.request.user.UserLogin
import com.example.dmerjimirror.library.model.request.user.UserRegister
import com.example.dmerjimirror.library.model.request.user.UserToken
import com.example.dmerjimirror.library.model.request.user.update.*
import com.example.dmerjimirror.library.model.response.Component
import com.example.dmerjimirror.library.model.response.ProfileImage
import com.example.dmerjimirror.library.model.response.User
import com.example.dmerjimirror.library.model.response.UserResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class UserController {
    companion object {
        fun login(user: UserLogin, callback: (UserResponse?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().login(user)
            apiCall.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
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

        fun refreshSignIn(token: UserToken, callback: (UserResponse?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().refreshSignIn(token)
            apiCall.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
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

        fun register(user: UserRegister, callback: (Any?, Throwable?, Int) -> Unit) {
            val apiCall = UserAPI.create().register(user)
            apiCall.enqueue(object : Callback<Any?> {
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    response.body()?.let {
                        callback(it, null, response.code())
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()), response.code())
                    }
                }

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    callback(null, t, 400)
                }

            })
        }

        private fun getMultipartBodyFromString(string: String): RequestBody {
            return MultipartBody.create(MediaType.parse("multipart/form-data"), string)
        }

        fun addImage(
            image: MultipartBody.Part,
            callback: (Any?, Throwable?, Int) -> Unit
        ) {
            val apiCall = UserAPI.create().addImage(image)
            apiCall.enqueue(object : Callback<ProfileImage> {
                override fun onResponse(call: Call<ProfileImage>, response: Response<ProfileImage>) {
                    response.body()?.let {
                        callback(it, null, response.code())
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()), response.code())
                    }
                }

                override fun onFailure(call: Call<ProfileImage>, t: Throwable) {
                    callback(null, t, 400)
                }

            })
        }

        fun update(apiCall: Call<User>, callback: (User?, Throwable?, Int?) -> Unit) {
            apiCall.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.body()?.let {
                        callback(it, null, response.code())
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()), response.code())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    callback(null, t, 400)
                }

            })
        }

        fun updateProfile(profile: UserUpdateProfile, callback: (User?, Throwable?, Int?) -> Unit) {
            val apiCall = UserAPI.create().updateProfile(profile)
            update(apiCall, callback)
        }

        fun updatePassword(
            profile: UserUpdatePassword,
            callback: (User?, Throwable?, Int?) -> Unit
        ) {
            val apiCall = UserAPI.create().updatePassword(profile)
            update(apiCall, callback)
        }

        fun updateEmail(profile: UserUpdateEmail, callback: (Throwable?, Int?) -> Unit) {
            val apiCall = UserAPI.create().updateEmail(profile)
            apiCall.enqueue(object : Callback<Any?> {
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    callback(null, response.code())
                }

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    callback(t, 500)
                }

            })
        }

        fun updateTimeFormat(
            profile: UserUpdateTimeFormat,
            callback: (User?, Throwable?, Int?) -> Unit
        ) {
            val apiCall = UserAPI.create().updateTimeFormat(profile)
            update(apiCall, callback)
        }

        fun updateUnit(profile: UserUpdateUnit, callback: (User?, Throwable?, Int?) -> Unit) {
            val apiCall = UserAPI.create().updateUnit(profile)
            update(apiCall, callback)
        }

        fun getComponents(userId: Int, callback: (ArrayList<Component>?, Throwable?) -> Unit) {
            val apiCall = UserAPI.create().getComponents(userId)
            apiCall.enqueue(object : Callback<ArrayList<Component>> {
                override fun onResponse(
                    call: Call<ArrayList<Component>>,
                    response: Response<ArrayList<Component>>
                ) {
                    response.body()?.let {
                        callback(it, null)
                    } ?: run {
                        callback(null, Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Component>>, t: Throwable) {
                    callback(null, t)
                }

            })
        }

        fun updateComponents(components: ArrayList<Component>) {
            val apiCall = UserAPI.create().updateComponents(components)
            apiCall.enqueue(object : Callback<Any?> {
                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ) {
                }

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                }

            })
        }

    }
}