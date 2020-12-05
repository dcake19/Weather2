package com.example.presentation_weather_2

import com.example.presentation_weather_2.constants.*

fun convertToWindDirection(windDeg: Int):Int{
    return when(windDeg){
        in 0..22 -> NORTH
        in 23..67 -> NORTH_EAST
        in 68..112 -> EAST
        in 113..157 -> SOUTH_EAST
        in 158..202 -> SOUTH
        in 203..247 -> SOUTH_WEST
        in 248..292 -> WEST
        in 293..337 -> NORTH_WEST
        in 338..360 -> NORTH
        else -> 0
    }
}