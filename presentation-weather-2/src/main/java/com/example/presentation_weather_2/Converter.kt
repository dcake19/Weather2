package com.example.presentation_weather_2

import com.example.presentation_weather_2.constants.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

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

fun convertToTime(timestamp: Int): String{
    val dateTime = DateTime(timestamp * 1000L)
    return DateTimeFormat.forPattern("H:mm").print(dateTime)
}

fun convertToDate(timestamp: Int): String{
    val dateTime = DateTime(timestamp * 1000L)
    return DateTimeFormat.forPattern("EE d MMMMM H:mm").print(dateTime)
}

fun convertToDay(timestamp: Int): String{
    val dateTime = DateTime(timestamp * 1000L)
    return DateTimeFormat.forPattern("EE").print(dateTime)
}


