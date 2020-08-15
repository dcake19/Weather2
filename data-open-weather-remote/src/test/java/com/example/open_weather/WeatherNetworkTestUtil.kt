package com.example.open_weather

import com.example.data_weather.WeatherDailyForecastData
import com.example.data_weather.WeatherData
import com.example.data_weather.WeatherHourlyForecastData

object WeatherNetworkTestUtil {
    fun getWeatherData1(): WeatherData{
        return WeatherData(1597484140,500,16.93f,
            15.91f,0.26f,1597466577,1597519434,
            4.1f, 20,90,1016,93,
            getWeatherHourlyForecastData1(), getWeatherDailyForecastData1())
    }

    fun getWeatherHourlyForecastData1(): List<WeatherHourlyForecastData>{
        return listOf(WeatherHourlyForecastData(1597482000,804,16.93f,
            15.79f,0f,4.27f,44,90),
            WeatherHourlyForecastData(1597485600,500,18.94f,18.11f,
            0.37f,4.24f,46,93),
            WeatherHourlyForecastData(1597489200,804,19.67f,18.67f,
            0f,4.41f,40,95),
            WeatherHourlyForecastData(1597492800,500,21.84f,
                21.08f,0.67f,4.77f,40,96)
        )
    }

    fun getWeatherDailyForecastData1(): List<WeatherDailyForecastData>{
        return listOf(WeatherDailyForecastData(1597492800,501,
            20.77f,17.93f,5.02f,
            1597466577,1597519434,4.77f,40,
            93,1016,84),
            WeatherDailyForecastData(1597579200,502,
                21.53f,16.91f,0f,
                1597553076,1597605711,3.17f,30,
                100,1011,77))
    }

    fun getWeatherData2(): WeatherData{
        return WeatherData(1597484140,500,16.93f,
            15.91f,0f,1597466577,1597519434,
            4.1f, 20,90,1016,93,
            getWeatherHourlyForecastData1(), getWeatherDailyForecastData1())
    }

}