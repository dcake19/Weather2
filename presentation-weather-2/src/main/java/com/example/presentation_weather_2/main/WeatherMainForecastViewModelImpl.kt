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
import java.util.concurrent.TimeUnit

class WeatherMainForecastViewModelImpl(private val weatherInteractor: WeatherInteractor,
                                       private val locationInteractor: LocationInteractor,
                                       private val scheduler: RxSchedulerProvider,
                                       private val locationsEmitter: ViewModelEmitter<List<LocationView>>,
                                       private val forecastEmitter: ViewModelEmitter<WeatherTodayView>,
                                       private val errorEmitter: ViewModelEmitter<String>,
                                       private val pendingEmitter: ViewModelEmitter<Unit>): WeatherMainForecastViewModel {

    private val forecastPending = HashMap<String,Boolean>()
    private val forecastCache = HashMap<String,WeatherTodayView>()
    private var newLocationPending = false

    override fun getLocationsObservable(): Observable<List<LocationView>> {
        return Observable.create { locationsEmitter.initEmitter(it) }
    }

    override fun getWeatherObservable(): Observable<WeatherTodayView> {
        return Observable.create { forecastEmitter.initEmitter(it) }
    }

    override fun getErrorObservable(): Observable<String> {
        return Observable.create { errorEmitter.initEmitter(it) }
    }

    override fun getPendingObservable(): Observable<Unit> {
        return Observable.create { pendingEmitter.initEmitter(it) }
    }

    override fun isPending(placeId: String): Boolean {
        return forecastPending[placeId]?:false
    }

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
                    errorEmitter.post(e.message)
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

    override fun getWeather(placeId: String,refresh: Boolean) {
        if (forecastPending[placeId]==false){
            val cachedForecast = forecastCache[placeId]
            if (cachedForecast!=null && !refresh){
                forecastEmitter.post(cachedForecast)
            }else {
                weatherInteractor.getForecast(placeId)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.computation())
                    .map { map(placeId, it) }
                    .subscribe(object : SingleObserver<WeatherTodayView> {
                        override fun onSuccess(t: WeatherTodayView) {
                            forecastCache[placeId] = t
                            forecastPending[placeId] = false
                            forecastEmitter.post(t)
                        }

                        override fun onSubscribe(d: Disposable) {
                            if (!refresh) pendingEmitter.post(Unit)
                            forecastPending[placeId] = true
                        }

                        override fun onError(e: Throwable) {
                            forecastPending[placeId] = false
                            errorEmitter.post(e.message)
                        }
                    })
            }

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

    override fun getForecast(latitude: Double, longitude: Double) {
        if (!newLocationPending) {
            weatherInteractor.getForecast(latitude, longitude)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.computation())
                .map { Pair(map(it.first), map(it.first.placeId, it.second)) }
                .subscribe(object : SingleObserver<Pair<LocationView, WeatherTodayView>> {
                    override fun onSuccess(t: Pair<LocationView, WeatherTodayView>) {
                        locationsEmitter.post(listOf(t.first))
                        forecastCache[t.second.placeId] = t.second
                        forecastEmitter.post(t.second)
                        newLocationPending = false
                    }

                    override fun onSubscribe(d: Disposable) {
                        newLocationPending = true
                    }

                    override fun onError(e: Throwable) {
                        newLocationPending = false
                        errorEmitter.post(e.message)
                    }
                })
        }
    }
}