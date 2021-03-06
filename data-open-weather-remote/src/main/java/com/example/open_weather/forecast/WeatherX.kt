package com.example.open_weather.forecast


import com.google.gson.annotations.SerializedName

data class WeatherX(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?
)