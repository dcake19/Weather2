package com.example.darkskiesapi.forecast


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("apparentTemperatureHigh")
    val apparentTemperatureHigh: Double,
    @SerializedName("apparentTemperatureHighTime")
    val apparentTemperatureHighTime: Int,
    @SerializedName("apparentTemperatureLow")
    val apparentTemperatureLow: Double,
    @SerializedName("apparentTemperatureLowTime")
    val apparentTemperatureLowTime: Int,
    @SerializedName("apparentTemperatureMax")
    val apparentTemperatureMax: Double,
    @SerializedName("apparentTemperatureMaxTime")
    val apparentTemperatureMaxTime: Int,
    @SerializedName("apparentTemperatureMin")
    val apparentTemperatureMin: Double,
    @SerializedName("apparentTemperatureMinTime")
    val apparentTemperatureMinTime: Int,
    @SerializedName("cloudCover")
    val cloudCover: Int,
    @SerializedName("dewPoint")
    val dewPoint: Double,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("moonPhase")
    val moonPhase: Double,
    @SerializedName("ozone")
    val ozone: Double,
    @SerializedName("precipIntensity")
    val precipIntensity: Double,
    @SerializedName("precipIntensityMax")
    val precipIntensityMax: Double,
    @SerializedName("precipIntensityMaxTime")
    val precipIntensityMaxTime: Int,
    @SerializedName("precipProbability")
    val precipProbability: Double,
    @SerializedName("precipType")
    val precipType: String,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("sunriseTime")
    val sunriseTime: Int,
    @SerializedName("sunsetTime")
    val sunsetTime: Int,
    @SerializedName("temperatureHigh")
    val temperatureHigh: Double,
    @SerializedName("temperatureHighTime")
    val temperatureHighTime: Int,
    @SerializedName("temperatureLow")
    val temperatureLow: Double,
    @SerializedName("temperatureLowTime")
    val temperatureLowTime: Int,
    @SerializedName("temperatureMax")
    val temperatureMax: Double,
    @SerializedName("temperatureMaxTime")
    val temperatureMaxTime: Int,
    @SerializedName("temperatureMin")
    val temperatureMin: Int,
    @SerializedName("temperatureMinTime")
    val temperatureMinTime: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("uvIndex")
    val uvIndex: Int,
    @SerializedName("uvIndexTime")
    val uvIndexTime: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("windBearing")
    val windBearing: Int,
    @SerializedName("windGust")
    val windGust: Double,
    @SerializedName("windGustTime")
    val windGustTime: Int,
    @SerializedName("windSpeed")
    val windSpeed: Double
)