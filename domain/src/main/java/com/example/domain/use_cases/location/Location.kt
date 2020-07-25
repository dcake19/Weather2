package com.example.domain.use_cases.location

data class Location(val placeId: String,val name: String,val region: String,
                    val country: String,val latitude: Double,val longitude: Double)