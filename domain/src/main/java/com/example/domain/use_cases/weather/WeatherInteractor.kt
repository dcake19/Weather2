package com.example.domain.use_cases.weather

class WeatherInteractor(private val weatherRepository: WeatherRepository)
    : WeatherRepository by weatherRepository