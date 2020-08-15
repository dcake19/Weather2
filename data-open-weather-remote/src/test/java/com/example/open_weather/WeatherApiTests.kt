package com.example.open_weather

import com.example.open_weather.key.OPEN_WEATHER_API_KEY
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

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

    @Test
    fun getWeather1(){
        val lat = 41.992764
        val lon = 21.438682
        val expected = WeatherNetworkTestUtil.getWeatherData1()

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/open_weather/weather_1.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/data/2.5/onecall?units=metric&appid=$OPEN_WEATHER_API_KEY&lat=$lat&lon=$lon") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        weatherApi.getWeather(lat,lon).test().assertValues(expected)
    }

    @Test
    fun getWeather2(){
        val lat = 41.992764
        val lon = 21.438682
        val expected = WeatherNetworkTestUtil.getWeatherData2()

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/open_weather/weather_2.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/data/2.5/onecall?units=metric&appid=$OPEN_WEATHER_API_KEY&lat=$lat&lon=$lon") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        weatherApi.getWeather(lat,lon).test().assertValues(expected)
    }

}