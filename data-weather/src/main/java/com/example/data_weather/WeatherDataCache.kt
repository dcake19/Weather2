package com.example.data_weather

import io.reactivex.Maybe
import io.reactivex.Single

interface WeatherDataCache {
    fun insertWeatherForecast(placeId: String,weatherData: WeatherData,
                              weatherHourlyForecastData: List<WeatherHourlyForecastData>,
                              weatherDailyForecastData: List<WeatherDailyForecastData>)
    fun getForecast(placeId: String): Maybe<WeatherData>
    fun getHourlyForecast(placeId: String): Single<List<WeatherHourlyForecastData>>
    fun getDailyForecast(placeId: String): Single<List<WeatherDailyForecastData>>
    fun deleteForecast(placeId: String)
}