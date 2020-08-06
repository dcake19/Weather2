package com.example.open_weather.forecast


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("temp")
    val temp: Temp?,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("dew_point")
    val dewPoint: Double?,
    @SerializedName("wind_speed")
    val windSpeed: Double?,
    @SerializedName("wind_deg")
    val windDeg: Int?,
    @SerializedName("weather")
    val weather: List<WeatherXX>?,
    @SerializedName("clouds")
    val clouds: Int?,
    @SerializedName("pop")
    val pop: Int?,
    @SerializedName("rain")
    val rain: Double?,
    @SerializedName("uvi")
    val uvi: Double?
)