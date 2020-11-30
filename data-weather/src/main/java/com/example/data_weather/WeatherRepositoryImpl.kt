package com.example.data_weather

import com.example.domain.use_cases.weather.*
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleSource

class WeatherRepositoryImpl(private val weatherDataNetwork: WeatherNetwork,
                            private val weatherDataCache: WeatherDataCache): WeatherRepository {

    override fun getForecast(placeId: String, latitude: Double, longitude: Double,
                             mustBeNewerThan: Int): Single<WeatherToday> {
        return weatherDataCache.getForecast(placeId)
            .switchIfEmpty (Maybe.defer{weatherDataNetwork.getWeather(latitude, longitude).toMaybe()})
           .flatMapSingle { if (it.timestamp>=mustBeNewerThan) Single.just(map(it))
            else weatherDataNetwork.getWeather(latitude, longitude).map { forecast -> map(forecast) } }
    }

    override fun getHourlyForecast(placeId: String): Single<List<WeatherHourForecast>> {
        return weatherDataCache.getHourlyForecast(placeId).map {list -> list.map{map(it)} }
    }

    override fun getDailyForecast(placeId: String): Single<List<WeatherDayForecast>> {
        return weatherDataCache.getDailyForecast(placeId).map {list -> list.map{map(it)} }
    }

    private fun map(wd: WeatherData): WeatherToday{
        return WeatherToday(wd.timestamp,wd.weatherId,wd.temperature,wd.feelsLike,wd.rain,
            wd.sunriseTimestamp,wd.sunsetTimestamp,wd.windSpeed,wd.windDirection,
            wd.cloudCoverage,wd.pressure,wd.humidity,wd.description,
            wd.hourlyForecast.map { mapForFullForecast(it)},
            wd.dailyForecast.map { mapForFullForecast(it)})
    }

    private fun mapForFullForecast(hf: WeatherHourlyForecastData): WeatherTodayHourlyForecast{
        return WeatherTodayHourlyForecast(hf.timestamp,hf.weatherId,hf.temperature,hf.rain)
    }

    private fun mapForFullForecast(hf: WeatherDailyForecastData): WeatherTodayDailyForecast{
        return WeatherTodayDailyForecast(hf.timestamp,hf.weatherId,
            hf.temperatureHigh,hf.temperatureLow,hf.rain)
    }

    private fun map(hf: WeatherHourlyForecastData): WeatherHourForecast{
        return WeatherHourForecast(hf.timestamp,hf.weatherId,hf.temperature,hf.feelsLike,
            hf.rain,hf.windSpeed,hf.windDirection,hf.cloudCoverage,hf.description)
    }

    private fun map(df: WeatherDailyForecastData): WeatherDayForecast{
        return WeatherDayForecast(df.timestamp,df.weatherId,df.temperatureHigh,df.temperatureLow,
            df.rain,df.sunriseTimestamp,df.sunsetTimestamp, df.windSpeed,
            df.windDirection,df.cloudCoverage,df.pressure,df.humidity,df.description)
    }

}