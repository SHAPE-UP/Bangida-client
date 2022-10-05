package com.example.shape_up_2022.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkServiceInsert {
    @POST("image")
    fun image(
    ): Call<ImageRes>

    @POST("record")
    fun record(
    ): Call<RecordRes>
}

data class ImageRes(val success: Boolean)
data class RecordRes(val success: Boolean)