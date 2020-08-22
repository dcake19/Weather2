package com.example.data_weather_local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data_weather_local.db.WeatherDao
import com.example.data_weather_local.db.WeatherDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
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
}