package com.example.weather2.dagger

import com.example.api_location.LocationApi
import com.example.api_location.LocationApiImpl
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import com.example.data_repository_location.LocationRepositoryImpl
import com.example.data_storage_location.db.LocationDatabase
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.domain.LocationRepository
import com.example.weather2.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationDataModule {

    @Provides
    @Singleton
    fun provideLocationRepository(locationApi: LocationApi,
                                  locationDatabase: LocationDatabaseProvider): LocationRepository{
        return LocationRepositoryImpl(locationApi, locationDatabase)
    }

    @Provides
    @Singleton
    fun provideLocationDatabase(): LocationDatabaseProvider {
        return LocationDatabaseProvider(WeatherApplication.getContext()!!)
    }

}