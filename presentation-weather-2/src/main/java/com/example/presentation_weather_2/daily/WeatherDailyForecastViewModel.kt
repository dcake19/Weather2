package com.example.presentation_weather_2.daily

import com.example.presentation_weather_2.WeatherDayForecastView
import io.reactivex.Observable

interface WeatherDailyForecastViewModel {
    fun getWeatherDaysObservable(): Observable<List<WeatherDayForecastView>>
    fun getWeatherDays(placeId: String)
}