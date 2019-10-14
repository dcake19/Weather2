package com.example.api_location.autocomplete


import com.google.gson.annotations.SerializedName

data class MainTextMatchedSubstring(
    @SerializedName("length")
    val length: Int,
    @SerializedName("offset")
    val offset: Int
)