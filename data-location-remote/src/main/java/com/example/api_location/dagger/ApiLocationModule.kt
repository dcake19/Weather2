package com.example.api_location.dagger

import com.example.api_location.LocationApi
import com.example.api_location.LocationApiImpl
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiLocationModule {

    @Provides
    @Singleton
    fun provideLocationApi(): LocationApi {
        return LocationApiImpl(
            RetrofitClient().getClient().create(RetrofitLocationApi::class.java)
        )
    }

}