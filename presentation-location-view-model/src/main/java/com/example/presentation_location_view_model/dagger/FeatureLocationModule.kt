package com.example.presentation_location_view_model.dagger

import com.example.domain.LocationInteractor
import com.example.domain.LocationRepository
import dagger.Module
import dagger.Provides

@Module
class FeatureLocationModule {

    @Provides
    @FeatureLocationScope
    fun provideLocationViewModel(locationInteractor: LocationInteractor): com.example.presentation_location_view_model.LocationViewModel {
        return com.example.presentation_location_view_model.LocationViewModelImpl(locationInteractor)
    }

    @Provides
    @FeatureLocationScope
    fun provideLocationInteractor(locationRepository: LocationRepository): LocationInteractor{
        return LocationInteractor(locationRepository)
    }
}