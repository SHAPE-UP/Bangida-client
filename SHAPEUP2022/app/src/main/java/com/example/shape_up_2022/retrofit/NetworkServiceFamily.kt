package com.example.shape_up_2022.retrofit

import com.example.shape_up_2022.data.Family
import com.example.shape_up_2022.data.User
import retrofit2.Call
import retrofit2.http.*

interface NetworkServiceFamily {
    @POST("getFamily")
    fun getFamily(
        @Body user: GetFamilyReq,
    ): Call<GetFamilyRes>


    @PUT("additionPetID")
    fun additionPetId(
        @Body user: AdditionPetIdReq,
    ): Call<AdditionPetIdRes>

    @GET("getPetID/{familyID}")
    fun getPetID(
        @Path("familyID") familyID: String
    ): Call<GetPetIDRes>
}


data class GetFamilyReq(val familyID: String)
data class GetFamilyRes(val success: String, val family: FamilyBody, val familyCode: String)
data class FamilyBody(val familyCode: String, val userGroup: Array<User>)

data class AdditionPetIdReq(val familyID: String, val petName: String)
data class AdditionPetIdRes(val success: Boolean)

data class GetPetIDRes(val success: Boolean, val petID: String)
