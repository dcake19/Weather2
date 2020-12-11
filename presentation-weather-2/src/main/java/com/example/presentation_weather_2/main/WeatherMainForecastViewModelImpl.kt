package com.example.presentation_weather_2.main

import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.location.LocationInteractor
import com.example.domain.use_cases.weather.*
import com.example.presentation_weather_2.*
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class WeatherMainForecastViewModelImpl(private val weatherInteractor: WeatherInteractor,
                                       private val locationInteractor: LocationInteractor,
                                       private val scheduler: RxSchedulerProvider,
                                       private val locationsEmitter: ViewModelEmitter<List<LocationView>>,
                                       private val forecastEmitter: ViewModelEmitter<WeatherTodayView>): WeatherMainForecastViewModel {

    private val forecastPending = HashMap<String,Boolean>()

    override fun start() {
        locationInteractor.getStoredLocations()
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .doOnSuccess { it.forEach { forecastPending[it.placeId] = false } }
            .map { it.map { map(it)} }
            .subscribe(object : SingleObserver<List<LocationView>>{
                override fun onSuccess(t: List<LocationView>) {
                    locationsEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    private fun map(location: Location): LocationView{
        return LocationView(location.placeId,location.name)
    }

    override fun pause() {
        locationsEmitter.stopEmitting()
        forecastEmitter.stopEmitting()
    }

    override fun getLocationsObservable(): Observable<List<LocationView>> {
        return Observable.create { locationsEmitter.initEmitter(it) }
    }

    override fun getWeatherObservable(): Observable<WeatherTodayView> {
        return Observable.create { forecastEmitter.initEmitter(it) }
    }

    override fun getWeather(placeId: String) {
        if (forecastPending[placeId]==false){
            weatherInteractor.getForecast(placeId)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.computation())
                .map { map(placeId,it) }
                .subscribe(object : SingleObserver<WeatherTodayView>{
                    override fun onSuccess(t: WeatherTodayView) {
                        forecastPending[placeId] = false
                        forecastEmitter.post(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        forecastPending[placeId] = true
                    }

                    override fun onError(e: Throwable) {
                        forecastPending[placeId] = false
                    }

                })

        }
    }

    private fun map(placeId: String,forecast: WeatherToday): WeatherTodayView{
        return WeatherTodayView(placeId,
            convertToDate(forecast.timestamp),
            forecast.weatherId,
            "${Math.round(forecast.temperature)}$TEMPERATURE",
            "${Math.round(forecast.feelsLike)}$TEMPERATURE",
            "${Math.round(forecast.rain)} $RAIN",
            convertToTime(forecast.sunriseTimestamp),
            convertToTime(forecast.sunsetTimestamp),
            "${String.format("%.1f",forecast.windSpeed)} $WIND_SPEED",
            convertToWindDirection(forecast.windDirection),
            "${forecast.cloudCoverage}$CLOUD_COVERAGE",
            "${forecast.pressure} $PRESSURE",
            "${forecast.humidity}$HUMIDITY",
            forecast.description,
            forecast.hourly.map {
                WeatherTodayHourlyForecastView(
                    convertToTime(it.timestamp),
                    it.weatherId,
                    "${Math.round(it.temperature)}$TEMPERATURE",
                    "${Math.round(it.rain)} $RAIN")
            },
            forecast.daily.map {
                WeatherTodayDailyForecastView(
                    convertToDay(it.timestamp),
                    it.weatherId,
                    "${Math.round(it.temperatureHigh)}$TEMPERATURE",
                    "${Math.round(it.temperatureLow)}$TEMPERATURE",
                    "${Math.round(it.rain)} $RAIN"
                )
            })
    }


}