package com.example.shape_up_2022

import android.app.Application
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build()
        // data
        var gson = GsonBuilder().setLenient().create()
        var networkServicePlaceData: NetworkService3
        val retrofitPlaceData: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://api.kcisa.kr/openapi/service/rest/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        init{
            networkService2 = retrofit2.create(NetworkService2::class.java)
            networkServiceAuth = retrofitAuth.create(NetworkService::class.java)
            networkServicePlaceData = retrofitPlaceData.create(NetworkService3::class.java)
        }



        private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
            val interceptor = HttpLoggingInterceptor { message ->
                Log.e(
                    "MyGitHubData :",
                    message + ""
                )
            }
            return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}