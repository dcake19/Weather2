package com.example.api_location

import com.example.api_location.key.GEOCODING_API_KEY
import com.example.data_repository_location.LocationData
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
        val expected = LocationData("ChIJv6-d15Jw2EcRJWe0Ap571s4",
            "Cambridge","England","United Kingdom",
            52.2052973,0.1218196,
            52.2066462802915,0.123168580291502,
            52.2039483197085,0.120470619708498)

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/geocode/geocode_latlng.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/geocode/json?latlng=52.2053%2C0.1218&key=$GEOCODING_API_KEY") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        locationGeocodeApi.getLocations(52.2053,0.1218)
            .test().assertValues(expected)
    }

    @Test
    fun locationsByAddress(){
        val expected = LocationData("ChIJdd4hrwug2EcRmSrV3Vo6llI",
            "London","England","United Kingdom",
            51.5073509,-0.1277583,
            51.6723432,0.148271,
            51.38494009999999,-0.3514683)

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/geocode/geocode_address.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/geocode/json?address=London&key=$GEOCODING_API_KEY") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        locationGeocodeApi.getLocations("London")
            .test().assertValues(expected)
    }

    @Test
    fun locationsByPlacesId(){
        val expected = LocationData("ChIJdd4hrwug2EcRmSrV3Vo6llI",
            "London","England","United Kingdom",
            51.5073509,-0.1277583,
            51.6723432,0.148271,
            51.38494009999999,-0.3514683)

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/geocode/geocode_address.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/geocode/json?place_id=ChIJdd4hrwug2EcRmSrV3Vo6llI&key=$GEOCODING_API_KEY") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        locationGeocodeApi.getLocationsByPlaceId("ChIJdd4hrwug2EcRmSrV3Vo6llI")
            .test().assertValues(expected)


    }
}