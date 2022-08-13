package com.example.shape_up_2022.retrofit

import com.example.shape_up_2022.data.SearchListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServiceYoutube {
    @GET("youtube/v3/search")
    fun getList(
        @Query("key") key:String,
        @Query("q") search_query : String,
        @Query("type") returnType : String,
        @Query("part") returnData : String

    ) : Call<SearchListResponse>
}