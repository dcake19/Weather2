package com.example.ui_weather_2

import android.content.Context
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