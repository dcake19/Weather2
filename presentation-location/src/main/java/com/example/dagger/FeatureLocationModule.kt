package com.example.dagger

import com.example.domain.LocationInteractor
import com.example.domain.LocationRepository
import com.example.view_model.LocationViewModel
import com.example.view_model.LocationViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class FeatureLocationModule {

    @Provides
    @FeatureLocationScope
    fun provideLocationViewModel(locationInteractor: LocationInteractor): LocationViewModel{
        return LocationViewModelImpl(locationInteractor)
    }

    @Provides
    @FeatureLocationScope
    fun provideLocationInteractor(locationRepository: LocationRepository): LocationInteractor{
        return LocationInteractor(locationRepository)
    }
}