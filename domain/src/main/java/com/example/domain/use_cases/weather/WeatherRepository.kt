package com.example.domain.use_cases.weather

import io.reactivex.Single

interface WeatherRepository {
    fun getForecast(placeId: String,latitude: Double,longitude: Double,
                    mustBeNewerThan: Int): Single<WeatherToday>
    fun getHourlyForecast(placeId: String): Single<List<WeatherHourForecast>>
    fun getDailyForecast(placeId: String): Single<List<WeatherDayForecast>>
}