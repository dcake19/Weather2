package com.example.api_location.locations


import com.google.gson.annotations.SerializedName

data class Locations(
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("status")
    val status: String
)