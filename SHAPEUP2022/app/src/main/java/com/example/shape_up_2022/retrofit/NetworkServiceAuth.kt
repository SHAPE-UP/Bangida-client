package com.example.shape_up_2022.retrofit

import retrofit2.Call
import retrofit2.http.*

interface NetworkServiceAuth {
    @POST("api/users/register")
    fun register(
        @Body user: RegisterReq,
    ): Call<RegisterRes>

    @POST("api/users/login")
    fun login(
        @Body user: LoginReq,
    ): Call<LoginRes>

    @GET("api/users/logout")
    fun logout(

    ): Call<LogoutRes>

}

data class RegisterReq(val name: String, val email: String, val password: String)

data class RegisterRes(val success: String)

data class LoginReq(val email: String, val password: String)

data class LoginRes(val loginSuccess: String, val message: String)

data class LogoutRes(val success: String, val err: String)