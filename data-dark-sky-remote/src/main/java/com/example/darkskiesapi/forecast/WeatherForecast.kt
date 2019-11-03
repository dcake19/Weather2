package com.example.darkskiesapi.forecast


import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("currently")
    val currently: Currently,
    @SerializedName("daily")
    val daily: Daily,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("hourly")
    val hourly: Hourly,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("minutely")
    val minutely: Minutely,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("timezone")
    val timezone: String
)