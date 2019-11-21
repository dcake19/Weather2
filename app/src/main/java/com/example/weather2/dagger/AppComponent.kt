package com.example.weather2.dagger

import com.example.api_location.dagger.ApiLocationModule
import com.example.domain.LocationsRepository
import com.example.domain.autocomplete.AutoCompleteRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [LocationDataModule::class,ApiLocationModule::class])
@Singleton
interface AppComponent {
    fun getLocationRepository(): LocationsRepository
    fun getAutoCompleteRepository(): AutoCompleteRepository
}