package com.example.data_repsoitory_weather

import com.example.data_weather.WeatherDataCache
import com.example.data_weather.WeatherNetwork
import com.example.data_weather.WeatherRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WeatherRepositoryTests {

    private lateinit var repository: WeatherRepositoryImpl
    @Mock lateinit var network: WeatherNetwork
    @Mock lateinit var cache: WeatherDataCache

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        repository = WeatherRepositoryImpl(network,cache)
    }

    //@Test

}