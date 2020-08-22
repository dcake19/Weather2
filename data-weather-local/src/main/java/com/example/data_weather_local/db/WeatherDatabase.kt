package com.example.data_weather_local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class,HourlyForecastEntity::class,DailyForecastEntity::class],
    version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}