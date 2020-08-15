package com.example.open_weather

import com.example.open_weather.forecast.Forecast
import com.example.open_weather.key.OPEN_WEATHER_API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/2.5/onecall?units=metric&appid=${OPEN_WEATHER_API_KEY}")
    fun getForecast(@Query("lat") lat: Double,
                    @Query("lon") long: Double): Single<Forecast>

}