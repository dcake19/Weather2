package com.example.presentation_location_view_model.map

data class MapLocationView(val placeId: String, val latitude: Double, val longitude: Double)

data class NewMapLocationView(val placeId: String, val latitude: Double, val longitude: Double,
                              val name: String,val region: String,val country: String)