package com.example.presentation_weather_2.hourly

import com.example.domain.use_cases.weather.*
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.presentation_weather_2.convertToWindDirection
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class WeatherHourlyForecastViewModelImpl(
    private val interactor: WeatherInteractor,
    private val scheduler: RxSchedulerProvider,
    private val weatherHourForecastEmitter: ViewModelEmitter<List<WeatherHourForecastView>>): WeatherHourlyForecastViewModel {

    override fun getWeatherHoursObservable(): Observable<List<WeatherHourForecastView>> {
        return Observable.create{ weatherHourForecastEmitter.initEmitter(it) }
    }

    override fun getWeatherHours(placeId: String) {
        interactor.getHourlyForecast(placeId)
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map { it.map { map(placeId,it) } }
            .subscribe(object : SingleObserver<List<WeatherHourForecastView>>{
                override fun onSuccess(t: List<WeatherHourForecastView>) {
                    weatherHourForecastEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

    private fun map(placeId: String,forecast: WeatherHourForecast): WeatherHourForecastView{
        val dateTime = DateTime(forecast.timestamp * 1000L)
        val timeText = DateTimeFormat.forPattern("HH:mm").print(dateTime)

        return WeatherHourForecastView(
            placeId,
            timeText,
            forecast.weatherId,
            "${Math.round(forecast.temperature)}$TEMPERATURE",
            "${Math.round(forecast.feelsLike)}$TEMPERATURE",
            "${Math.round(forecast.rain)} $RAIN",
            "${String.format("%.1f",forecast.windSpeed)} $WIND_SPEED",
            convertToWindDirection(forecast.windDirection),
            "${forecast.cloudCoverage}$CLOUD_COVERAGE",
            forecast.description)
    }
}