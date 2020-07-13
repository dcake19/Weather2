package com.example.open_weather

import com.example.open_weather.forecast.Forecast
import com.example.open_weather.key.OPEN_WEATHER_API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenWeatherApi {

    @GET("/onecall?lat={latValue}&lon={longValue}&units=metric&appid=${OPEN_WEATHER_API_KEY}")
    fun getForecast(@Path("latValue") lat: Double,
                    @Path("longValue") long: Double): Single<Forecast>

}