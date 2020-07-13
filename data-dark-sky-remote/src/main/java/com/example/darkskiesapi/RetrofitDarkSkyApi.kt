package com.example.darkskiesapi


import com.example.darkskiesapi.forecast.WeatherForecast
import com.example.darkskiesapi.key.DARKSKY_API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitDarkSkyApi {

    @GET("/forecast/${DARKSKY_API_KEY}/{latLong}/?units=si")
    fun getForecast(@Path("latLong") latLong: String): Single<WeatherForecast>


    @GET("/forecast/${DARKSKY_API_KEY}/{lat},{long}/?units=si")
    fun getForecast(@Path("lat") lat: Double,
                    @Path("long") long: Double): Single<WeatherForecast>

}