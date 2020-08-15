package com.example.open_weather

import com.example.data_weather.WeatherDailyForecastData
import com.example.data_weather.WeatherData
import com.example.data_weather.WeatherHourlyForecastData
import com.example.data_weather.WeatherNetwork
import com.example.open_weather.forecast.Daily
import com.example.open_weather.forecast.Forecast
import com.example.open_weather.forecast.Hourly
import io.reactivex.Single

class WeatherApi(private val openWeatherApi: OpenWeatherApi): WeatherNetwork {

    override fun getWeather(latitude: Double, longitude: Double): Single<WeatherData> {
        return openWeatherApi.getForecast(latitude,longitude).map { map(it) }
    }

    private fun map(forecast: Forecast): WeatherData{
        val c = forecast.current!!
        return WeatherData(c.dt!!,c.weather!![0].id!!,c.temp!!.toFloat(),c.feelsLike!!.toFloat(),
        c.rain?.h?.toFloat()?:0f,c.sunrise!!,c.sunset!!, c.windSpeed!!.toFloat(),c.windDeg!!,
            c.clouds!!,c.pressure!!,c.humidity!!,
            forecast.hourly?.map { map(it) }?: listOf(),
            forecast.daily?.map { map(it) }?: listOf())
    }

    private fun map(hourly: Hourly): WeatherHourlyForecastData{
        return WeatherHourlyForecastData(hourly.dt!!,hourly.weather!![0].id!!,
            hourly.temp!!.toFloat(),hourly.feelsLike!!.toFloat(),hourly.rain?.h?.toFloat()?:0f,
            hourly.windSpeed!!.toFloat(),hourly.windDeg!!,hourly.clouds!!)
    }

    private fun map(daily: Daily): WeatherDailyForecastData {
        return WeatherDailyForecastData(daily.dt!!,daily.weather!![0].id!!,
            daily.temp!!.max!!.toFloat(),daily.temp.min!!.toFloat(),daily.rain?.toFloat()?:0f,
            daily.sunrise!!,daily.sunset!!,daily.windSpeed!!.toFloat(),daily.windDeg!!,daily.clouds!!,
            daily.pressure!!,daily.humidity!!)
    }
}