package com.example.data_storage_location

import com.example.data_repository_location.LocationData
import com.example.data_repository_location.LocationDataCache
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.data_storage_location.db.LocationEntity
import io.reactivex.Single

class LocationCache(private val locationDatabase: LocationDatabaseProvider) : LocationDataCache {

   private val locationDao = locationDatabase.getLocationDao()

    override fun getLocationsBounding(lat: Double, lng: Double): Single<List<LocationData>> {
        return locationDao.getLocationsBounding(lat, lng)
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun getLocations(): Single<List<LocationData>> {
        return locationDao.getLocations()
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun deleteLocation(placeId: String) {
        locationDao.delete(placeId)
    }

    override fun insert(locationData: LocationData) {
        locationDao.insert(LocationEntity(locationData))
    }

    private fun mapToLocationData(locationEntity: LocationEntity): LocationData{
        return LocationData(locationEntity.placeId,locationEntity.locality?:"",
            locationEntity.adminArea1?:"",locationEntity.country?:"",
            locationEntity.latitude,locationEntity.longitude,
            locationEntity.latitudeNorthEast,locationEntity.longitudeNorthEast,
            locationEntity.latitudeSouthWest,locationEntity.longitudeSouthWest)
    }
}