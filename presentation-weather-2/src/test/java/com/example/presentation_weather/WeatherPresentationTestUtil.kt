package com.example.presentation_weather

import com.example.domain.use_cases.weather.*
import com.example.presentation_weather_2.WeatherTodayHourlyForecastView
import com.example.presentation_weather_2.WeatherTodayView

object WeatherPresentationTestUtil {
    fun createWeatherHourForecastList(timestamp: Int=0) = listOf(
        WeatherHourForecast(timestamp,804,16.93f, 15.79f,0f,
            4.27f,44,90),
        WeatherHourForecast(timestamp+3600,500,18.94f,18.11f,
            0.37f,4.24f,46,93),
        WeatherHourForecast(timestamp+3600*2,804,19.36f,18.67f,
            0f,4.41f,40,95),
        WeatherHourForecast(timestamp+3600*3,500,21.84f,
            21.08f,0.67f,4.77f,40,96)
    )

    fun createWeatherDailyForecastList(timestamp: Int=0) = listOf(
        WeatherDayForecast(timestamp+3600*24,501,
            20.77f,17.93f,5.02f,
            timestamp+3600*(24+6),timestamp+3600*(24+20),4.77f,
            40, 93,1016,84),
        WeatherDayForecast(timestamp+3600*24*2,502,
            21.53f,16.91f,0f,
            timestamp+3600*(24*2+6),timestamp+3600*(24*2+20),
            3.17f,30, 100,1011,77)
    )

    fun createWeatherToday(timestamp: Int=0) = WeatherToday(timestamp,500,16.93f,
        15.91f,0.26f,timestamp+3600*6,timestamp+3600*20,
        4.1f, 20,90,1016,93,
        createWeatherTodayHourlyForecast(timestamp), createWeatherTodayDailyForecast(timestamp))

    fun createWeatherTodayHourlyForecast(timestamp: Int=0) = listOf(
        WeatherTodayHourlyForecast(timestamp,804,16.93f,0f),
        WeatherTodayHourlyForecast(timestamp+3600,500,18.94f,0.37f),
        WeatherTodayHourlyForecast(timestamp+3600*2,804,19.36f,0f),
        WeatherTodayHourlyForecast(timestamp+3600*3,500,21.84f,0.67f)
    )

    fun createWeatherTodayDailyForecast(timestamp: Int=0) = listOf(
        WeatherTodayDailyForecast(timestamp+3600*24,501,
            20.77f,17.93f,5.02f),
        WeatherTodayDailyForecast(timestamp+3600*24*2,502,
            21.53f,16.91f,0f)
    )

 //   fun createWeatherTodayView(placeId: String,placeName: String,dateTime: String) =
  //      WeatherTodayView(placeId,placeName,dateTime)

  //  fun createWeatherTodayHourlyForecastViewList(times: List<String>) = listOf(WeatherTodayHourlyForecastView(times[0],804,16.93f,0f))
}