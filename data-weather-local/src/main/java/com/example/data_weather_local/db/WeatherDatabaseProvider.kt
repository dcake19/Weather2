package com.example.data_weather_local.db

import android.content.Context
import androidx.room.Room

class WeatherDatabaseProvider(private val context: Context){

    fun getWeatherDao(): WeatherDao{
        return Room.databaseBuilder(context,WeatherDatabase::class.java,DatabaseName.WEATHER_DB)
            .fallbackToDestructiveMigration()
            .build()
            .weatherDao()
    }
}