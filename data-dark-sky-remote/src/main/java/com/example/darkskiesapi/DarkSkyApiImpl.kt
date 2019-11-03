package com.example.darkskiesapi

import com.example.darkskiesapi.forecast.WeatherForecast
import io.reactivex.Single

class DarkSkyApiImpl(private val retrofitDarkSkyApi: RetrofitDarkSkyApi): DarkSkyApi, RetrofitDarkSkyApi by retrofitDarkSkyApi {

//    override fun getForecast(latitude: Double, longitude: Double): Single<WeatherForecast> {
//        return retrofitDarkSkyApi.getForecast("$latitude,$longitude")
//    }
}