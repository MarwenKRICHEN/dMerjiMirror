//package com.example.dmerjimirror.library.api
//
//import com.example.dmerjimirror.library.utils.Constants
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.POST
//
//interface UserAPI {
//    companion object {
//        private const val BASE_URL = Constants.BASE_URL
//
//        fun create(): UserAPI {
//
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//            return retrofit.create(UserAPI::class.java)
//
//        }
//    }
//
//    @POST("public/login")
//    fun login(@Header("Authorization") accessToken: String): Call<Accounts>
//
//    @GET("{account}")
//    fun getAccount(
//        @Header("Authorization") accessToken: String,
//        @Path(value = "account", encoded = true) account: String
//    ): Call<Account>
//}