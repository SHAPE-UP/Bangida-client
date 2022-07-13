package com.example.shape_up_2022

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkServiceKakao {
    @GET("v2/local/search/address.json")
    fun getLocation(
        @Header("Authorization") key: String,
        @Query("query") keyword: String,
        @Query("analyze_type") analyze_type: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Call<LocationPageModel>
}