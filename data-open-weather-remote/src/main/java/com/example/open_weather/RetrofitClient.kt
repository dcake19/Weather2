package com.example.open_weather

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object Client{
        private var retrofit: Retrofit? = null
    }

    fun getClient() : Retrofit {

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.callTimeout(10,TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClientBuilder.addInterceptor(interceptor)

        val r = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        if(retrofit ==null){
            retrofit = r
        }

        return retrofit ?:r

    }
}