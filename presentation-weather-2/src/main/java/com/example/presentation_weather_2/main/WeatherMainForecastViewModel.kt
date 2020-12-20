package com.example.presentation_weather_2.main

import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import io.reactivex.Observable

interface WeatherMainForecastViewModel {
    fun getLocationsObservable(): Observable<List<LocationView>>
    fun getWeatherObservable(): Observable<WeatherTodayView>
    fun getErrorObservable(): Observable<String>
    fun getPendingObservable(): Observable<Unit>
    fun start()
    fun pause()
    fun getWeather(placeId: String,refresh: Boolean)
    fun isPending(placeId: String): Boolean
    fun getForecast(latitude: Double,longitude: Double)
}