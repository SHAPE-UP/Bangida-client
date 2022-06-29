package com.example.shape_up_2022

import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    @POST("api/user/register")
    fun register(
        @Body user: RegisterReq,
    ): Call<RegisterRes>

    @POST("api/user/login")
    fun login(
        @Body user: LoginReq,
    ): Call<LoginRes>

    @GET("api/user/logout")
    fun logout(
        @Body user: LogoutReq
    ): Call<LogoutRes>

}

data class RegisterReq(val name: String, val email: String, val password: String)

data class RegisterRes(val success: String)

data class LoginReq(val email: String, val password: String)

data class LoginRes(val loginSuccess: String, val message: String)

data class LogoutReq(val _id: String, val email: String, val password: String)

data class LogoutRes(val success: String)