package com.example.weather2.dagger

import com.example.dagger.FeatureLocationModule
import com.example.dagger.FeatureLocationScope
import com.example.view_model.LocationViewModel
import com.example.weather2.FragmentLocation
import dagger.Component

@Component(modules = [FeatureLocationModule::class],dependencies = [AppComponent::class])
@FeatureLocationScope
interface FeatureLocationComponent {
    fun inject(fragmentLocation: FragmentLocation)
   // fun getLocationViewModel(): LocationViewModel
}