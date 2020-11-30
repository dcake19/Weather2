package com.example.data_weather_local

import com.example.data_weather.WeatherDailyForecastData
import com.example.data_weather.WeatherData
import com.example.data_weather.WeatherDataCache
import com.example.data_weather.WeatherHourlyForecastData
import com.example.data_weather_local.db.*
import io.reactivex.Maybe
import io.reactivex.Single

class WeatherCache(weatherDatabase: WeatherDatabaseProvider): WeatherDataCache {

    private val weatherDao = weatherDatabase.getWeatherDao()

    override fun insertWeatherForecast(placeId: String,weatherData: WeatherData,
        weatherHourlyForecastData: List<WeatherHourlyForecastData>,
        weatherDailyForecastData: List<WeatherDailyForecastData>) {
        weatherDao.insertFullForecast(map(placeId,weatherData),
            weatherHourlyForecastData.map{map(placeId,it)},
            weatherDailyForecastData.map{map(placeId,it)})
    }

    override fun getForecast(placeId: String): Maybe<WeatherData> {
        return weatherDao.getWeather(placeId).map { map(it) }
    }

    override fun getHourlyForecast(placeId: String): Single<List<WeatherHourlyForecastData>> {
        return weatherDao.getHourlyForecast(placeId).map { list -> list.map{map(it)} }
    }

    override fun getDailyForecast(placeId: String): Single<List<WeatherDailyForecastData>> {
        return weatherDao.getDailyForecast(placeId).map { list -> list.map{map(it)} }
    }

    override fun deleteForecast(placeId: String) {
        weatherDao.deleteWeatherForLocation(placeId)
    }

    private fun map(placeId: String, wd: WeatherData): WeatherEntity{
        return WeatherEntity(placeId,wd.timestamp,wd.weatherId,wd.temperature,wd.feelsLike,wd.rain,
            wd.sunriseTimestamp,wd.sunsetTimestamp,wd.windSpeed,wd.windDirection,
            wd.cloudCoverage,wd.pressure,wd.humidity,wd.description)
    }

    private fun map(placeId: String,hf: WeatherHourlyForecastData): HourlyForecastEntity{
        return HourlyForecastEntity(placeId,hf.timestamp,hf.weatherId,hf.temperature,hf.feelsLike,
            hf.rain,hf.windSpeed,hf.windDirection,hf.cloudCoverage,hf.description)
    }

    private fun map(placeId: String,df: WeatherDailyForecastData): DailyForecastEntity{
        return DailyForecastEntity(placeId,df.timestamp,df.weatherId,
            df.temperatureHigh,df.temperatureLow,df.rain,
            df.sunriseTimestamp,df.sunsetTimestamp,
            df.windSpeed,df.windDirection,df.cloudCoverage,df.pressure,df.humidity,df.description)
    }

    private fun map(forecast: WeatherAllForLocation): WeatherData{
        val we = forecast.weather
        return WeatherData(we.timestamp,we.weatherId,we.temperature,we.feelsLike,we.rain,
            we.sunriseTimestamp,we.sunsetTimestamp,we.windSpeed,we.windDirection,we.cloudCoverage,
            we.pressure,we.humidity,we.description,forecast.hourlyForecast.map { map(it) },
            forecast.dailyForecast.map { map(it) })
    }

    private fun map(hfe: HourlyForecastEntity): WeatherHourlyForecastData{
        return WeatherHourlyForecastData(hfe.timestamp,hfe.weatherId,hfe.temperature,hfe.feelsLike,
            hfe.rain,hfe.windSpeed,hfe.windDirection,hfe.cloudCoverage,hfe.description)
    }

    private fun map(dfe: DailyForecastEntity): WeatherDailyForecastData{
        return WeatherDailyForecastData(dfe.timestamp,dfe.weatherId,dfe.temperatureHigh,
            dfe.temperatureLow,dfe.rain,dfe.sunriseTimestamp,dfe.sunsetTimestamp,dfe.windSpeed,
            dfe.windDirection,dfe.cloudCoverage,dfe.pressure,dfe.humidity,dfe.description)
    }

}