package com.example.presentation_weather_2.hourly

import com.example.presentation_weather_2.WeatherHourForecastView
import io.reactivex.Observable

interface WeatherHourlyForecastViewModel {
    fun getWeatherHoursObservable(): Observable<List<WeatherHourForecastView>>
    fun getWeatherHours(placeId: String)
}