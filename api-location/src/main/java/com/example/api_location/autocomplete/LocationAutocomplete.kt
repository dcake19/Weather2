package com.example.api_location.autocomplete


import com.google.gson.annotations.SerializedName

data class LocationAutocomplete(
    @SerializedName("predictions")
    val predictions: List<Prediction>,
    @SerializedName("status")
    val status: String
)