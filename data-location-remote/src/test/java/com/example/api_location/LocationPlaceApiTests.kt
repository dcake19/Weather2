package com.example.api_location

import com.example.api_location.TestUtils.getJsonPath
import com.example.api_location.key.GEOCODING_API_KEY
import com.example.data_repository_location.auto_complete.LocationPredictionData
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.mockwebserver.*
import java.net.HttpURLConnection
import kotlin.math.exp

class LocationPlaceApiTests {

    private var mockWebServer = MockWebServer()
    private lateinit var locationPlaceApi: LocationPlaceApi
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
        locationPlaceApi = LocationPlaceApi(retrofitLocationApi)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test() {
        val expected = listOf(
            LocationPredictionData("ChIJdd4hrwug2EcRmSrV3Vo6llI", listOf("London","UK")),
            LocationPredictionData("ChIJWdeZQOjKwoARqo8qxPo6AKE", listOf("Long Beach","CA","USA")),
            LocationPredictionData("ChIJbVivV1pD65QRcfkUxqURrCM", listOf("Londrina","State of Paraná","Brazil")),
            LocationPredictionData("ChIJC5uNqA7yLogRlWsFmmnXxyg", listOf("London","ON","Canada")),
            LocationPredictionData("ChIJUTPfl_81NoYRM1kZdPfwD7M", listOf("place_0","place_1","place_2")))

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getJsonPath("api/autocomplete/autocomplete.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/place/autocomplete/json?input=lon&types=%28cities%29&key=$GEOCODING_API_KEY") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        locationPlaceApi.getAutocompleteLocation("lon")
            .test().assertValues(expected)
    }


}