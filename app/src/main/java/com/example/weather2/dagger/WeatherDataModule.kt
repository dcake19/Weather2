package com.example.weather2.dagger

import com.example.data_weather.WeatherDataCache
import com.example.data_weather.WeatherNetwork
import com.example.data_weather.WeatherRepositoryImpl
import com.example.data_weather_local.WeatherCache
import com.example.data_weather_local.db.WeatherDatabaseProvider
import com.example.domain.use_cases.weather.WeatherRepository
import com.example.weather2.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherNetwork: WeatherNetwork,
                                 weatherDataCache: WeatherDataCache): WeatherRepository{
        return WeatherRepositoryImpl(weatherNetwork,weatherDataCache)
    }

    @Provides
    @Singleton
    fun provideWeatherDataCache(weatherDatabaseProvider: WeatherDatabaseProvider): WeatherDataCache{
        return WeatherCache(weatherDatabaseProvider)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(): WeatherDatabaseProvider{
        return WeatherDatabaseProvider(WeatherApplication.getContext()!!)
    }
}