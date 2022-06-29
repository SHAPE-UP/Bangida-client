package com.example.shape_up_2022

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object {
        //유튜브
        var networkService2: NetworkService2
        val retrofit2: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // 로그인
        var networkServiceAuth: NetworkService
        val retrofitAuth: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://192.168.219.105:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        // "http://ec2-13-124-250-65.ap-northeast-2.compute.amazonaws.com:5000/"
        init{
            networkService2 = retrofit2.create(NetworkService2::class.java)
            networkServiceAuth = retrofitAuth.create(NetworkService::class.java)
        }
    }
}