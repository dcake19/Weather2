package com.example.api_location

import com.example.api_location.repository.LocationRepository
import com.example.api_location.repository.LocationRepositoryImpl
import com.example.api_location.repository.db.LocationDaoProvider
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideLocationRepository(locationApi: LocationApi,
                                  locationDaoProvider: LocationDaoProvider): LocationRepository{
        return LocationRepositoryImpl(locationApi, locationDaoProvider)
    }

    @Provides
    fun provideLocationApi(): LocationApi{
        return LocationApiImpl(RetrofitClient().getClient().create(RetrofitLocationApi::class.java))
    }

    @Provides
    fun provideLocationDaoProvider(): LocationDaoProvider{
        return LocationDaoProvider()
    }

}