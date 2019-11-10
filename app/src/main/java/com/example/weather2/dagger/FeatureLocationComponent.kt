package com.example.weather2.dagger

import com.example.presentation_location_view_model.dagger.FeatureLocationModule
import com.example.presentation_location_view_model.dagger.FeatureLocationScope
import com.example.locations.FragmentLocations
import dagger.Component

@Component(modules = [FeatureLocationModule::class],dependencies = [AppComponent::class])
@FeatureLocationScope
interface FeatureLocationComponent {
    fun inject(fragmentLocations: FragmentLocations)
   // fun getLocationViewModel(): LocationViewModel
}