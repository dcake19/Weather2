package com.example.weather2

import com.example.api_location.LocationModule
import dagger.Component

@Component(modules = [LocationModule::class])
interface MyComponent {
    fun inject(mainActivity: MainActivity)
}