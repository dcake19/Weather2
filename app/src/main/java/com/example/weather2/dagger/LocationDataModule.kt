package com.example.weather2.dagger

import com.example.data_repository_location.LocationDataCache
import com.example.data_repository_location.LocationDataNetwork
import com.example.data_repository_location.LocationRepositoryImpl2
import com.example.data_storage_location.LocationCache
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
    fun provideLocationRepository(locationApi: LocationDataNetwork,
                                  locationDataCache: LocationDataCache): LocationRepository{
        return LocationRepositoryImpl2(locationApi, locationDataCache)
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

}