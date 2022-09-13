package com.example.shape_up_2022.retrofit

import retrofit2.Call
import retrofit2.http.*

interface NetworkServiceUsers {
    @POST("register")
    fun register(
        @Body user: RegisterReq,
    ): Call<RegisterRes>

    @POST("login")
    fun login(
        @Body user: LoginReq,
    ): Call<LoginRes>

    @POST("addFamily")
    fun addFamliy(
        @Body body: AddFamilyReq,
    ): Call<AddFamilyRes>

    @POST("joinFamily")
    fun joinFamliy(
        @Body body: JoinFamilyReq,
    ): Call<JoinFamilyRes>

    @PUT("completeTest")
    fun completeTest(
        @Body body: CompleteTestReq,
    ): Call<CompleteTestRes>

}

// req, res
data class RegisterReq(val name: String, val email: String, val password: String)
data class RegisterRes(val success: String)

data class LoginReq(val email: String, val password: String)
data class LoginRes(val loginSuccess: String, val user: UserInfo)

data class AddFamilyReq(val userID: String)
data class AddFamilyRes(val success: String, val message: String, val familyID: String)

data class JoinFamilyReq(val familyCode: String, val email: String)
data class JoinFamilyRes(val success: String, val message: String, val familyID: String)

data class CompleteTestReq(val userID: String)
data class CompleteTestRes(val success: String, val message: String)

// data class
data class UserInfo(val _id: String, val name: String, val email: String, val tested: Boolean, val familyID: String, val achieve: ArrayList<Boolean>)