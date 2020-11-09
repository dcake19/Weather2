package com.example.presentation_weather_2.main

import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import io.reactivex.Observable

interface WeatherMainForecastViewModel {
    fun start()
    fun pause()
    fun getLocationsObservable(): Observable<List<LocationView>>
    fun getWeatherObservable(): Observable<WeatherTodayView>
   // fun getLocations()
    fun getWeather(placeId: String)
}