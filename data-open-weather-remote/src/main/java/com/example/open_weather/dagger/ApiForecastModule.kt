package com.example.open_weather.dagger

import com.example.data_weather.WeatherNetwork
import com.example.open_weather.OpenWeatherApi
import com.example.open_weather.RetrofitClient
import com.example.open_weather.WeatherApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiForecastModule {

    @Provides
    @Singleton
    fun provideForecastApi(): WeatherNetwork{
        return WeatherApi(RetrofitClient().getClient().create(OpenWeatherApi::class.java))
    }
}