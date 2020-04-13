package com.example.data_repository_location

import com.example.domain.Location
import io.reactivex.Single

interface LocationDataCache {

    fun getLocationsBounding(lat: Double,lng: Double): Single<List<LocationData>>
    fun getLocations(): Single<List<LocationData>>
    fun deleteLocation(placeIds: List<String>)
    fun insert(locationData: LocationData): LocationData
    fun updateLocations(locations: List<String>)
}