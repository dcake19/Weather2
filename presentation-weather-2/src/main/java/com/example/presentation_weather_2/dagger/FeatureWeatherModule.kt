package com.example.presentation_weather_2.dagger

import com.example.domain.use_cases.location.LocationInteractor
import com.example.domain.use_cases.location.LocationsRepository
import com.example.domain.use_cases.weather.WeatherInteractor
import com.example.domain.use_cases.weather.WeatherRepository
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModelImpl
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModel
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModelImpl
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.presentation_weather_2.main.WeatherMainForecastViewModelImpl
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class FeatureWeatherModule {

    @Provides
    @FeatureWeatherScope
    fun provideWeatherMainForecastViewModel(weatherInteractor: WeatherInteractor,
                                            locationInteractor: LocationInteractor,
                                            scheduler: RxSchedulerProvider): WeatherMainForecastViewModel{
        val locationsEmitter = ViewModelEmitter<List<LocationView>>(true)
        val forecastEmitter = ViewModelEmitter<WeatherTodayView>()
        return WeatherMainForecastViewModelImpl(weatherInteractor,locationInteractor,
            scheduler,locationsEmitter, forecastEmitter)
    }

    @Provides
    @FeatureWeatherScope
    fun provideWeatherHourlyForecastViewModel(weatherInteractor: WeatherInteractor,
                                              scheduler: RxSchedulerProvider): WeatherHourlyForecastViewModel {
        val forecastEmitter = ViewModelEmitter<List<WeatherHourForecastView>>()
        return WeatherHourlyForecastViewModelImpl(weatherInteractor,scheduler,forecastEmitter)
    }

    @Provides
    @FeatureWeatherScope
    fun provideWeatherDailyForecastViewModel(weatherInteractor: WeatherInteractor,
                                             scheduler: RxSchedulerProvider): WeatherDailyForecastViewModel {
        val forecastEmitter = ViewModelEmitter<List<WeatherDayForecastView>>()
        return WeatherDailyForecastViewModelImpl(weatherInteractor,scheduler,forecastEmitter)
    }

    @Provides
    @FeatureWeatherScope
    fun provideWeatherInteractor(weatherRepository: WeatherRepository,
                                 locationsRepository: LocationsRepository): WeatherInteractor {
        return WeatherInteractor(weatherRepository,locationsRepository)
    }

    @Provides
    @FeatureWeatherScope
    fun provideLocationInteractor(locationsRepository: LocationsRepository): LocationInteractor {
        return LocationInteractor(locationsRepository)
    }

}