package com.example.open_weather.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int?,
    @SerializedName("current")
    val current: Current?,
    @SerializedName("hourly")
    val hourly: List<Hourly>?,
    @SerializedName("daily")
    val daily: List<Daily>?
)