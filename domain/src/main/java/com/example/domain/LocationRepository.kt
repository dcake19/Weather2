package com.example.domain

import io.reactivex.Single

interface LocationRepository {
    fun getLocation(latitude: Double, longitude: Double): Single<Location>
    fun getLocation(placeId: String): Single<Location>
    fun getStoredLocations(): Single<List<Location>>
    fun deleteLocation(placeId: String)
}