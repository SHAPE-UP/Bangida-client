package com.example.shape_up_2022.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkServicePet {
    @GET("getPet/{petID}")
    fun getPetInfo(
        @Path("petID") petID: String
    ): Call<GetPetInfoRes>

}

// req, res
data class GetPetInfoRes(val success : Boolean, val petInfo: PetInfo)

// data class
data class PetInfo(val name: String , val sex: String, val weight: String, val height: String)