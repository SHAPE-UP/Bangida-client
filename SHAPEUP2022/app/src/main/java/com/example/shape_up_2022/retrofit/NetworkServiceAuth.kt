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

    @POST("api/users/addFamily")
    fun addFamliy(
        @Body body: AddFamilyReq,
    ): Call<AddFamilyRes>

    @POST("api/users/joinFamily")
    fun joinFamliy(
        @Body body: JoinFamilyReq,
    ): Call<JoinFamilyRes>

    @PUT("api/users/completeTest")
    fun completeTest(
        @Body body: CompleteTestReq,
    ): Call<CompleteTestRes>

}

data class RegisterReq(val name: String, val email: String, val password: String)
data class RegisterRes(val success: String)

data class LoginReq(val email: String, val password: String)
data class LoginRes(val loginSuccess: String, val userId: String, val userName: String, val message: String)

data class LogoutRes(val success: String, val err: String)

data class AddFamilyReq(val userID: String)
data class AddFamilyRes(val success: String)

data class JoinFamilyReq(val familyCode: String, val email: String)
data class JoinFamilyRes(val success: String, val message: String)

data class CompleteTestReq(val userID: String)
data class CompleteTestRes(val success: String, val message: String)
