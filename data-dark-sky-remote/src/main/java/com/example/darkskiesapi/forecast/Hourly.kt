package com.example.darkskiesapi.forecast


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("summary")
    val summary: String
)