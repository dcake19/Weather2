package com.example.weather2.dagger

import com.example.data_repository_location.LocationDataCache
import com.example.data_repository_location.LocationDataNetwork
import com.example.data_repository_location.LocationsRepositoryImpl
import com.example.data_repository_location.auto_complete.AutoCompleteDataNetwork
import com.example.data_repository_location.auto_complete.AutoCompleteRepositoryImpl
import com.example.data_storage_location.LocationCache
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.domain.LocationsRepository
import com.example.domain.autocomplete.AutoCompleteRepository
import com.example.weather2.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationDataModule {

    @Provides
    @Singleton
    fun provideLocationRepository(locationApi: LocationDataNetwork,
                                  locationDataCache: LocationDataCache): LocationsRepository{
        return LocationsRepositoryImpl(locationApi, locationDataCache)
    }

    @Provides
    @Singleton
    fun provideLocationDataCache(locationDatabaseProvider: LocationDatabaseProvider): LocationDataCache{
        return LocationCache(locationDatabaseProvider)
    }

    @Provides
    @Singleton
    fun provideLocationDatabase(): LocationDatabaseProvider {
        return LocationDatabaseProvider(WeatherApplication.getContext()!!)
    }

    @Provides
    @Singleton
    fun provideAutoCompleteRepository(autoCompleteDataNetwork: AutoCompleteDataNetwork): AutoCompleteRepository{
        return AutoCompleteRepositoryImpl(autoCompleteDataNetwork)
    }

}