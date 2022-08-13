package com.example.shape_up_2022.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServicePlace {
    @GET("convergence2019/getConver03")
    fun getList(
        @Query("pageNo") pageNo : String?,
        @Query("numOfRows") numOfRows : String?,
        @Query("keyword") keyword : String?,
        @Query("where") where : String?,
        @Query("serviceKey") serviceKey : String?,
    ) : Call<responseInfo>
}