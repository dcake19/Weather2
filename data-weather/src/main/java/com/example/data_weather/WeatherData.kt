package com.example.data_weather

data class WeatherData(val timestamp: Int, val weatherId:Int,
                       val temperature: Float,val feelsLike: Float,
                       val rain: Float,val sunriseTimestamp: Int,val sunsetTimestamp: Int,
                       val windSpeed: Float, val windDirection: Int,val cloudCoverage: Int,
                       val pressure: Int, val humidity: Int,val description: String,
                       val hourlyForecast: List<WeatherHourlyForecastData>,
                       val dailyForecast: List<WeatherDailyForecastData>)

data class WeatherHourlyForecastData( val timestamp: Int, val weatherId:Int,
                                      val temperature: Float,val feelsLike: Float,
                                      val rain: Float, val windSpeed: Float,
                                      val windDirection: Int,val cloudCoverage: Int,
                                      val description: String)

data class WeatherDailyForecastData(val timestamp: Int, val weatherId:Int,
                                    val temperatureHigh: Float,val temperatureLow: Float,
                                    val rain: Float,val sunriseTimestamp: Int,val sunsetTimestamp: Int,
                                    val windSpeed: Float, val windDirection: Int,val cloudCoverage: Int,
                                    val pressure: Int, val humidity: Int,val description: String)