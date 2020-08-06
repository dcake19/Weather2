package com.example.open_weather

import com.example.data_weather.WeatherData

object WeatherNetworkTestUtil {
    fun getWeatherData(placeId: String): WeatherData{


        return WeatherData(placeId,1596278292,800,295.43f,
            296.89f,0,1596275582,1596327085, 3.32f,
            10,1,1012,88,)
    }
}