package com.example.api_location

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LocationGeocodeApiTests {

    private var mockWebServer = MockWebServer()
    private lateinit var locationGeocodeApi: LocationGeocodeApi
    private lateinit var retrofitLocationApi: RetrofitLocationApi

    @Before
    fun init(){
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitLocationApi = retrofit.create(RetrofitLocationApi::class.java)
        locationGeocodeApi = LocationGeocodeApi(retrofitLocationApi)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun locationsByLatLng(){

    }

    @Test
    fun locationsByAddress(){

    }

    @Test
    fun locationsByPlacesId(){

    }
}