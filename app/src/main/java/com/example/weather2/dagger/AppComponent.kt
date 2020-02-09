package com.example.weather2.dagger

import com.example.api_location.dagger.ApiLocationModule
import com.example.domain.LocationsRepository
import com.example.domain.autocomplete.AutoCompleteRepository
import com.example.utils.schedulers.RxSchedulerProvider
import com.example.utils.schedulers.SchedulersModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [SchedulersModule::class,LocationDataModule::class,ApiLocationModule::class])
@Singleton
interface AppComponent {
    fun scheduler(): RxSchedulerProvider
    fun getLocationRepository(): LocationsRepository
    fun getAutoCompleteRepository(): AutoCompleteRepository
}