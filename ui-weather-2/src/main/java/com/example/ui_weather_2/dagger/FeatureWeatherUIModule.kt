package com.example.ui_weather_2.dagger

import com.example.presentation_weather_2.dagger.FeatureWeatherScope
import com.example.ui_weather_2.today.LocationSetter
import dagger.Module
import dagger.Provides

@Module
class FeatureWeatherUIModule {

    @Provides
    @FeatureWeatherScope
    fun provideLocationSetter() = LocationSetter()

}