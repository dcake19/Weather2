package com.example.weather2.dagger

import com.example.presentation_weather_2.dagger.FeatureWeatherModule
import com.example.presentation_weather_2.dagger.FeatureWeatherScope
import com.example.ui_weather_2.daily.FragmentWeatherDays
import com.example.ui_weather_2.hourly.FragmentWeatherHours
import com.example.ui_weather_2.today.FragmentWeatherTodayOverview
import dagger.Component

@Component(modules = [FeatureWeatherModule::class],dependencies = [AppComponent::class])
@FeatureWeatherScope
interface FeatureWeatherComponent {
    fun inject(fragmentWeatherDays: FragmentWeatherDays)
    fun inject(fragmentWeatherHours: FragmentWeatherHours)
    fun inject(fragmentWeatherTodayOverview: FragmentWeatherTodayOverview)
}