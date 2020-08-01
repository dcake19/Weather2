package com.example.open_weather

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiTests {

    private var mockWebServer = MockWebServer()
    private lateinit var openWeatherApi: OpenWeatherApi
    private lateinit var weatherApi: WeatherApi

    @Before
    fun init(){
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
        weatherApi = WeatherApi(openWeatherApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }



}