package com.example.shape_up_2022

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object{
        //유튜브
        var networkService2 : NetworkService2
        val retrofit2: Retrofit
            get()= Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init{
            networkService2 = retrofit2.create(NetworkService2::class.java)
        }
    }
}