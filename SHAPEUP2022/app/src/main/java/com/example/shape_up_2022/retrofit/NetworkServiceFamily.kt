package com.example.shape_up_2022.retrofit

import com.example.shape_up_2022.data.Family
import com.example.shape_up_2022.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface NetworkServiceFamily {
    @POST("getFamily")
    fun getFamily(
        @Body user: GetFamilyReq,
    ): Call<GetFamilyRes>


    @PUT("additionPetId")
    fun additionPetId(
        @Body user: AdditionPetIdReq,
    ): Call<AdditionPetIdRes>


}


data class GetFamilyReq(val familyID: String)
data class GetFamilyRes(val success: String, val family: FamilyBody, val familyCode: String)
data class FamilyBody(val familyCode: String, val userGroup: Array<User>)

data class AdditionPetIdReq(val familyID: String)
data class AdditionPetIdRes(val success: String)