package com.example.presentation_weather_2

data class WeatherTodayView (val placeId: String, val placeName: String, val dateTime: String,
                           val weatherId:Int, val temperature: String,val feelsLike:String,
                           val rain: String, val sunrise: String, val sunset: String,
                           val windSpeed: String, val windDirection: Int, val cloudCoverage: String,
                           val pressure: String, val humidity: String,val description: String,
                           val hourly: List<WeatherTodayHourlyForecastView>,
                           val daily: List<WeatherTodayDailyForecastView>)

data class WeatherTodayHourlyForecastView(val time: String, val weatherId: Int,
                                         val temperature: String,val rain: String)

data class WeatherTodayDailyForecastView(val day: String,val weatherId: Int,
                             val temperatureHigh: String,val temperatureLow: String,val rain: String)

data class WeatherHourForecastView(val placeId: String,val placeName: String,val time: String,
                                   val weatherId: Int,val temperature: String,val feelsLike:String,
                                   val rain: String, val windSpeed: String, val windDirection: Int,
                                   val cloudCoverage: String,val description: String)

data class WeatherDayForecastView(val placeId: String,val placeName: String,val date: String,
                                  val weatherId: Int,val temperatureHigh: String,
                                  val temperatureLow: String,val rain: String,
                                  val sunrise: String, val sunset: String,val windSpeed: String,
                                  val windDirection: Int,val cloudCoverage: String,
                                  val pressure: String,val humidity: String,val description: String)
