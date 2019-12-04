package com.example.data_repository_location

data class LocationData(val placeId: String,val name: String,val region: String,
                        val country: String,val latitude: Double,val longitude: Double,
                        val latitudeNorthEast: Double, val longitudeNorthEast: Double,
                        val latitudeSouthWest: Double, val longitudeSouthWest: Double,
                        var position: Int = -1)