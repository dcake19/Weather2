package com.example.open_weather.forecast


import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("temp")
    val temp: Double?,
    @SerializedName("feels_like")
    val feelsLike: Double?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("dew_point")
    val dewPoint: Double?,
    @SerializedName("uvi")
    val uvi: Double?,
    @SerializedName("clouds")
    val clouds: Int?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("wind_speed")
    val windSpeed: Double?,
    @SerializedName("wind_deg")
    val windDeg: Int?,
    @SerializedName("weather")
    val weather: List<Weather>?
)