package com.example.data_storage_location

import com.example.data_repository_location.LocationData
import com.example.data_repository_location.LocationDataCache
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.data_storage_location.db.LocationEntity
import io.reactivex.Maybe
import io.reactivex.Single

class LocationCache(locationDatabase: LocationDatabaseProvider) : LocationDataCache {

   private val locationDao = locationDatabase.getLocationDao()

    override fun getLocationsBounding(lat: Double, lng: Double): Single<List<LocationData>> {
        return locationDao.getLocationsBoundingSingle(lat, lng)
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun getLocation(placeId: String): Single<LocationData> {
        return locationDao.getLocation(placeId)
            .map { mapToLocationData(it) }
    }

    override fun getLocations(): Single<List<LocationData>> {
        return locationDao.getLocationsSingle()
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun deleteLocation(placeIds: List<String>) {
        locationDao.delete(placeIds)
    }

    override fun updateLocationsOrder(locations: List<String>){
        val currentCachedLocations = locationDao.getLocations()
        val updatedCachedLocations = mutableListOf<LocationEntity>()
        for (location in locations){
            val locationEntity = currentCachedLocations.firstOrNull { it.placeId == location }
            if (locationEntity!=null) updatedCachedLocations.add(locationEntity)
        }

        locationDao.insert(updatedCachedLocations)
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