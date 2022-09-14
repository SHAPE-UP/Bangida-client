package com.example.shape_up_2022.retrofit

import android.app.Application
import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object {
        // 유튜브
        var networkServiceYoutube: NetworkServiceYoutube
        val retrofitYoutube: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        // 공공데이터 요청

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        var networkServicePlaceData: NetworkServicePlace
        val retrofitPlaceData: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://api.kcisa.kr/openapi/service/rest/")
                .client(client)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        // OkHttpClient
        var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build()

        /* 서버 요청용 */
        val apiserver = "http://192.168.219.104:5000/"
        // http://ec2-13-124-250-65.ap-northeast-2.compute.amazonaws.com:5000/

        var networkServiceUsers: NetworkServiceUsers
        val retrofitUsers: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(apiserver + "api/users/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var networkServiceTodo: NetworkServiceTodo
        val retrofitTodo: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(apiserver + "api/todo/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var networkServiceFamily: NetworkServiceFamily
        val retrofitFamily: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(apiserver + "api/family/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var networkServicePet: NetworkServicePet
        val retrofitPet: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(apiserver + "api/pet/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        init{
            networkServiceYoutube = retrofitYoutube.create(NetworkServiceYoutube::class.java)
            networkServicePlaceData = retrofitPlaceData.create(NetworkServicePlace::class.java)

            // 서버 요청용
            networkServiceUsers = retrofitUsers.create(NetworkServiceUsers::class.java)  // api/users/
            networkServiceTodo = retrofitTodo.create(NetworkServiceTodo::class.java)  // api/todo/
            networkServiceFamily = retrofitFamily.create(NetworkServiceFamily::class.java)  // api/family/
            networkServicePet = retrofitPet.create(NetworkServicePet::class.java) // api/pet
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