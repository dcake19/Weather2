package com.example.presentation_weather_2.main

import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.location.LocationInteractor
import com.example.domain.use_cases.weather.WeatherInteractor
import com.example.domain.use_cases.weather.WeatherToday
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
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
                .map { map(it) }
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

    private fun map(forecast: WeatherToday): WeatherTodayView{
        
    }


}