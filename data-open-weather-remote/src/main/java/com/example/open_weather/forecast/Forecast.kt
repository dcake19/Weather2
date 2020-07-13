package com.example.open_weather.forecast

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Int?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int?,
    @SerializedName("current")
    val current: Current?,
    @SerializedName("minutely")
    val minutely: List<Minutely>?,
    @SerializedName("hourly")
    val hourly: List<Hourly>?,
    @SerializedName("daily")
    val daily: List<Daily>?
)