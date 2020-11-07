package com.example.ui_weather_2

import android.location.Location
import com.example.presentation_weather_2.*
import com.example.presentation_weather_2.constants.*

object WeatherUITestUtil {

    fun getLocation(i: Int): LocationView{
        return LocationView("placeId_$i","Place Name $i")
    }

    fun getWeatherToday(i: Int,weatherId: Int = 0,temp: Int,feelLike:Int, rain: Int,
                        sunrise: String,sunset: String,windSpeed: Double, windDirection: Int,
                        cloudCoverage: Int,pressure: Int,humidity: Int,
                        initHour: Int=0,initDay: Int=0): WeatherTodayView{

        return WeatherTodayView("placeId_$i","Place Name $i",
            "$initHour:00",weatherId,"$temp\u00B0C",
            "$feelLike\u00B0C","$rain mm",sunrise,sunset,"$windSpeed m/s",
            windDirection,"$cloudCoverage%","$pressure hPa","$humidity%",
            "Description $i",getOverviewHourly(i,initHour), getOverviewDay(i,initDay))
    }

    fun getOverviewHourly(i: Int,initHour: Int): List<WeatherTodayHourlyForecastView>{
        return (1..24).map { mapHour(i,(it + initHour)%24)}
    }

    private fun mapHour(i: Int,hour: Int): WeatherTodayHourlyForecastView{
        val id = (i*24 + hour)% weatherIds.size
        val weatherId = weatherIds[id]
        val rain = rainIds.indexOf(weatherId) + 1
        val temp = if (i<=12) hour + i%5 else 2*hour - 12 + i%5
        return WeatherTodayHourlyForecastView("$hour:00", weatherId,"$temp\u00B0C","$rain mm")
    }

    fun getOverviewDay(i: Int,initDay: Int): List<WeatherTodayDailyForecastView>{
        return (1..7).map { mapDay(i,(it + initDay)%7,initDay) }
    }

    private fun mapDay(i: Int,day: Int,initDay: Int): WeatherTodayDailyForecastView{
        val id = (i*24 + day)% weatherIds.size
        val weatherId = weatherIds[id]
        val rain = rainIds.indexOf(weatherId) + 1
        val tempHigh = 15 + day + i%5
        val tempLow = day + i%5
        val dayName = if (day==initDay) "Today" else days[day]
        return WeatherTodayDailyForecastView(dayName,weatherId,
            "$tempHigh\u00B0C","$tempLow\u00B0C","$rain mm")
    }

    fun getHourForecastView(i: Int,hour:Int,weatherId: Int = 0,temp: Int,feelLike:Int, rain: Int,
                            windSpeed: Double, windDirection: Int, cloudCoverage: Int): WeatherHourForecastView{
        return WeatherHourForecastView("placeId_$i","Place Name $i","$hour:00",
            weatherId,"$temp\u00B0C",
            "$feelLike\u00B0C","$rain mm","$windSpeed m/s",
            windDirection,"$cloudCoverage%", "Description $i")
    }

    fun getDayForecastView(i: Int,date: String,weatherId: Int = 0,tempHigh: Int,tempLow:Int, rain: Int,
                           sunrise: String,sunset: String,windSpeed: Double, windDirection: Int,
                           cloudCoverage: Int,pressure: Int,humidity: Int): WeatherDayForecastView{
        return WeatherDayForecastView("placeId_$i","Place Name $i",date,
            weatherId,"$tempHigh\u00B0C",
            "$tempLow\u00B0C","$rain mm",sunrise,sunset,"$windSpeed m/s",
            windDirection,"$cloudCoverage%","$pressure hPa","$humidity%",
            "Description $i")
    }

    val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")

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

