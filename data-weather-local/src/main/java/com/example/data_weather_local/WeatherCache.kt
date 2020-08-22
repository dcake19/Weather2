package com.example.data_weather_local

import com.example.data_weather.WeatherDataCache
import com.example.data_weather_local.db.WeatherDatabaseProvider

class WeatherCache(weatherDatabase: WeatherDatabaseProvider): WeatherDataCache {

    private val weatherDao = weatherDatabase.getWeatherDao()

}