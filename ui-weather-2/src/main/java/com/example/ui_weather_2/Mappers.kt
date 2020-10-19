package com.example.ui_weather_2

import android.content.Context
import androidx.annotation.DrawableRes
import com.example.presentation_weather_2.constants.*

fun mapWindDirection(context: Context?,direction: Int): String{
    val directionStringId =  when(direction){
        NORTH -> R.string.north_abbr
        NORTH_EAST -> R.string.north_east_abbr
        NORTH_WEST -> R.string.north_west_abbr
        EAST -> R.string.east_abbr
        SOUTH_EAST -> R.string.south_east_abbr
        SOUTH_WEST -> R.string.south_west_abbr
        SOUTH -> R.string.south_abbr
        WEST -> R.string.west_abbr
        else -> return ""
    }
    return context?.getString(directionStringId)?:""
}

@DrawableRes
fun mapToImageResource(weatherId: Int): Int{
    return when(weatherId) {
        in listOf(THUNDERSTORM,THUNDERSTORM_WITH_LIGHT_RAIN, THUNDERSTORM_WITH_RAIN,
            THUNDERSTORM_WITH_HEAVY_RAIN,THUNDERSTORM_LIGHT,
            THUNDERSTORM_HEAVY,THUNDERSTORM_RAGGED, THUNDERSTORM_WITH_LIGHT_DRIZZLE,
            THUNDERSTORM_WITH_DRIZZLE,
            THUNDERSTORM_WITH_HEAVY_DRIZZLE) -> R.drawable.forecast_thunderstorm
        CLEAR -> R.drawable.forecast_day_sunny
        CLOUDS_FEW -> R.drawable.forecast_day_cloudy
        in listOf(CLOUDS_SCATTERED, CLOUDS_BROKEN, CLOUDS_OVERCAST)-> R.drawable.ic_wi_cloud
        in listOf(RAIN_SHOWER, RAIN_LIGHT_SHOWER, RAIN_HEAVY_SHOWER, RAIN_RAGGED_SHOWER)-> R.drawable.forecast_day_rain
        in listOf(RAIN_LIGHT,RAIN_MODERATE, RAIN_HEAVY, RAIN_VERY_HEAVY, RAIN_EXTREME, DRIZZLE_LIGHT,
            DRIZZLE,DRIZZLE_HEAVY,DRIZZLE_RAIN_LIGHT,DRIZZLE_RAIN,DRIZZLE_RAIN_HEAVY)-> R.drawable.forecast_rain
        in listOf(DRIZZLE_SHOWER, DRIZZLE_SHOWER_RAIN, DRIZZLE_HEAVY_SHOWER_RAIN)-> R.drawable.forecast_day_showers
        RAIN_FREEZING-> R.drawable.forecast_hail
        in listOf(SNOW_LIGHT_SHOWER, SNOW_SHOWER, SNOW_HEAVY_SHOWER, SNOW_LIGHT_RAIN,SNOW_RAIN)-> R.drawable.forecast_day_rain_mix
        in listOf(SNOW_LIGHT,SNOW, SNOW_HEAVY) -> R.drawable.forecast_snow
        in listOf(SNOW_LIGHT_SHOWER_SLEET,SNOW_SLEET,SNOW_SHOWER_SLEET)-> R.drawable.forecast_day_sleet
        in listOf(TORNADO, SQUALL) -> R.drawable.forecast_tornado
        in listOf(DUST, DUST_WHIRLS) -> R.drawable.forecast_dust
        SAND -> R.drawable.forecast_sandstorm
        SMOKE -> R.drawable.forecast_smoke
        in listOf(FOG, MIST) -> R.drawable.forecast_fog
        HAZE -> R.drawable.forecast_day_haze
        ASH -> R.drawable.forecast_volcano
        else -> return 0
    }
}