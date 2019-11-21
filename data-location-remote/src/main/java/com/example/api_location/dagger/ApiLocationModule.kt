package com.example.api_location.dagger

import com.example.api_location.LocationGeocodeApi
import com.example.api_location.LocationPlaceApi
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import com.example.data_repository_location.LocationDataNetwork
import com.example.data_repository_location.auto_complete.AutoCompleteDataNetwork
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiLocationModule {

    @Provides
    @Singleton
    fun provideLocationApi(): LocationDataNetwork {
        return LocationGeocodeApi(
            RetrofitClient().getClient().create(RetrofitLocationApi::class.java))
    }

    @Provides
    @Singleton
    fun provideAutoCompleteApi(): AutoCompleteDataNetwork{
        return LocationPlaceApi(RetrofitClient().getClient().create(RetrofitLocationApi::class.java))
    }

}