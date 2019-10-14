package com.example.api_location.locations


import com.google.gson.annotations.SerializedName

data class Bounds(
    @SerializedName("northeast")
    val northeast: Northeast,
    @SerializedName("southwest")
    val southwest: Southwest
)