package com.example.presentation_weather_2.daily

import com.example.domain.use_cases.weather.*
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.convertToTime
import com.example.presentation_weather_2.convertToWindDirection
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class WeatherDailyForecastViewModelImpl(private val interactor: WeatherInteractor,
                                        private val scheduler: RxSchedulerProvider,
                                        private val weatherDayForecastEmitter: ViewModelEmitter<List<WeatherDayForecastView>>): WeatherDailyForecastViewModel{

    override fun getWeatherDaysObservable(): Observable<List<WeatherDayForecastView>> {
        return Observable.create{ weatherDayForecastEmitter.initEmitter(it) }
    }

    override fun getWeatherDays(placeId: String) {
        interactor.getDailyForecast(placeId)
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map { it.map { map(placeId,it) }}
            .subscribe(object : SingleObserver<List< WeatherDayForecastView>>{
                override fun onSuccess(t: List<WeatherDayForecastView>) {
                    weatherDayForecastEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun map(placeId: String,forecast: WeatherDayForecast): WeatherDayForecastView{
        val dateTime = DateTime(forecast.timestamp * 1000L)
        val dayText = DateTimeFormat.forPattern("EE d MMMM").print(dateTime)

        return WeatherDayForecastView(placeId,dayText,forecast.weatherId,
            "${Math.round(forecast.temperatureHigh)}$TEMPERATURE",
            "${Math.round(forecast.temperatureLow)}$TEMPERATURE",
            "${Math.round(forecast.rain)} $RAIN",
            convertToTime(forecast.sunriseTimestamp),
            convertToTime(forecast.sunsetTimestamp),
            "${String.format("%.1f",forecast.windSpeed)} $WIND_SPEED",
            convertToWindDirection(forecast.windDirection),
            "${forecast.cloudCoverage}$CLOUD_COVERAGE",
            "${forecast.pressure} $PRESSURE",
            "${forecast.humidity}$HUMIDITY",
            forecast.description
        )
    }


}