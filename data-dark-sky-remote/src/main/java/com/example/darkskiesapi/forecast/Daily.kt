package com.example.darkskiesapi.forecast


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("data")
    val `data`: List<DataXX>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("summary")
    val summary: String
)