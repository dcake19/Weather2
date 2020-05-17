package com.example.presentation_location_view_model.map

data class MapLocationView(val placeId: String, val latitude: Double, val longitude: Double)

data class NewMapLocationView(val placeId: String, val latitude: Double, val longitude: Double,
                              val placeName: String,val placeRegion: String)