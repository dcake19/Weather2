package com.example.domain

import io.reactivex.Single

interface LocationsRepository {
    fun getLocation(latitude: Double, longitude: Double): Single<Location>
    fun getLocation(placeId: String): Single<Location>
    fun getStoredLocations(): Single<List<Location>>
    fun deleteLocations(placeIds:  List<String>)
    fun updateLocations(locations: List<String>)
}