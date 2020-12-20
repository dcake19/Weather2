package com.example.domain.use_cases.weather

import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.location.LocationsRepository
import io.reactivex.Single
import org.joda.time.DateTime

class WeatherInteractor(private val weatherRepository: WeatherRepository,
                        private val locationsRepository: LocationsRepository)
    : WeatherRepository by weatherRepository{

    fun getForecast(placeId: String): Single<WeatherToday>{
        val timeNowSecs = (DateTime.now().millis/1000).toInt()
        return locationsRepository.getCachedLocationByPlaceId(placeId)
            .flatMap { weatherRepository.getForecast(it.placeId,it.latitude,it.longitude,
                timeNowSecs - MAX_FORECAST_STORE_TIME)}
    }

    fun getForecast(latitude: Double,longitude: Double): Single<Pair<Location,WeatherToday>>{
        val timeNowSecs = (DateTime.now().millis/1000).toInt()
        return locationsRepository.getLocation(latitude, longitude)
            .flatMap { location ->
                weatherRepository.getForecast(location.placeId,location.latitude,location.longitude, timeNowSecs - MAX_FORECAST_STORE_TIME)
                .map {
                    Pair(location,it) }
            }
    }

}