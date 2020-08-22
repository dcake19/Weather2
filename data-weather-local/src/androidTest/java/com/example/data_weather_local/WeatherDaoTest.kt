package com.example.data_weather_local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data_weather_local.db.WeatherDao
import com.example.data_weather_local.db.WeatherDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var db: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,WeatherDatabase::class.java).build()
        dao = db.weatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertFullForecast(){
        val placeId = "place_id"
        val weather = WeatherDaoTestUtil.createWeather(placeId)
        val hourlyForecast = WeatherDaoTestUtil.createHourlyForecast(placeId)
        val dailyForecast = WeatherDaoTestUtil.createDailyForecast(placeId)

        dao.insertFullForecast(weather,hourlyForecast,dailyForecast)

        val weatherAll = dao.getWeather(placeId).test().values()[0]
        assertThat(weatherAll.weather, `is`(weather))
        assertThat(weatherAll.hourlyForecast, `is`(hourlyForecast))
        assertThat(weatherAll.dailyForecast, `is`(dailyForecast))
    }

    @Test
    fun replaceFullForecast(){
        val placeId = "place_id"

        val weather0 = WeatherDaoTestUtil.createWeather(placeId)
        val hourlyForecast0 = WeatherDaoTestUtil.createHourlyForecast(placeId)
        val dailyForecast0 = WeatherDaoTestUtil.createDailyForecast(placeId)

        dao.insertFullForecast(weather0,hourlyForecast0,dailyForecast0)

        val weather = WeatherDaoTestUtil.createWeather(placeId,60*60*24)
        val hourlyForecast = WeatherDaoTestUtil.createHourlyForecast(placeId,60*60*24)
        val dailyForecast = WeatherDaoTestUtil.createDailyForecast(placeId,60*60*24)

        dao.insertFullForecast(weather,hourlyForecast,dailyForecast)

        val weatherAll = dao.getWeather(placeId).test().values()[0]
        assertThat(weatherAll.weather, `is`(weather))
        assertThat(weatherAll.hourlyForecast, `is`(hourlyForecast))
        assertThat(weatherAll.dailyForecast, `is`(dailyForecast))
    }

    @Test
    fun getHourlyForecast(){
        val placeId = "place_id"
        val weather = WeatherDaoTestUtil.createWeather(placeId)
        val hourlyForecast = WeatherDaoTestUtil.createHourlyForecast(placeId)
        val dailyForecast = WeatherDaoTestUtil.createDailyForecast(placeId)

        dao.insertFullForecast(weather,hourlyForecast,dailyForecast)

        val hourlyForecastFromDb = dao.getHourlyForecast(placeId).test().values()[0]

        assertThat(hourlyForecastFromDb, `is`(hourlyForecast))
    }

    @Test
    fun getDailylyForecast(){
        val placeId = "place_id"
        val weather = WeatherDaoTestUtil.createWeather(placeId)
        val hourlyForecast = WeatherDaoTestUtil.createHourlyForecast(placeId)
        val dailyForecast = WeatherDaoTestUtil.createDailyForecast(placeId)

        dao.insertFullForecast(weather,hourlyForecast,dailyForecast)

        val dailyForecastFromDb = dao.getDailyForecast(placeId).test().values()[0]

        assertThat(dailyForecastFromDb, `is`(dailyForecast))
    }
}