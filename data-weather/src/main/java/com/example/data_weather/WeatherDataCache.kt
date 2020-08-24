package com.example.data_weather

import io.reactivex.Single

interface WeatherDataCache {
    fun getForecast(placeId: String): Single<WeatherData>
    fun getHourlyForecast(placeId: String): Single<List<WeatherHourlyForecastData>>
    fun getDailyForecast(placeId: String): Single<List<WeatherDailyForecastData>>
}