package com.example.data_weather_local.db

import androidx.room.*

@Entity(tableName = WeatherTables.CURRENT_WEATHER)
data class WeatherEntity(
    @PrimaryKey @ColumnInfo(name = CurrentWeatherColumns.PLACE_ID)var placeId: String,
    @ColumnInfo(name = CurrentWeatherColumns.TIMESTAMP)var timestamp: Int,
    @ColumnInfo(name = CurrentWeatherColumns.WEATHER_ID)var weatherId: Int,
    @ColumnInfo(name = CurrentWeatherColumns.TEMPERATURE)var temperature: Float,
    @ColumnInfo(name = CurrentWeatherColumns.FEELS_LIKE)var feelsLike: Float,
    @ColumnInfo(name = CurrentWeatherColumns.RAIN)var rain: Float,
    @ColumnInfo(name = CurrentWeatherColumns.SUNRISE_TIMESTAMP)var sunriseTimestamp: Int,
    @ColumnInfo(name = CurrentWeatherColumns.SUNSET_TIMESTAMP)var sunsetTimestamp: Int,
    @ColumnInfo(name = CurrentWeatherColumns.WIND_SPEED)var windSpeed: Float,
    @ColumnInfo(name = CurrentWeatherColumns.WIND_DIRECTION)var windDirection: Int,
    @ColumnInfo(name = CurrentWeatherColumns.CLOUD_COVERAGE)var cloudCoverage: Int,
    @ColumnInfo(name = CurrentWeatherColumns.PRESSURE)var pressure: Int,
    @ColumnInfo(name = CurrentWeatherColumns.HUMIDITY)var humidity: Int,
    @ColumnInfo(name = CurrentWeatherColumns.DESCRIPTION)var description: String) {
}

@Entity(tableName = WeatherTables.HOURLY_FORECAST,
    foreignKeys = [ForeignKey(entity = WeatherEntity::class,
    parentColumns = [CurrentWeatherColumns.PLACE_ID],
    childColumns = [CurrentWeatherColumns.PLACE_ID],
    onDelete = ForeignKey.CASCADE)])
data class HourlyForecastEntity(
    @ColumnInfo(name = CurrentWeatherColumns.PLACE_ID)var placeId: String,
    @ColumnInfo(name = HourlyForecastColumns.TIMESTAMP)var timestamp: Int,
    @ColumnInfo(name = HourlyForecastColumns.WEATHER_ID)var weatherId: Int,
    @ColumnInfo(name = HourlyForecastColumns.TEMPERATURE)var temperature: Float,
    @ColumnInfo(name = HourlyForecastColumns.FEELS_LIKE)var feelsLike: Float,
    @ColumnInfo(name = HourlyForecastColumns.RAIN)var rain: Float,
    @ColumnInfo(name = HourlyForecastColumns.WIND_SPEED)var windSpeed: Float,
    @ColumnInfo(name = HourlyForecastColumns.WIND_DIRECTION)var windDirection: Int,
    @ColumnInfo(name = HourlyForecastColumns.CLOUD_COVERAGE)var cloudCoverage: Int,
    @ColumnInfo(name = HourlyForecastColumns.DESCRIPTION)var description: String){
    @PrimaryKey(autoGenerate = true) var id:Long = 0
}

@Entity(tableName = WeatherTables.DAILY_FORECAST,
    foreignKeys = [ForeignKey(entity = WeatherEntity::class,
        parentColumns = [CurrentWeatherColumns.PLACE_ID],
        childColumns = [CurrentWeatherColumns.PLACE_ID],
        onDelete = ForeignKey.CASCADE)])
data class DailyForecastEntity(
    @ColumnInfo(name = CurrentWeatherColumns.PLACE_ID)var placeId: String,
    @ColumnInfo(name = DailyForecastColumns.TIMESTAMP)var timestamp: Int,
    @ColumnInfo(name = DailyForecastColumns.WEATHER_ID)var weatherId: Int,
    @ColumnInfo(name = DailyForecastColumns.TEMPERATURE_HIGH)var temperatureHigh: Float,
    @ColumnInfo(name = DailyForecastColumns.TEMPERATURE_LOW)var temperatureLow: Float,
    @ColumnInfo(name = DailyForecastColumns.RAIN)var rain: Float,
    @ColumnInfo(name = DailyForecastColumns.SUNRISE_TIMESTAMP)var sunriseTimestamp: Int,
    @ColumnInfo(name = DailyForecastColumns.SUNSET_TIMESTAMP)var sunsetTimestamp: Int,
    @ColumnInfo(name = DailyForecastColumns.WIND_SPEED)var windSpeed: Float,
    @ColumnInfo(name = DailyForecastColumns.WIND_DIRECTION)var windDirection: Int,
    @ColumnInfo(name = DailyForecastColumns.CLOUD_COVERAGE)var cloudCoverage: Int,
    @ColumnInfo(name = DailyForecastColumns.PRESSURE)var pressure: Int,
    @ColumnInfo(name = DailyForecastColumns.HUMIDITY)var humidity: Int,
    @ColumnInfo(name = DailyForecastColumns.DESCRIPTION)var description: String){
    @PrimaryKey(autoGenerate = true) var id:Long = 0
}

class WeatherAllForLocation(@Embedded val weather: WeatherEntity,
                            @Relation(parentColumn = CurrentWeatherColumns.PLACE_ID,
                                entityColumn = CurrentWeatherColumns.PLACE_ID)
                            val hourlyForecast: List<HourlyForecastEntity> = emptyList(),
                            @Relation(parentColumn = CurrentWeatherColumns.PLACE_ID,
                                entityColumn = CurrentWeatherColumns.PLACE_ID)
                            val dailyForecast: List<DailyForecastEntity> = emptyList())

