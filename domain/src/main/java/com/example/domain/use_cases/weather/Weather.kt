package com.example.domain.use_cases.weather

data class WeatherToday(val placeId: String, val placeName: String, val timestamp: Int,
                        val weatherId:Int, val temperature: Float,val feelsLike: Float,
                        val rain: Float,val sunriseTimestamp: Int,val sunsetTimestamp: Int,
                        val windSpeed: Float, val windDirection: Int,val cloudCoverage: Int,
                        val pressure: Int, val humidity: Int,
                        val hourly: List<WeatherTodayHourlyForecast>,
                        val daily: List<WeatherTodayDailyForecast>)

data class WeatherTodayHourlyForecast(val timestamp: Int, val weatherId: Int,
                                      val rain: Float, val temperature: Float)

data class WeatherTodayDailyForecast(val timestamp: Int,val weatherId: Int,val rain: Float,
                                     val temperatureHigh: Float,val temperatureLow: Float)

data class WeatherHourForecast(val placeId: String, val placeName: String, val timestamp: Int,
                               val weatherId:Int, val temperature: Float,val feelsLike: Float,
                               val rain: Float, val windSpeed: Float,
                               val windDirection: Int,val cloudCoverage: Int)

data class WeatherDayForecast(val placeId: String, val placeName: String,val timestamp: Int,
                              val weatherId:Int, val temperatureHigh: Float,val temperatureLow: Float,
                              val rain: Float,val sunriseTimestamp: Int,val sunsetTimestamp: Int,
                              val windSpeed: Float, val windDirection: Int,val cloudCoverage: Int,
                              val pressure: Int, val humidity: Int)