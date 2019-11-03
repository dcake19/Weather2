package com.example.data_repository_location

import io.reactivex.Single

interface LocationDataCache {

    fun getLocationsBounding(lat: Double,lng: Double): Single<List<LocationData>>
    fun getLocations(): Single<List<LocationData>>
    fun deleteLocation(placeId: String)
    fun insert(locationData: LocationData)
}