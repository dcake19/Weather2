package com.example.data_weather

import io.reactivex.Single

interface WeatherNetwork {
    fun getWeather(latitude: Double,longitude: Double): Single<WeatherData>
}