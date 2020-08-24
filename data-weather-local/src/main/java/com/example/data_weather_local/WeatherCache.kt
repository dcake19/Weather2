package com.example.data_weather_local

import com.example.data_weather.WeatherDailyForecastData
import com.example.data_weather.WeatherData
import com.example.data_weather.WeatherDataCache
import com.example.data_weather.WeatherHourlyForecastData
import com.example.data_weather_local.db.*
import io.reactivex.Single

class WeatherCache(weatherDatabase: WeatherDatabaseProvider): WeatherDataCache {

    private val weatherDao = weatherDatabase.getWeatherDao()

    override fun getForecast(placeId: String): Single<WeatherData> {
        return weatherDao.getWeather(placeId).map { map(it) }
    }

    override fun getHourlyForecast(placeId: String): Single<List<WeatherHourlyForecastData>> {
        return weatherDao.getHourlyForecast(placeId).map { list -> list.map{map(it)} }
    }

    override fun getDailyForecast(placeId: String): Single<List<WeatherDailyForecastData>> {
        return weatherDao.getDailyForecast(placeId).map { list -> list.map{map(it)} }
    }

    private fun map(forecast: WeatherAllForLocation): WeatherData{
        val we = forecast.weather
        return WeatherData(we.timestamp,we.weatherId,we.temperature,we.feelsLike,we.rain,
            we.sunriseTimestamp,we.sunsetTimestamp,we.windSpeed,we.windDirection,we.cloudCoverage,
            we.pressure,we.humidity,forecast.hourlyForecast.map { map(it) },
            forecast.dailyForecast.map { map(it) })
    }

    private fun map(hfe: HourlyForecastEntity): WeatherHourlyForecastData{
        return WeatherHourlyForecastData(hfe.timestamp,hfe.weatherId,hfe.temperature,hfe.feelsLike,
            hfe.rain,hfe.windSpeed,hfe.windDirection,hfe.cloudCoverage)
    }

    private fun map(dfe: DailyForecastEntity): WeatherDailyForecastData{
        return WeatherDailyForecastData(dfe.timestamp,dfe.weatherId,dfe.temperatureHigh,
            dfe.temperatureLow,dfe.rain,dfe.sunriseTimestamp,dfe.sunsetTimestamp,dfe.windSpeed,
            dfe.windDirection,dfe.cloudCoverage,dfe.pressure,dfe.humidity)
    }
}