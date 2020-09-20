package com.example.ui_weather_2.app

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.ui_weather_2.application.ApplicationFeatureWeather

class WeatherApplicationTest: Application(), ApplicationFeatureWeather {
    override fun injectWeather(fragment: Fragment) {

    }
}