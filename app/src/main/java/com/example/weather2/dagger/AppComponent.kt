package com.example.weather2.dagger

import com.example.api_location.dagger.ApiLocationModule
import com.example.domain.use_cases.location.LocationsRepository
import com.example.domain.use_cases.autocomplete.AutoCompleteRepository
import com.example.domain.use_cases.weather.WeatherRepository
import com.example.open_weather.dagger.ApiForecastModule
import com.example.utils.schedulers.RxSchedulerProvider
import com.example.utils.schedulers.SchedulersModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SchedulersModule::class,LocationDataModule::class,
    ApiLocationModule::class,WeatherDataModule::class,ApiForecastModule::class])
@Singleton
interface AppComponent {
    fun scheduler(): RxSchedulerProvider
    fun getLocationRepository(): LocationsRepository
    fun getAutoCompleteRepository(): AutoCompleteRepository
    fun getWeatherRepository(): WeatherRepository
}