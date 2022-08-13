package com.example.shape_up_2022

import android.app.Application
import android.util.Log
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object {
        // kakao api
        var networkServiceKakao : NetworkServiceKakao
        val retrofitKakao: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // 유튜브
        var networkServiceYoutube: NetworkServiceYoutube
        val retrofitYoutube: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // 로그인
        var networkServiceAuth: NetworkServiceAuth
        val retrofitAuth: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://ec2-13-124-250-65.ap-northeast-2.compute.amazonaws.com:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        // "http://ec2-13-124-250-65.ap-northeast-2.compute.amazonaws.com:5000/"
        var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build()
        // data

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        var networkServicePlaceData: NetworkService3
        val retrofitPlaceData: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://api.kcisa.kr/openapi/service/rest/")
                .client(client)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            networkServiceYoutube = retrofitYoutube.create(NetworkServiceYoutube::class.java)
            networkServiceAuth = retrofitAuth.create(NetworkServiceAuth::class.java)
            networkServicePlaceData = retrofitPlaceData.create(NetworkService3::class.java)
            networkServiceKakao = retrofitKakao.create(NetworkServiceKakao::class.java)
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