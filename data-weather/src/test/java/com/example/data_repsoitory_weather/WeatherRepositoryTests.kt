package com.example.data_repsoitory_weather

import com.example.data_weather.WeatherDataCache
import com.example.data_weather.WeatherNetwork
import com.example.data_weather.WeatherRepositoryImpl
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

class WeatherRepositoryTests {

    private lateinit var repository: WeatherRepositoryImpl
    @Mock lateinit var network: WeatherNetwork
    @Mock lateinit var cache: WeatherDataCache

    private val placeId = "place_id"

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        repository = WeatherRepositoryImpl(network,cache)
    }

    @Test
    fun getHourlyForecast(){
        val forecastExpected = WeatherRepositoryTestUtil.createWeatherHourForecastList()

        `when`(cache.getHourlyForecast(placeId))
            .thenReturn(Single.just(WeatherRepositoryTestUtil.createHourlyForecastDataList()))

        val forecastActual = repository.getHourlyForecast(placeId).test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

    @Test
    fun getDailyForecast(){
        val forecastExpected = WeatherRepositoryTestUtil.createWeatherDailyForecastList()

        `when`(cache.getDailyForecast(placeId))
            .thenReturn(Single.just(WeatherRepositoryTestUtil.createDailyForecastDataList()))

        val forecastActual = repository.getDailyForecast(placeId).test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

    // cache recent
    @Test
    fun getForecast1(){
        val latitude = 0.0
        val longitude = 0.0
        val cachedTimestamp = 500

        val forecastExpected = WeatherRepositoryTestUtil.createWeatherToday(cachedTimestamp)

        `when`(cache.getForecast(placeId))
            .thenReturn(Maybe.just(WeatherRepositoryTestUtil.createWeatherData(cachedTimestamp)))

        val forecastActual = repository.getForecast(placeId,latitude,longitude,200)
            .test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

    // cache old
    @Test
    fun getForecast2(){
        val latitude = 0.0
        val longitude = 0.0
        val cachedTimestamp = 500
        val nowTimestamp = 1000
        val forecastExpected = WeatherRepositoryTestUtil.createWeatherToday(nowTimestamp)

        `when`(cache.getForecast(placeId))
            .thenReturn(Maybe.just(WeatherRepositoryTestUtil.createWeatherData(cachedTimestamp)))

        `when`(network.getWeather(latitude,longitude))
            .thenReturn(Single.just(WeatherRepositoryTestUtil.createWeatherData(nowTimestamp)))

        val forecastActual = repository.getForecast(placeId,latitude,longitude,700)
            .test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

    //cache empty
    @Test
    fun getForecast3(){
        val latitude = 0.0
        val longitude = 0.0
        val nowTimestamp = 1000

        val forecastExpected = WeatherRepositoryTestUtil.createWeatherToday(nowTimestamp)

        `when`(cache.getForecast(placeId)).thenReturn(Maybe.empty())

        `when`(network.getWeather(latitude,longitude))
            .thenReturn(Single.just(WeatherRepositoryTestUtil.createWeatherData(nowTimestamp)))

        val test = repository.getForecast(placeId,latitude,longitude,700).test()

        val forecastActual = repository.getForecast(placeId,latitude,longitude,700)
            .test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

}