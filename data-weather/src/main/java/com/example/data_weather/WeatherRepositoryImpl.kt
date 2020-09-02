package com.example.data_weather

import com.example.domain.use_cases.weather.WeatherRepository

class WeatherRepositoryImpl(private val weatherDataNetwork: WeatherNetwork,
                            private val weatherDataCache: WeatherDataCache): WeatherRepository {
}