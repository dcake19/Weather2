package com.example.data_weather_local.db

object DatabaseName{
    const val WEATHER_DB = "weather_db"
}

object  WeatherTables{
    const val CURRENT_WEATHER = "current_weather"
    const val HOURLY_FORECAST = "hourly_forecast"
    const val DAILY_FORECAST = "daily_forecast"
}

object CurrentWeatherColumns{
    const val PLACE_ID = "place_id"
    const val TIMESTAMP = "timestamp"
    const val WEATHER_ID = "weather_id"
    const val TEMPERATURE = "temperature"
    const val FEELS_LIKE = "feels_like"
    const val RAIN = "rain"
    const val SUNRISE_TIMESTAMP = "sunrise_timestamp"
    const val SUNSET_TIMESTAMP = "sunset_timestamp"
    const val WIND_SPEED = "wind_speed"
    const val WIND_DIRECTION = "wind_direction"
    const val CLOUD_COVERAGE = "cloud_coverage"
    const val PRESSURE = "pressure"
    const val HUMIDITY = "humidity"
}

object HourlyForecastColumns{
    const val TIMESTAMP = "timestamp"
    const val WEATHER_ID = "weather_id"
    const val TEMPERATURE = "temperature"
    const val FEELS_LIKE = "feels_like"
    const val RAIN = "rain"
    const val WIND_SPEED = "wind_speed"
    const val WIND_DIRECTION = "wind_direction"
    const val CLOUD_COVERAGE = "cloud_coverage"
}

object DailyForecastColumns{
    const val TIMESTAMP = "timestamp"
    const val WEATHER_ID = "weather_id"
    const val TEMPERATURE_HIGH = "temperature_high"
    const val TEMPERATURE_LOW = "temperature_low"
    const val RAIN = "rain"
    const val SUNRISE_TIMESTAMP = "sunrise_timestamp"
    const val SUNSET_TIMESTAMP = "sunset_timestamp"
    const val WIND_SPEED = "wind_speed"
    const val WIND_DIRECTION = "wind_direction"
    const val CLOUD_COVERAGE = "cloud_coverage"
    const val PRESSURE = "pressure"
    const val HUMIDITY = "humidity"
}