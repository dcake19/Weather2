package com.example.data_weather_local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data_weather_local.db.WeatherDao
import com.example.data_weather_local.db.WeatherDatabaseProvider
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherCacheTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var cache: WeatherCache

    @Mock lateinit var dbProvider: WeatherDatabaseProvider
    @Mock lateinit var dao: WeatherDao

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(dbProvider.getWeatherDao()).thenReturn(dao)
        cache = WeatherCache(dbProvider)
    }


}