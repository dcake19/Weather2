package com.example.domain

import io.reactivex.Completable
import io.reactivex.Single

interface LocationsRepository {
    fun getLocation(latitude: Double, longitude: Double): Single<Location>
    fun addLocation(placeId: String): Completable
    fun getStoredLocations(): Single<List<Location>>
    fun deleteLocations(placeIds:  List<String>)
    fun updateLocations(locations: List<String>)
    fun getLocationByPlaceId(placeId: String): Single<Location>
    fun getLocationByName(name: String): Single<Location>
}