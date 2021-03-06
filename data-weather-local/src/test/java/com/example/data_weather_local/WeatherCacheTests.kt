package com.example.data_weather_local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data_weather_local.db.WeatherAllForLocation
import com.example.data_weather_local.db.WeatherDao
import com.example.data_weather_local.db.WeatherDatabaseProvider
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
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

    private val placeId = "place_id"

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(dbProvider.getWeatherDao()).thenReturn(dao)
        cache = WeatherCache(dbProvider)
    }

    @Test
    fun insertForecast(){
        val weatherData = WeatherCacheTestUtil.createWeatherData()
        val weatherHourlyForecastData = WeatherCacheTestUtil.createHourlyForecastDataList()
        val weatherDailyForecastData = WeatherCacheTestUtil.createDailyForecastDataList()
        val weatherEntity = WeatherCacheTestUtil.createWeatherEntity(placeId)
        val hourlyForecastEntity = WeatherCacheTestUtil.createHourlyForecastEntityList(placeId)
        val dailyForecastEntity = WeatherCacheTestUtil.createDailyForecastEntityList(placeId)

        cache.insertWeatherForecast(placeId,weatherData,weatherHourlyForecastData,weatherDailyForecastData)

        Mockito.verify(dao).insertFullForecast(weatherEntity,hourlyForecastEntity,dailyForecastEntity)
    }

    @Test
    fun getForecast(){
        val weatherAllForLocation = WeatherAllForLocation(
            WeatherCacheTestUtil.createWeatherEntity(placeId),
            WeatherCacheTestUtil.createHourlyForecastEntityList(placeId),
            WeatherCacheTestUtil.createDailyForecastEntityList(placeId))
        Mockito.`when`(dao.getWeather(placeId)).thenReturn(Maybe.just(weatherAllForLocation))

        val forecastExpected = WeatherCacheTestUtil.createWeatherData()
        val forecastActual = cache.getForecast(placeId).test().values()[0]

        assertThat(forecastActual, `is`(forecastExpected))
    }

    @Test
    fun emptyForecast(){
        Mockito.`when`(dao.getWeather(placeId)).thenReturn(Maybe.empty())
        val empty = cache.getForecast(placeId).isEmpty.test().values()[0]
        assertThat(empty, `is`(true))
    }

    @Test
    fun getHourlyForecast(){
        Mockito.`when`(dao.getHourlyForecast(placeId)).thenReturn(
            Single.just(WeatherCacheTestUtil.createHourlyForecastEntityList(placeId)))

        val hourlyForecastExpected = WeatherCacheTestUtil.createHourlyForecastDataList()
        val hourlyForecastActual = cache.getHourlyForecast(placeId).test().values()[0]

        assertThat(hourlyForecastActual, `is`(hourlyForecastExpected))
    }

    @Test
    fun getDailyForecast(){
        Mockito.`when`(dao.getDailyForecast(placeId)).thenReturn(
            Single.just(WeatherCacheTestUtil.createDailyForecastEntityList(placeId)))

        val dailyForecastExpected = WeatherCacheTestUtil.createDailyForecastDataList()
        val dailyForecastActual = cache.getDailyForecast(placeId).test().values()[0]

        assertThat(dailyForecastActual, `is`(dailyForecastExpected))
    }

    @Test
    fun deleteForecast(){
        cache.deleteForecast(placeId)
        Mockito.verify(dao).deleteWeatherForLocation(placeId)
    }
}