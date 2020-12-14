package com.example.data_repository_location

import io.reactivex.Maybe
import io.reactivex.Single

interface LocationDataCache {

    fun getLocationsBounding(lat: Double,lng: Double): Single<List<LocationData>>
    fun getLocation(placeId: String): Single<LocationData>
    fun getLocations(): Single<List<LocationData>>
    fun deleteLocation(placeIds: List<String>)
    fun insert(locationData: LocationData)
    fun updateLocationsOrder(locations: List<String>)
}