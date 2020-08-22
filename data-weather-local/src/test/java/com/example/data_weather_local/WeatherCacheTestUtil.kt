package com.example.data_weather_local

import com.example.data_weather_local.db.DailyForecastEntity
import com.example.data_weather_local.db.HourlyForecastEntity
import com.example.data_weather_local.db.WeatherEntity

object WeatherCacheTestUtil {
    fun createWeather(placeId: String,timestamp: Int=0) = WeatherEntity(placeId,
        timestamp,500,16.93f,
        15.91f,0.26f,timestamp+3600*6,timestamp+3600*20,
        4.1f, 20,90,1016,93)

    fun createHourlyForecast(placeId: String,timestamp: Int=0) = listOf(
        HourlyForecastEntity(placeId,timestamp,804,16.93f,
            15.79f,0f,4.27f,44,90),
        HourlyForecastEntity(placeId,timestamp+3600,500,18.94f,18.11f,
            0.37f,4.24f,46,93),
        HourlyForecastEntity(placeId,timestamp+3600*2,804,19.36f,18.67f,
            0f,4.41f,40,95),
        HourlyForecastEntity(placeId,timestamp+3600*3,500,21.84f,
            21.08f,0.67f,4.77f,40,96)
    )

    fun createDailyForecast(placeId: String,timestamp: Int=0) = listOf(
        DailyForecastEntity(placeId,timestamp+3600*24,501,
            20.77f,17.93f,5.02f,
            timestamp+3600*(24+6),timestamp+3600*(24+20),4.77f,40,
            93,1016,84),
        DailyForecastEntity(placeId,timestamp+3600*24*2,502,
            21.53f,16.91f,0f,
            timestamp+3600*(24*2+6),timestamp+3600*(24*2+20),3.17f,30,
            100,1011,77)
    )
}