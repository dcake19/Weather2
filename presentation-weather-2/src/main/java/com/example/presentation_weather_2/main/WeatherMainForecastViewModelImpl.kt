package com.example.presentation_weather_2.main

import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.location.LocationInteractor
import com.example.domain.use_cases.weather.WeatherInteractor
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
    override fun start() {
        locationInteractor.getStoredLocations()
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
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
        TODO("Not yet implemented")
    }

    override fun getLocationsObservable(): Observable<List<LocationView>> {
        return Observable.create { locationsEmitter.initEmitter(it) }
    }

    override fun getWeatherObservable(): Observable<WeatherTodayView> {
        return Observable.create { forecastEmitter.initEmitter(it) }
    }

    override fun getWeather(placeId: String) {
        TODO("Not yet implemented")
    }


}