package com.example.api_location.dagger

import com.example.api_location.LocationGeocodingApi
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import com.example.data_repository_location.LocationDataNetwork
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiLocationModule {

    @Provides
    @Singleton
    fun provideLocationApi(): LocationDataNetwork {
        return LocationGeocodingApi(
            RetrofitClient().getClient().create(RetrofitLocationApi::class.java))
    }

}