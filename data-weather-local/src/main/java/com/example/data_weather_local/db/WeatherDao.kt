package com.example.data_weather_local.db

import androidx.room.*
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("select * from ${WeatherTables.CURRENT_WEATHER} where ${CurrentWeatherColumns.PLACE_ID} = :placeId")
    fun getWeather(placeId: String): Single<WeatherAllForLocation>

    @Insert
    fun insertHourlyForecast(hourly: List<HourlyForecastEntity>)

    @Query("delete from ${WeatherTables.HOURLY_FORECAST} where ${CurrentWeatherColumns.PLACE_ID} = :placeId")
    fun deleteHourlyForecast(placeId: String)

    @Transaction
    fun update(placeId: String,hourly: List<HourlyForecastEntity>){
        deleteHourlyForecast(placeId)
        insertHourlyForecast(hourly)
    }

    @Insert
    fun insertDailyForecast(daily: List<DailyForecastEntity>)

    @Query("delete from ${WeatherTables.DAILY_FORECAST} where ${CurrentWeatherColumns.PLACE_ID} = :placeId")
    fun deleteDailyForecast(placeId: String)

    @Transaction
    fun updateDailyForecast(placeId: String,daily: List<DailyForecastEntity>){
        deleteDailyForecast(placeId)
        insertDailyForecast(daily)
    }

    @Transaction
    fun insertFullForecast(weatherEntity: WeatherEntity,hourly: List<HourlyForecastEntity>,
                           daily: List<DailyForecastEntity>){
        deleteHourlyForecast(weatherEntity.placeId)
        deleteDailyForecast(weatherEntity.placeId)
        insert(weatherEntity)
        insertHourlyForecast(hourly)
        insertDailyForecast(daily)
    }

}