package com.example.api_location.repository

import com.example.api_location.repository.db.LocationEntity
import io.reactivex.Single

interface LocationRepository {
    fun getLocation(latitude: Double, longitude: Double): Single<LocationEntity>
    fun getLocation(placeId: String): Single<LocationEntity>
    fun getStoredLocations(): Single<List<LocationEntity>>
    fun deleteLocation(locationEntity: LocationEntity)
}