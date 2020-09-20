package com.example.ui_weather_2

import com.example.presentation_weather_2.WeatherTodayDailyForecastView
import com.example.presentation_weather_2.WeatherTodayHourlyForecastView
import com.example.presentation_weather_2.constants.*

object WeatherUITestUtil {


    fun getOverviewHourly(i: Int): List<WeatherTodayHourlyForecastView>{
        return (0..23).map { mapHour(i,it)}
    }

    private fun mapHour(i: Int,hour: Int): WeatherTodayHourlyForecastView{
        val id = (i*24 + hour)% weatherIds.size
        val weatherId = weatherIds[id]
        val rain = rainIds.indexOf(weatherId) + 1
        val temp = if (i<=12) hour + i%5 else 2*hour - 12 + i%5
        return WeatherTodayHourlyForecastView("$hour:00", weatherId,"$temp\\u00B0C","$rain mm")
    }

    fun getOverviewDay(i: Int): List<WeatherTodayDailyForecastView>{
        return (0..6).map { mapDay(i,it) }
    }

    private fun mapDay(i: Int,day: Int): WeatherTodayDailyForecastView{
        val id = (i*24 + day)% weatherIds.size
        val weatherId = weatherIds[id]
        val rain = rainIds.indexOf(weatherId) + 1
        val tempHigh = 15 + day + i%5
        val tempLow = day + i%5
        return WeatherTodayDailyForecastView(days[day],weatherId,
            "$tempHigh\\u00B0C","$tempLow\\u00B0C","$rain mm")
    }

    val days = listOf("Today","Mon","Tue","Wed","Thu","Fri","Sat")

    val weatherIds = listOf(THUNDERSTORM_WITH_LIGHT_RAIN,
        THUNDERSTORM_WITH_RAIN,
        THUNDERSTORM_WITH_HEAVY_RAIN ,
        THUNDERSTORM_LIGHT,
        THUNDERSTORM ,
        THUNDERSTORM_HEAVY ,
        THUNDERSTORM_RAGGED,
        THUNDERSTORM_WITH_LIGHT_DRIZZLE,
        THUNDERSTORM_WITH_DRIZZLE,
        THUNDERSTORM_WITH_HEAVY_DRIZZLE,
        DRIZZLE_LIGHT ,
        DRIZZLE ,
        DRIZZLE_HEAVY,
        DRIZZLE_RAIN_LIGHT,
        DRIZZLE_RAIN ,
        DRIZZLE_RAIN_HEAVY,
        DRIZZLE_SHOWER_RAIN ,
        DRIZZLE_HEAVY_SHOWER_RAIN,
        DRIZZLE_SHOWER,
        RAIN_LIGHT ,
        RAIN_MODERATE,
        RAIN_HEAVY,
        RAIN_VERY_HEAVY,
        RAIN_EXTREME,
        RAIN_FREEZING,
        RAIN_LIGHT_SHOWER,
        RAIN_SHOWER,
        RAIN_HEAVY_SHOWER,
        RAIN_RAGGED_SHOWER,
        SNOW_LIGHT,
        SNOW,
        SNOW_HEAVY,
        SNOW_SLEET,
        SNOW_LIGHT_SHOWER_SLEET,
        SNOW_SHOWER_SLEET,
        SNOW_LIGHT_RAIN,
        SNOW_RAIN ,
        SNOW_LIGHT_SHOWER,
        SNOW_SHOWER ,
        SNOW_HEAVY_SHOWER ,
        MIST,
        SMOKE,
        HAZE ,
        DUST_WHIRLS,
        FOG,
        SAND,
        DUST ,
        ASH ,
        SQUALL,
        TORNADO ,
        CLEAR,
        CLOUDS_FEW,
        CLOUDS_SCATTERED,
        CLOUDS_BROKEN,
        CLOUDS_OVERCAST)

    val rainIds = listOf(RAIN_LIGHT, RAIN_MODERATE, RAIN_FREEZING, RAIN_LIGHT_SHOWER,
        RAIN_RAGGED_SHOWER, RAIN_SHOWER, RAIN_HEAVY_SHOWER, RAIN_HEAVY, RAIN_VERY_HEAVY, RAIN_EXTREME)
}

