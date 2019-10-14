package com.example.darkskiesapi.forecast


import com.google.gson.annotations.SerializedName

data class Minutely(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("summary")
    val summary: String
)